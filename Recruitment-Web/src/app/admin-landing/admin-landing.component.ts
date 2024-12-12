import { AdminServicesService } from './../services/admin-services.service';
import { Component, OnInit } from '@angular/core';
import { AppConfigurationService } from '../services/app.configuration.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { Usermodel } from '../Model/usermodel';
import { trigger, state, style, animate, transition } from '@angular/animations';
import { AuthService } from '../auth.service';
import { CommonServiceProvider } from '../services/common.service';

declare var $: any;

@Component({
  animations: [
    trigger('fadeInOut', [
        state('in', style({ opacity: 1 })),
        transition(':enter', [
            style({ opacity: 0 }),
            animate(300)
        ]),
        transition(':leave',
            animate(300, style({ opacity: 0 }))
        )
    ]),
    trigger('slideInOut', [
      state('in', style({
        'max-height': '500px', 
        opacity: 1,
      })),
      state('out', style({
        'max-height': '0',
        opacity: 0,
      })),
      transition('in => out', animate('4000ms ease-in-out')),
      transition('out => in', animate('4000ms ease-in-out'))
    ])
],
  selector: 'app-admin-landing',
  templateUrl: './admin-landing.component.html',
  styleUrls: ['./admin-landing.component.css','./admin-landing.component.scss']
})
export class AdminLandingComponent implements OnInit {

  updateddata: {};
  slect2: string = "";
  candidatetable: any;
  selecteduser: any = [];
  isCardBodyVisible = false;
  selectedcandidateForUpdate: any;
  selectedFile: File | null = null;
  selectedcandidateIdForDelete: any;
  static obj: AdminLandingComponent;
  userobj : Usermodel = new Usermodel();
  statusCountList: StatusCountModel = new StatusCountModel();
  selectedId:string='';

  initialHead: HeaderConfig[] = [
    { name: "User ID", isSelected: false },
    { name: "Name", isSelected: false },
    { name: "College", isSelected: false },
    { name: "Department", isSelected: false },
    { name: "Email", isSelected:false},
    { name: "Phone", isSelected: false },
    { name: "status", isSelected: false },
  ];

  constructor(private appconfigurationservice : AppConfigurationService,  private authService: AuthService,private toastr: ToastrService,
     private serviceadmin : AdminServicesService, private router: Router,private commonServiceProvider:CommonServiceProvider) { }

  ngOnInit(): void {
    AdminLandingComponent.obj = this;
   this.getUsers();
   this.candidatetable.draw();
  }

