import { AdminServicesService } from './../services/admin-services.service';
import { CandidateServicesService } from './../services/candidate-service.service';
import { Component, OnInit } from '@angular/core';
import { HeaderConfig } from '../admin-landing/admin-landing.component';
import { AppConfigurationService } from '../services/app.configuration.service';
import { Resultmodel } from '../Model/resultmodel';
import { AuthService } from '../auth.service';
import { Usermodel } from '../Model/usermodel';
import { ToastrService } from 'ngx-toastr';
import * as fileSaver from 'file-saver';

declare var $: any;

@Component({
  selector: 'app-view.result',
  templateUrl: './view.result.component.html',
  styleUrls: ['./view.result.component.css','./view.result.component.scss']
})
export class ViewResultComponent implements OnInit {

  resulttable: any;
  selectedId: any = [];
  candidate: any ;
  isCardBodyVisible = false;
  static obj: ViewResultComponent;

  initialHead: HeaderConfig[] = [
    { name: "User ID", isSelected: false },
    { name: "MCQ Score", isSelected: false },
    { name: "Code Score", isSelected: false },
    { name: "Total Score", isSelected: false },
    { name: "result", isSelected:false},
  ];
  
  currentAdmin: Usermodel  = new Usermodel();
  currentAdminId: string;

  constructor(private appconfigurationservice : AppConfigurationService, private authService: AuthService,private candidateService:CandidateServicesService,private toastr: ToastrService,private adminService: AdminServicesService) { }

  ngOnInit(): void {
    ViewResultComponent.obj = this;
    this. getUsers();

    this.currentAdmin = this.authService.getUser();

   
    this.currentAdminId = this.currentAdmin.userId;
  }

  getUsers() {
    this.resulttable = $('#result-table').DataTable({

      "bProcessing": true,
      "bDeferRender": true,
      "ordering": false,
      bAutoWidth: false,
      bServerSide: true,

      sAjaxSource: this.appconfigurationservice.getApiService("finalresult"),

      "fnServerParams": function (aoData) {
        var dataString = ViewResultComponent.obj.getSearchInputs();
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
        { "mDataProp": "mcqScore", "bSortable": false },
        { "mDataProp": "codingScore", "bSortable": false, },
        { "mDataProp": "score", "bSortable": false },
        { "mDataProp": "result", "bSortable": false },
      ],
      "initComplete": function (settings, json) {
        $('#result-table').addClass('table'); 
      }

    });

    $('#result-table tbody').on('click', 'tr', (event) => {
      $(event.currentTarget).toggleClass('selected');
    });

    $('#result-table tbody').on('dblclick', 'tr', (event) => {
      this.selectedId = this.resulttable.row(event.currentTarget).data();
      this.showDetailsModal(this.selectedId);
    });
  }

  getSearchInputs() {
    let resultSearch: Resultmodel = new Resultmodel();
    resultSearch.userId = $('#userid').val();
    resultSearch.result = $('#result').val();
    resultSearch.score = $('#score').val();
    if (Object.values(resultSearch).length>0) {
      return JSON.stringify(resultSearch);
    }
  }

  get(label) {
    return  label;
  }

  search() {
    this.resulttable.draw();
  }

  toggleCardBody() {
    this.isCardBodyVisible = !this.isCardBodyVisible;
  }

  clear() {
    $('#name').val('');
    $('#score').val('');
    $('#result').val('');
    this.resulttable.draw();
  }

  showDetailsModal(data: any) {

    this.adminService.getUserById(data.userId).subscribe((result: any) => {
      
        this.candidate=result;
      
      const modalBody = $('#detailsModalBody');
      modalBody.empty();
      
      for (const key in data) {
        if (data.hasOwnProperty(key) && key!="pdf" ) {
          modalBody.append(`<p><strong>${key}:</strong> ${data[key]}</p>`);
        }
      }
      $('#detailsModal').modal({
        backdrop: false
      });
      $('#detailsModal').modal('show');
 
    }, error => {
        console.log("error");
        this.toastr.error('Failed Downoad','Danger');
    });

   
  }

  closedetails(){
    this.selectedId = "";
    $("#detailsModal").modal("hide");
  }

  downloadPDF() {
const userId=this.selectedId.finalResultId;
    var blob:Blob;
    this.adminService.downloadFile(userId).subscribe((data: any) => {
    blob = data.body;

    const url= window.URL.createObjectURL(blob);
    window.open(url,'_blank');
    fileSaver.saveAs(blob,data.headers.get('Content-Disposition'));

  }, error => {
      console.log("error");
      this.toastr.error('Failed Downoad','Danger');
  });


  }


  
}
