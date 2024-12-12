import { Component, OnInit } from '@angular/core';
import { Criteriamodel } from '../Model/criteriamodel';
import { AdminServicesService } from '../services/admin-services.service';
import { ToastrService } from 'ngx-toastr';
import { AppConfigurationService } from '../services/app.configuration.service';
import { concat } from 'rxjs';
import { AuthService } from '../auth.service';

declare var $: any;

@Component({
  selector: 'app-add-criteria',
  templateUrl: './add-criteria.component.html',
  styleUrls: ['./add-criteria.component.css']
})
export class AddCriteriaComponent implements OnInit {

  criteriaobj : Criteriamodel = new Criteriamodel(); 
  criteriatable: any; 
  static obj: AddCriteriaComponent;
  selectedcriteriaIdForDelete: any;
  currentAdminId: string;

  initialHead: HeaderConfig[] = [
    { name: "Criteria ID", isSelected: false },
    { name: "Title", isSelected: false },
    { name: "Easy MCQ", isSelected: false },
    { name: "Medium MCQ", isSelected: false },
    { name: "Hard MCQ", isSelected: false },
    { name: "Easy Code", isSelected:false},
    { name: "Medium Code", isSelected: false },
    { name: "Hard Code", isSelected: false },
    { name: "Test Duration", isSelected: false },
    { name: "Total Questions", isSelected: false },
    // { name: "Status", isSelected: false },
    { name: "Action", isSelected: false },
  ];

  constructor(private serviceadmin : AdminServicesService ,private authService: AuthService,private toastr: ToastrService,private appconfigurationservice : AppConfigurationService) { }

  ngOnInit(): void {
    AddCriteriaComponent.obj = this;
    this.getCriteria();

    const storedUser = this.authService.getUser();

   
      this.currentAdminId = storedUser.userId;
  }

  addcriteria(){
    this.criteriaobj.userId=this.currentAdminId;
    this.serviceadmin.addCriteria(this.criteriaobj).subscribe((item: any) => {
      if (item.code.toLowerCase() == "success") 
      {
        this.toastr.success(item.message, 'Success');
        this.clear();
        this.criteriatable.draw();
      } 
      else 
      {
        this.toastr.error(item.message, 'Error');
      }
    }, error => {
      console.log(error)
    }, () => {
      console.log("finally");
    });
  }

  getCriteria() {
    this.criteriatable = $('#criteria-table').DataTable({

      "bProcessing": true,
      "bDeferRender": true,
      "ordering": false,
      bAutoWidth: false,
      bServerSide: true,

      sAjaxSource: this.appconfigurationservice.getApiService("criteriaview"),

      "fnServerParams": function (aoData) {
        var dataString = AddCriteriaComponent.obj.getSearchInputs();
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
            // this.userService.setStatusCount(this.statusCountList, data.countByStatus);
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
        { "mDataProp": "criteriaId","bSortable": false},
        { "mDataProp": "title", "bSortable": false },
        { "mDataProp": "easyMcqQuestions", "bSortable": false },
        { "mDataProp": "mediumMcqQuestions", "bSortable": false, },
        { "mDataProp": "hardMcqQuestions", "bSortable": false },
        { "mDataProp": "easyCodingQuestions", "bSortable": false },
        { "mDataProp": "mediumCodingQuestions","bSortable": false},
        { "mDataProp": "hardCodingQuestions","bSortable": false},
        { "mDataProp": "testDuration","bSortable": false},
        { "mDataProp": "totalQuestions","bSortable": false},
        // { "mDataProp": "status","bSortable": false},
        {// Custom column for the "Publish" button
          "mData": null,
          "bSortable": false,
          "render": function (data, type, full, meta,) {
            const isPublished = data.status === 'VERIFIED';
            const buttonText = isPublished ? 'Published' : 'Publish';
            // return '<button class="btn btn-primary btn-publish">' + buttonText + '</button>';
            return '<button class="btn btn-primary btn-publish" onclick="togglePublish(this, ' + data.criteriaId + ')">' + buttonText + '</button>';
          }
        },
      ],
      "initComplete": function (settings, json) {
        $('#criteria-table').addClass('table');  // Add the 'table' class
      }
    });

    $('#criteria-table tbody').on('click', '.btn-publish', (event) => {
      event.stopPropagation();
      const clickedRow = this.criteriatable.row($(event.target).closest('tr')).data();
      const buttonText = clickedRow.status === 'PROCESSD' ? 'Publish' : 'Published';
      $(event.target).text(buttonText);
      this.criteriatable.draw();
      
      const id = clickedRow.criteriaId;
      console.log('Clicked Row Data:',clickedRow.criteriaId );
      
      this.serviceadmin.UpdateCriteria(id).subscribe((item: any) => {

        if(item.code.toLowerCase() == "success")
       {
        this.toastr.success(item.message, 'Sucess');
        this.criteriatable.draw();
        this.clear();
       } 
        else if(item.code.toLowerCase() == "cancelled")
       {
        this.toastr.success(item.message, 'Sucess');
        this.clear();
       } 
       else{
        this.toastr.error(item.message, 'Failed');
       }
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
      // Perform actions related to publishing here...
    });
    
    $('#criteria-table tbody').on('click', 'tr', (event) => {
      const clickedRow = this.criteriatable.row(event.currentTarget).data();
      console.log('Clicked Row Data:', clickedRow);
      $(event.currentTarget).toggleClass('selected');
    });
  }

  getSearchInputs() {
    let criteriasearch: Criteriamodel = new Criteriamodel();
    criteriasearch.title = $('#test').val();
    criteriasearch.testDuration = $('#duration').val();
    criteriasearch.easyCodingQuestions = $('#mcqeasy').val();
    if (Object.values(criteriasearch).length>0) {
      return JSON.stringify(criteriasearch);
    }
    // return "";
  }

  get(label) {
    return  label;
  }

  delete() 
  {
    if (this.criteriatable.rows('.selected').data().length == 0) 
    {
      this.toastr.error('Select a Field', 'Error');
    } 
    else if (this.criteriatable.rows('.selected').data().length > 1) 
    {
      this.toastr.error('Multiple Records cannot be deleted', 'Warning');
    } 
    else 
    {
      this.selectedcriteriaIdForDelete = this.criteriatable.rows('.selected').data()[0].criteriaId;
      
      $('#delete-user-modal').modal({
        backdrop: false
      }); 
      $("#delete-user-modal").modal("show"); 
    }
  }

  confirmDeleteCriteria() 
  {
    this.serviceadmin.deletecriteria(this.selectedcriteriaIdForDelete).subscribe((item: any) => {
      this.selectedcriteriaIdForDelete = "";
      $("#delete-user-modal").modal("hide");
      console.log(item);
      this.toastr.success('Criteria Deleted', 'Sucess');
      this.criteriatable.draw();
    }, error => {
      console.log(error)
    }, () => {
      console.log("finally")
    });
  }

  cancelDeleteCriteria() 
  {
    this.selectedcriteriaIdForDelete = "";
    $("#delete-user-modal").modal("hide");
  }

  clear() {
    $('#codeeasy').val('');
    $('#codeinter').val('');
    $('#codehard').val('');
    $('#mcqeasy').val('');
    $('#mcqinter').val('');
    $('#mcqhard').val('');
    $('#test').val('');
    $('#duration').val('');
  }

}

export class HeaderConfig {
  name: string;
  isSelected: boolean;
}

