import { Component, OnInit } from '@angular/core';
import { CategoryCountModel } from '../view-question/view-question.component';
import { HeaderConfig } from '../admin-landing/admin-landing.component';
import { AppConfigurationService } from '../services/app.configuration.service';
import { ToastrService } from 'ngx-toastr';
import { AdminServicesService } from '../services/admin-services.service';
import { Codemodel } from '../Model/codemodel';
import { AuthService } from '../auth.service';

declare var $: any;

@Component({
  selector: 'app-view-codequestion',
  templateUrl: './view-codequestion.component.html',
  styleUrls: ['./view-codequestion.component.css']
})
export class ViewCodequestionComponent implements OnInit {

  questiontable: any;
  selectedCategory: string = '';
  selectedquestionIdForDelete: any;
  dataTableRows: any[] = [];
  static obj: ViewCodequestionComponent;
  
  categoryCountList: CategoryCountModel = new CategoryCountModel();

  initialHead: HeaderConfig[] = [
    
    { name: "Question ID", isSelected: false },
    { name: "Question", isSelected: false },
    { name: "Test Case 1 ", isSelected: false },
    { name: "Expected Output 1 ", isSelected: false },
  ];


  constructor(private appconfigurationservice : AppConfigurationService  ,private authService: AuthService, private toastr: ToastrService, private serviceadmin : AdminServicesService) { }

  ngOnInit(): void {
    ViewCodequestionComponent.obj = this;
   this. getQuestions();
  }

  getQuestions() {
    this.questiontable = $('#codequestion-table').DataTable({

      "bProcessing": true,
      "bDeferRender": true,
      "ordering": false,
      bAutoWidth: true,
      bServerSide: true,

      sAjaxSource: this.appconfigurationservice.getApiService("admincodequestionview"),

      "fnServerParams": function (aoData) {
        var dataString = ViewCodequestionComponent.obj.getSearchInputs();
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
        { "mDataProp": "questionId", "bSortable": false },
        { "mDataProp": "question", "bSortable": false },
        { "mDataProp": "testCase1", "bSortable": false, },
        { "mDataProp": "expected1", "bSortable": false },
      ]

    });

    $('#codequestion-table tbody').on('click', 'tr', (event) => {
      const clickedRow = this.questiontable.row(event.currentTarget).data();
      console.log('Clicked Row Data:', clickedRow);
      $(event.currentTarget).toggleClass('selected');
    
      if (clickedRow) {
        console.log('Question:', clickedRow.questionId );
      }
    });

    $('#codequestion-table tbody').on('dblclick', 'tr', (event) => {
      const clickedRowData = this.questiontable.row(event.currentTarget).data();
      this.showDetailsModal(clickedRowData);
    });
    
  }

  getSearchInputs() {
    let questionSearch: Codemodel = new Codemodel();
    // candidateSearch.userId = $('#userid').val();
    // candidateSearch.name = $('#name').val();
    // questionSearch.category = $('#category').val();
    if (questionSearch.question == null || questionSearch.question == undefined) {
      questionSearch.question = '';
    }
    if (questionSearch.difficulty == null || questionSearch.difficulty == undefined) {
      questionSearch.difficulty = '';
    }
    if (Object.values(questionSearch).length>0) {
      return JSON.stringify(questionSearch);
    }
    // return "";
  }

  search() {
    this.questiontable.draw();
  }

  get(label) {
    return  label;
  }

  delete() 
  {
    if (this.questiontable.rows('.selected').data().length == 0) 
    {
      this.toastr.error('Select a Field', 'Error');
    } 
    else if (this.questiontable.rows('.selected').data().length > 1) 
    {
      this.toastr.error('Multiple Records cannot be deleted', 'Warning');
    } 
    else 
    {
      this.selectedquestionIdForDelete = this.questiontable.rows('.selected').data()[0].questionId;
      
      $('#delete-user-modal').modal({
        backdrop: false
      }); 
      $("#delete-user-modal").modal("show"); 
    }
  }

  confirmDeleteUser() 
  {
    this.serviceadmin.deletecodequestion(this.selectedquestionIdForDelete).subscribe((item: any) => {
      this.selectedquestionIdForDelete = "";
      $("#delete-user-modal").modal("hide");
      console.log(item);
      this.toastr.success('Question Deleted', 'Sucess');
      this.questiontable.draw();
    }, error => {
      console.log(error)
    }, () => {
      console.log("finally")
    });
  }

  cancelDeleteUser() 
  {
    this.selectedquestionIdForDelete = "";
    $("#delete-user-modal").modal("hide");
  }

  showDetailsModal(data: any) {
    const modalBody = $('#detailsModalBody');
    modalBody.empty();
    const keyOrder = ['questionId', 'question', 'testCase1', 'expected1', 'testCase2', 'expected2',
     'testCase3', 'expected3', 'testCase4', 'expected4', 'testCase5', 'expected5', 'difficulty', 'userId', 'status'];
    keyOrder.forEach((key) => {
      if (data.hasOwnProperty(key)) {
        modalBody.append(`<p><strong>${key}:</strong> ${data[key]}</p>`);
      }
    });
    $('#detailsModal').modal({
      backdrop: false
    });
    $('#detailsModal').modal('show');
  }

  closedetails(){
    this.selectedquestionIdForDelete = "";
    $("#detailsModal").modal("hide");
  }

}
