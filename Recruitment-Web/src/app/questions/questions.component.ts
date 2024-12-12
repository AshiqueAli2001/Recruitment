import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Questionmodel } from '../Model/questionmodel';
import { FormsModule } from '@angular/forms';
import { AdminServicesService } from '../services/admin-services.service';
import { ToastrService } from 'ngx-toastr';
import { Codemodel } from '../Model/codemodel';
import { ViewQuestionComponent } from '../view-question/view-question.component';
import { AuthService } from '../auth.service';


declare var $: any;
@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css','./questions.component.scss']
})
export class QuestionsComponent implements OnInit {

  questionobj : Questionmodel = new Questionmodel();
  codeobj : Codemodel = new Codemodel();
  currentAdminId: string;
  selectedCorrectAnswer : string;

  constructor(private router: Router, private serviceadmin : AdminServicesService,private toastr: ToastrService,private authService: AuthService) { }

  ngOnInit(): void {

    const storedUser = this.authService.getUser();

   
      this.currentAdminId = storedUser.userId;
   
  }

  viewques() {
    this.router.navigate(['/allques']);
  }

  viewcodeques(){
    this.router.navigate(['/codeques']);
  }

  addmcqmodal(){
    $('#addmcqModal').modal({
      backdrop: false
    }); 
    $("#addmcqModal").modal("show"); 
  }

  closeaddmcq(){
    $("#addmcqModal").modal("hide");
  }

  addcodemodal(){
    $('#addcodeModal').modal({
      backdrop: false
    }); 
    $("#addcodeModal").modal("show"); 
  }

  closeaddcode(){
    $("#addcodeModal").modal("hide");
  }

  onCorrectAnswerChange(): void {
    this.selectedCorrectAnswer = this.questionobj[this.questionobj.correctAnswer];
  }

  addmcq(){
    this.questionobj.userId=this.currentAdminId;
    this.questionobj.correctAnswer=this.selectedCorrectAnswer;
    this.serviceadmin.addquestion(this.questionobj).subscribe((item: any) => {
      if (item.code.toLowerCase() == "success") 
      {
        this.toastr.success(item.message, 'Success');
        this.clear();
      } 
      else 
      {
        this.toastr.error(item.message, 'Failed');
      }
    }, error => {
    }, () => {
      console.log("finally")
    });

  }

  addcode(){
    // this.codeobj.type="Coding";
    this.codeobj.userId=this.currentAdminId;
    this.serviceadmin.addcodequestion(this.codeobj).subscribe((item: any) => {
      if (item.code.toLowerCase() == "success") 
      {
        this.toastr.success(item.message, 'Success');
        this.clear();
      } 
      else 
      {
        this.toastr.error(item.message, 'Failed');
      }
    }, error => {
    }, () => {
      console.log("finally")
    });
  }

  clear() {
    $('#textarea').val('');
    $('#category').val('');
    $('#option1').val('');
    $('#option2').val('');
    $('#option3').val('');
    $('#option4').val('');
    $('#correctans').val('');
    $('#difficulty').val('');
    $('#textarea').val('');
    $('#testcase1').val('');
    $('#expectedoutput1').val('');
    $('#testcase2').val('');
    $('#expectedoutput2').val('');
    $('#testcase3').val('');
    $('#expectedoutput3').val('');
    $('#testcase4').val('');
    $('#expectedoutput4').val('');
    $('#testcase5').val('');
    $('#expectedoutput5').val('');
  }

}