  getUsers() {
    this.candidatetable = $('#candidate-table').DataTable({

      "bProcessing": true,
      "bDeferRender": true,
      "ordering": false,
      bAutoWidth: false,
      bServerSide: true,

      sAjaxSource: this.appconfigurationservice.getApiService("candidateview"),

      "fnServerParams": function (aoData) {
        var dataString = AdminLandingComponent.obj.getSearchInputs();
        aoData.push({ name: "searchParam", value: dataString });
      },
      "fnServerData": (sSource, aaData, fnCallback, oSettings) => {
        oSettings.jqXHR = $.ajax({
          "dataType": 'json',
          "type": "GET",
          "url": sSource,
          "data": aaData,
          "beforeSend": (xhr) => {
            const token = this.authService.getToken();
            if(token){
              xhr.setRequestHeader('Authorization',`Bearer ${token}`);
            }
          },
          "success": (data) => {
            this.commonServiceProvider.setStatusCount(this.statusCountList, data.countByStatus);

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
        { "mDataProp": "college", "bSortable": false, },
        { "mDataProp": "department", "bSortable": false },
        { "mDataProp": "email", "bSortable": false },
        { "mDataProp": "phone","bSortable":false},
        { "mDataProp": "status", "bSortable": false,
        "mRender": function (data) {

          if (data == 'PROCESSD') {
            return '<span class="badge badge-warning ipsh-badge-pending">Pending Approval</span>';
          } else if (data == 'DELETE') {
            return '<span class="badge badge-danger ipsh-badge-delete-approval">Delete Approval</span>';
          } else if (data == 'VERIFIED') {
            return '<span class="badge badge-success ipsh-badge-approve">Approved</span>'
          } else if (data == 'DELETED') {
            return '<span class="badge badge-danger ipsh-badge-delete">Deleted</span>'
          } 
          return data;
        }
      }
      ],
      "initComplete": function (settings, json) {
        $('#candidate-table').addClass('table'); 
      }
    });

    $('#candidate-table tbody').on('click', 'tr', (event) => {
      $(event.currentTarget).toggleClass('selected');
    });

    $('#candidate-table tbody').on('dblclick', 'tr', (event) => {
      this.selecteduser = this.candidatetable.row(event.currentTarget).data();
      this.showDetailsModal(this.selecteduser);
    });
  }

  getSearchInputs() {
    let candidateSearch: Usermodel = new Usermodel();
    candidateSearch.userId = $('#userid').val();
    candidateSearch.name = $('#name').val();
    candidateSearch.college = $('#college').val();
    candidateSearch.role="USER";
    candidateSearch.status = $('#idStatus').val();
    if ($('#idStatus').val()==""|| candidateSearch.status == undefined ) {
      candidateSearch.status = '';
    }
    if (Object.values(candidateSearch).length>0) {
      return JSON.stringify(candidateSearch);
    }
  }

  get(label) {
    return  label;
  }

  search() {
    this.candidatetable.draw();
  }

  clear() {
    $('#name').val('');
    $('#userid').val('');
    $('#college').val('');
    $('#idStatus').val("").trigger("change");
    this.candidatetable.draw();
  }

  delete() 
  {
    if (this.candidatetable.rows('.selected').data().length == 0) 
    {
      this.toastr.error('Select a Field', 'Error');
    } 
    else if (this.candidatetable.rows('.selected').data().length > 1) 
    {
      this.toastr.error('Multiple Records cannot be deleted', 'Warning');
    } 
    else 
    {
      this.selectedcandidateIdForDelete = this.candidatetable.rows('.selected').data()[0].userId;
      
      $('#delete-user-modal').modal({
        backdrop: false
      }); 
      $("#delete-user-modal").modal("show"); 
    }
  }

  confirmDeleteUser() 
  {
    this.serviceadmin.deletecandidate(this.selectedcandidateIdForDelete).subscribe((item: any) => {
      this.selectedcandidateIdForDelete = "";
      $("#delete-user-modal").modal("hide");
      console.log(item);
      this.toastr.success('Candidate Deleted', 'Sucess');
      this.candidatetable.draw();
    }, error => {
      console.log(error)
    }, () => {
      console.log("finally")
    });
  }

  cancelDeleteUser() 
  {
    this.selectedcandidateIdForDelete = "";
    $("#delete-user-modal").modal("hide");
  }

  showDetailsModal(data: any) {
    const modalBody = $('#detailsModalBody');
    modalBody.empty();
    const keyOrder = ['userId', 'name', 'phone', 'email', 'college', 'department', 'highestScore',
     'noOfAttempts'];
    keyOrder.forEach((key) => {
      if (data.hasOwnProperty(key)&& key!="password"&& key!="image") {
        modalBody.append(`<p><strong>${key}:</strong> ${data[key]}</p>`);
      }
    });
    $('#detailsModal').modal({
      backdrop: false
    });
    $('#detailsModal').modal('show');
  }

  closedetails(){
    this.selectedcandidateIdForDelete = "";
    $("#detailsModal").modal("hide");
  }

  update(){
    if (this.candidatetable.rows('.selected').data().length == 0) 
    {
      this.toastr.warning('Select a Field', 'Error');
    } 
    else if (this.candidatetable.rows('.selected').data().length > 1) 
    {
      this.toastr.warning('Multiple Records cannot be deleted', 'Warning');
    } 
    else 
    {
      this.selectedcandidateForUpdate = this.candidatetable.rows('.selected').data()[0];
      this.showEditModal(this.selectedcandidateForUpdate);
    }
  }


  Verify() {
    if (this.candidatetable.rows('.selected').data().length == 0) {
      this.toastr.warning("Please Select a Record To Verify.", 'Warning');
    } else if (this.candidatetable.rows('.selected').data().length > 1) {
      this.toastr.warning('Multiple Record Cannot Verify.', 'Warning');
    } else {
      
        var status: any;
        status = this.candidatetable.rows('.selected').data()[0].status;
        this.selectedId = this.candidatetable.rows('.selected').data()[0].userId;
        if (status == "VERIFIED") {
          this.toastr.error( 'Candidate Already Verified !', 'Info');
        } else {

          this.serviceadmin.verifyCandidate(this.selectedId).subscribe((item: any) => {
            console.log(item);
            if(item.code=="SUCCESS")
             {this.toastr.success(item.message, 'Sucess');}
             else{
              this.toastr.error(item.message,'Error');
             }
            this.candidatetable.draw();
          }, error => {
            console.log(error)
          }, () => {
            console.log("finally")
          });          
        }
      
    }
  }

  showEditModal(data: any) {
    const modalBody = $('#updateModalBody');
    modalBody.empty();
    for (const key in data) {
      if (data.hasOwnProperty(key) && key!="password" && key!="finishingTime" && key!="criteriaId" 
      && key!="result" && key!="score" && key!="pdf" && key!="status" && key!="userId" && key!="image") {
        modalBody.append(`
          <div class="form-group">
            <label for="${key}">${key}</label>
            <input type="text" class="form-control" id="${key}" value="${data[key]}">
          </div>
        `);
        
      }else{
        this.userobj.userId=data["userId"];
        this.userobj.password=data["password"];
        // this.userobj.finishingTime=data["finishingTime"];
        // this.userobj.criteriaId=data["criteriaId"];
        // this.userobj.result=data["result"];
        // this.userobj.score=data["score"];
        // this.userobj.pdf=data["pdf"];
        this.userobj.status=data["status"];
        }
    }
    $('#updateModal').modal({
      backdrop: false
    });
    $('#updateModal').modal('show');
  }

  closeupdate(){
    this.selectedcandidateIdForDelete = "";
    $("#updateModal").modal("hide");
  }

  updateCandidate() {
    const updatedData = {};
    $('#updateModalBody input').each((index, element) => {
      const key = $(element).attr('id');
      const value = $(element).val();
      updatedData[key] = value;
    }); 

    this.userobj.college=updatedData["college"];
    this.userobj.department=updatedData["department"];
    this.userobj.phone=updatedData["phone"];
    this.userobj.name=updatedData["name"];
    this.userobj.email=updatedData["email"];

    console.log("data",this.userobj)

    console.log("----------------------------")
    this.serviceadmin.UpdateCandidate(this.userobj,this.selectedFile).subscribe((item: any) => {
      if(item.code.toLowerCase()=="success"){
        this.toastr.success(item.message, 'Sucess');
      }
      else{
        this.toastr.error(item.message,'Failed')
      }
      this.clear();
    }, error => {
      if (error.status == "400") 
      {
        let msg = "";
        error.error.details.forEach(element => {
          msg = msg + element + "<br>"
        });
      }
    }, () => {
    });
    
    console.log(this.userobj);
    $('#updateModal').modal('hide');
  }

  toggleCardBody() {
    this.isCardBodyVisible = !this.isCardBodyVisible;
  }

  onFileSelected(event: any) {
    this.selectedFile=event.target.files[0];
  }

  refreshByStatus(status: string) 
  {
    if(this.isCardBodyVisible==false)
    {
      this.toggleCardBody();
    }
    $("#idStatus").val(status).trigger("change");
    this.search();
  }
  
}

export class HeaderConfig {
  name: string;
  isSelected: boolean;
}

export class StatusCountModel {
  delete: number;
  deleted: number;
  processd: number;
  totalStatus: number;
  verified: number;
  reprocess:number;
  constructor() {
      this.delete = 0;
      this.deleted = 0;
      this.processd = 0;
      this.totalStatus = 0;
      this.verified = 0;
      this.reprocess=0;
  }

}
