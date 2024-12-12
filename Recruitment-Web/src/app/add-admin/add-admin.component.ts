import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Adminmodel } from '../Model/adminmodel';
import { FormsModule } from '@angular/forms';
import { AdminServicesService } from '../services/admin-services.service';
import {  Usermodel } from '../Model/usermodel';
import { Router } from '@angular/router';
import { CommonServiceProvider } from '../services/common.service';
import { ToastrService } from 'ngx-toastr';
import { trigger, state, style, animate, transition } from '@angular/animations';



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
    ])
],
  selector: 'app-add-admin',
  templateUrl: './add-admin.component.html',
  styleUrls: ['./add-admin.component.css','./add-admin.component.scss']
})
export class AddAdminComponent implements OnInit {

  selectedFile: File | null = null;
  displayedFilePath: string;
  fileList3: any;
  isCardBodyVisible = false;
  iscandidateCardBodyVisible = true;
  userobj : Usermodel = new Usermodel();

  constructor(private http: HttpClient,private toastr: ToastrService, private serviceadmin : AdminServicesService,private router: Router,private commonServiceProvider: CommonServiceProvider) { }

  ngOnInit(): void {
  }

  addadmin(){
    this.serviceadmin.addAdmin(this.userobj).subscribe((item: any) => {
      if (item.code.toLowerCase() == "success") 
      {
        this.toastr.success(item.message, 'Success');
        this.router.navigate(['/profile']);
      } 
      else 
      {
        this.toastr.error(item.message, 'Error');
      }
    }, error => {
    }, () => {
      console.log("finally")
    });
  }

  onFileSelected(event: any): void {
    const fileInput = event.target;
    if (fileInput.files && fileInput.files.length > 0) {
      this.displayedFilePath = fileInput.files[0].name;
    } else {
      this.displayedFilePath = 'Choose file';
    }
    $(".tempFileLabel").css("display", "block");
    $(".originalFileLabel").css("display", "none");
    $(".tempFileLabel").text(this.displayedFilePath);

    const formData = new FormData();
    this.selectedFile = event.target.files[0];
    this.fileList3 = event.target.files;
    if (this.fileList3[0].size > 10485760) {
      this.fileList3 = null;
    } else {

      formData.append('file', this.selectedFile, this.selectedFile.name);
      this.userobj.filedata = formData;

      let reader = new FileReader();
      let file = this.fileList3[0];
      reader.readAsBinaryString(file);
      var byteString;
      reader.onload = (file) => {
        let temp = btoa(reader.result.toString());
       if(temp =="" || temp ==null || temp == undefined){
       }else{
        this.userobj.file = temp;
        this.userobj.fileName = this.fileList3[0].name;   
        if(this.fileList3[0].name.length>50){
          $("#fileUploader").text(this.fileList3[0].name.substring(0,30)+ "........");
        }else{
          $("#fileUploader").text(this.fileList3[0].name);
        }
       }
      };
    }
  }

  addcandidate(){
      if (this.selectedFile) {
      this.serviceadmin.upload(this.userobj.filedata).subscribe(
        (response) => {
          console.log('File uploaded successfully:', response);
          this.toastr.success('File Uploaded Sucessfully', 'Success');
          this.backToUser();
        },
        (error) => {
          console.error('Error uploading file:', error);
          this.toastr.error('File Uploaded Unucessfully', 'Error');
        }
      );
    }
  }

  addindividualcandidate(){
    this.serviceadmin.addIndividualCandidate(this.userobj).subscribe((item: any) => {
      if (item.code.toLowerCase() == "success") 
      {
        this.toastr.success(item.message, 'Success');
        this.backToUser();
      } 
      else 
      {
        this.toastr.error(item.message, 'Error');
      }
    }, error => {
    }, () => {
      console.log("finally")
    });
  }

  showaddcandidate(){
    $('#addModal').modal({
      backdrop: false
    }); 
    $("#addModal").modal("show"); 
  }

  closeadd(){
    $("#addModal").modal("hide"); 
  }

  backToUser() {
    this.router.navigate(['/adminlanding']);
  }

  toggleCardBody() {
    this.isCardBodyVisible = !this.isCardBodyVisible;
  }

  togglecandidateCardBody() {
    this.iscandidateCardBodyVisible = !this.iscandidateCardBodyVisible;
  }

}
