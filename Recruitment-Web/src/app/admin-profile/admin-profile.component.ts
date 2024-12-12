import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { AppConfigurationService } from '../services/app.configuration.service';
import { AdminServicesService } from '../services/admin-services.service';
import { ToastrService } from 'ngx-toastr';
import { Usermodel } from '../Model/usermodel';
import { AuthService } from '../auth.service';
import { CommonServiceProvider } from '../services/common.service';

declare var $: any;

@Component({
  selector: 'app-admin-profile',
  templateUrl: './admin-profile.component.html',
  styleUrls: ['./admin-profile.component.css']
})
export class AdminProfileComponent implements OnInit {
  
  table: any;
  admintable: any; 
  selectedFile: File | null = null;
  imageDataUrl: string | null = null;
  adminData: any[] = [];
  selectedadminIdForDelete: any;
  static obj: AdminProfileComponent;
  userobj : Usermodel = new Usermodel();
  imageData : Uint8Array;

  initialHead: HeaderConfig[] = [
    { name: "User ID", isSelected: false },
    { name: "Name", isSelected: false },
    { name: "Phone", isSelected: false },
    { name: "status", isSelected: false },
    // { name: "role", isSelected: false },
  ];

  currentAdmin: Usermodel = new Usermodel();
  currentAdminId: string;

  constructor(private appconfigurationservice : AppConfigurationService, private serviceadmin : AdminServicesService,private toastr: ToastrService,private commonServiceProvider:CommonServiceProvider,
    private authService: AuthService) { }

  ngOnInit(): void {
    AdminProfileComponent.obj = this;
    this. getUsers();

    this.currentAdmin = this.authService.getUser();

   
    this.currentAdminId = this.currentAdmin.userId;
  }

  getUsers() {
    this.admintable = $('#admins-table').DataTable({

      "bProcessing": true,
      "bDeferRender": true,
      "ordering": false,
      bAutoWidth: false,
      bServerSide: true,

      sAjaxSource: this.appconfigurationservice.getApiService("adminview"),

      "fnServerParams": function (aoData) {
        var dataString = AdminProfileComponent.obj.getSearchInputs();
        aoData.push({ name: "searchParam", value: dataString });
      },
      "fnServerData": (sSource, aoData, fnCallback, oSettings) => {
        oSettings.jqXHR = $.ajax({
          "dataType": 'json',
          "type": "GET",
          "url": sSource,
          "data": aoData,
          "beforeSend": (xhr) => {
            const token = this.authService.getToken();
            if(token){
              xhr.setRequestHeader('Authorization',`Bearer ${token}`);
            }},
          "success": (data) => {
            console.log("Data Recived",data)
            this.currentAdmin = data.aaData.find(admin => admin.userId === this.currentAdminId);

            console.log("This is the image",this.currentAdmin)
            fnCallback(data);
          },
          "error": (e) => {
            if (e.status == "403" || e.status == "401") {
            }
          }
        });
      },

      "sDom": "<rt><'row border-top pt-2'<'col-sm-12 col-md-5'l><'col-sm-12 col-md-7'p>>",
      "aoColumns": [
        { "mDataProp": "userId", "bSortable": false },
        { "mDataProp": "name", "bSortable": false },
        { "mDataProp": "phone", "bSortable": false },
        { "mDataProp": "status", "bSortable": false,
        "mRender": function (data) {

         if (data == 'DELETE') {
            return '<span class="badge badge-danger ipsh-badge-delete-approval">Inactive</span>';
          } else if (data == 'VERIFIED') {
            return '<span class="badge badge-success ipsh-badge-approve">Active</span>'
          } else if (data == 'DELETED') {
            return '<span class="badge badge-danger ipsh-badge-delete">Inactive</span>'
          } 
          return data;
        }
      }
      ],
      "initComplete": function (settings, json) {
        $('#admins-table').addClass('table');  
      }
    });

    $('#admins-table tbody').on('click', 'tr', (event) => {
      const clickedRow = this.admintable.row(event.currentTarget).data();
      console.log('Clicked Row Data:', clickedRow);
      $(event.currentTarget).toggleClass('selected');
    });
  }

  getSearchInputs() {
    let userSearch: Usermodel = new Usermodel();
    userSearch.userId = $('#userId').val();
    userSearch.name = $('#name').val();
    userSearch.role="ADMIN";
    userSearch.status ==null;
    if (userSearch.userId == null || userSearch.userId == undefined) {
      userSearch.userId = '';
    }
    if (userSearch.name == null || userSearch.name == undefined) {
      userSearch.name = '';
    }
    if (userSearch.status == null || userSearch.status == undefined) {
      userSearch.status = '';
    }
    
    if (Object.values(userSearch).length>0) {
      return JSON.stringify(userSearch);
    }
    return "";
  }

  get(label) {
    return  label;
  }

  delete() 
  {
    if (this.admintable.rows('.selected').data().length == 0) 
    {
      this.toastr.error('Select a Field', 'Error');
    } 
    else if (this.admintable.rows('.selected').data().length > 1) 
    {
      this.toastr.error('Multiple Records cannot be deleted', 'Warning');
    } 
    else 
    {
      this.selectedadminIdForDelete = this.admintable.rows('.selected').data()[0].userId;
      if(this.selectedadminIdForDelete=="C0101")
      {
        this.toastr.error('Cannot Delete Main Admin', 'Warning');
      }
      else{
        $('#delete-user-modal').modal({
          backdrop: false
        }); 
        $("#delete-user-modal").modal("show");
      } 
    }
  }

  confirmDeleteAdmin() 
  {
    this.serviceadmin.deleteadmin(this.selectedadminIdForDelete).subscribe((item: any) => {
      this.selectedadminIdForDelete = "";
      $("#delete-user-modal").modal("hide");
      console.log(item);
      this.toastr.success('Admin Deleted', 'Sucess');
      this.admintable.draw();
    }, error => {
      console.log(error)
    }, () => {
      console.log("finally")
    });
  }

  cancelDeleteAdmin() 
  {
    this.selectedadminIdForDelete = "";
    $("#delete-user-modal").modal("hide");
  }

  upload() {
    this.userobj.phone=this.currentAdmin.phone;
    this.userobj.name=this.currentAdmin.name;
    this.userobj.email=this.currentAdmin.email;
    this.userobj.userId=this.currentAdminId;
    const maxSizeInBytes = 2 * 1024 * 1024;
    if (this.selectedFile.size > maxSizeInBytes) {
      this.toastr.error('File size exceeds the limit (2 MB)', 'Error');
    }
    else{
      this.serviceadmin.uploadimg(this.userobj,this.selectedFile).subscribe(
        (item: any) => {
          this.toastr.success('Image Uploaded', 'Success');
          this.admintable.destroy();
          this. getUsers();
        },
        (error) => {
          if (error.status === 400) {
            let msg = '';
            error.error.details.forEach((element) => {
              msg = msg + element + '<br>';
            });
          }
        },
        () => {}
      );
    }
    $('#updateModal').modal('hide');
  }
  

  addDp(){
    $('#updateModal').modal({
      backdrop: false
    });
    $('#updateModal').modal('show');
  }

  closeupdate(){
    $("#updateModal").modal("hide");
  }

  onFileSelected(event: any): void {
    this.selectedFile=event.target.files[0];
  }

}

export class HeaderConfig {
  name: string;
  isSelected: boolean;
}
