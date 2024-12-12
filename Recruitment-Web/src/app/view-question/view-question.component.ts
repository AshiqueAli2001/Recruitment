import { Component, OnInit } from '@angular/core';
import { AppConfigurationService } from '../services/app.configuration.service';
import { Questionmodel } from '../Model/questionmodel';
import { ToastrService } from 'ngx-toastr';
import { AdminServicesService } from '../services/admin-services.service';
import { AuthService } from '../auth.service';

declare var $: any;

@Component({
  selector: 'app-view-question',
  templateUrl: './view-question.component.html',
  styleUrls: ['./view-question.component.css']
})
export class ViewQuestionComponent implements OnInit {

  questiontable: any;
  selectedCategory: string = '';
  selectedquestionIdForDelete: any;
  dataTableRows: any[] = [];
  static obj: ViewQuestionComponent;
  
  categoryCountList: CategoryCountModel = new CategoryCountModel();

  initialHead: HeaderConfig[] = [
    { name: "Question ID ", isSelected: false },
    { name: "Category ", isSelected:false},
    { name: "Question", isSelected: false },
    { name: "Option 1 ", isSelected: false },
    { name: "Option 2 ", isSelected: false },
    { name: "Option 3 ", isSelected: false },
    { name: "Option 4 ", isSelected:false},
  ];


  constructor(private appconfigurationservice : AppConfigurationService, private authService: AuthService, private toastr: ToastrService, private serviceadmin : AdminServicesService) { }

  ngOnInit(): void {
    ViewQuestionComponent.obj = this;
   this. getQuestions();
  }

  getQuestions() {
    this.questiontable = $('#question-table').DataTable({

      "bProcessing": true,
      "bDeferRender": true,
      "ordering": false,
      bAutoWidth: true,
      bServerSide: true,

      sAjaxSource: this.appconfigurationservice.getApiService("adminquestionview"),

      "fnServerParams": function (aoData) {
        var dataString = ViewQuestionComponent.obj.getSearchInputs();
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
            }},
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
        { "mDataProp": "category", "bSortable": false },
        { "mDataProp": "question", "bSortable": false },
        { "mDataProp": "option1", "bSortable": false },
        { "mDataProp": "option2", "bSortable": false, },
        { "mDataProp": "option3", "bSortable": false },
        { "mDataProp": "option4", "bSortable": false },
      ]

    });

    $('#question-table tbody').on('click', 'tr', (event) => {
      const clickedRow = this.questiontable.row(event.currentTarget).data();
      console.log('Clicked Row Data:', clickedRow);
      $(event.currentTarget).toggleClass('selected');
    
      if (clickedRow) {
        console.log('Question:', clickedRow.questionId );
      }
    });

    $('#question-table tbody').on('dblclick', 'tr', (event) => {
      const clickedRowData = this.questiontable.row(event.currentTarget).data();
      console.log("Data : ",clickedRowData);
      this.showDetailsModal(clickedRowData);
    });
    
  }

  private calculateQuestionCount(data: any): number {
    // Implement logic to calculate question count based on your data
    return data.length; // Example: assuming data is an array
  }

  getSearchInputs() {
    let questionSearch: Questionmodel = new Questionmodel();
    // candidateSearch.userId = $('#userid').val();
    // candidateSearch.name = $('#name').val();
    // questionSearch.category = $('#category').val();
    if (questionSearch.question == null || questionSearch.question == undefined) {
      questionSearch.question = '';
    }
    if (questionSearch.difficulty == null || questionSearch.difficulty == undefined) {
      questionSearch.difficulty = '';
    }
    if (questionSearch.category == null || questionSearch.category == undefined) {
      questionSearch.category = '';
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
    this.serviceadmin.deletequestion(this.selectedquestionIdForDelete).subscribe((item: any) => {
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
    const keyOrder = ['questionId', 'category', 'question', 'option1', 'option2', 'option3', 'option4',
     'correctAnswer', 'difficulty', 'userId', 'status'];
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

  // refreshByCategory(category: string) 
  // {
  //   console.log(category)
  //   this.selectedCategory = category;
  //   $("#category").val(category).trigger("change");
  //   this.search();
  // }

}

export class HeaderConfig {
  name: string;
  isSelected: boolean;
}

export class CategoryCountModel {
  Java: number;
  Python: number;
  totalQuestions: number;
  constructor() {
      this.Java = 0;
      this.Python = 0;
      this.totalQuestions = 0;
  }
}


