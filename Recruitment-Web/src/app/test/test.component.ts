import { AfterViewInit, Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { trigger, transition, animate, style, keyframes, state } from '@angular/animations';
import { CandidateServicesService } from '../services/candidate-service.service';
import { ToastrService } from 'ngx-toastr';
import { AuthService } from '../auth.service';
import { Usermodel } from '../Model/usermodel';
import { CompileService } from '../services/compile.service';
import { Resultmodel } from '../Model/resultmodel';

declare var $: any;

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css'],
  animations: [
    
    trigger('fadeInOut', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('400ms', style({ opacity: 1 })),
      ]),
      transition(':leave', [
        animate('400ms', style({ opacity: 0 })),
      ]),
    ]),

    trigger('slideInOut', [
      transition(':enter', [
        animate('300ms ease-in', keyframes([
          style({ opacity: 0, transform: 'translateX(-100%)', offset: 0 }),
          style({ opacity: 1, transform: 'translateX(0)', offset: 1.0 })
        ])),
      ]),
      transition(':leave', [
        animate('300ms ease-out', keyframes([
          style({ opacity: 1, transform: 'translateX(0)', offset: 0 }),
          style({ opacity: 0, transform: 'translateX(100%)', offset: 1.0 })
        ])),
      ]),
    ]),

    trigger('scaleInOut', [
      transition(':enter', [
        animate('200ms ease-in', keyframes([
          style({ opacity: 0, transform: 'scale(0.5)', offset: 0 }),
          style({ opacity: 1, transform: 'scale(1)', offset: 1.0 })
        ])),
      ]),
      transition(':leave', [
        animate('200ms ease-out', keyframes([
          style({ opacity: 1, transform: 'scale(1)', offset: 0 }),
          style({ opacity: 0, transform: 'scale(0.5)', offset: 1.0 })
        ])),
      ]),
    ]),

    trigger('cardAnimation', [
      transition(':enter', [
        style({ opacity: 0, transform: 'translateY(-20px)' }),
        animate('300ms ease-out', style({ opacity: 1, transform: 'translateY(0)' })),
      ]),
      transition(':leave', [
        animate('300ms ease-in', style({ opacity: 0, transform: 'translateY(20px)' })),
      ]),
    ]),

    trigger('zoom', [
      transition(':enter', [
        style({ opacity: 0, transform: 'scale(0.8)' }), 
        animate('300ms ease-out', style({ opacity: 1, transform: 'scale(1.8)' })), 
      ]),
      transition(':leave', [
        animate('300ms ease-in', style({ opacity: 0, transform: 'scale(0.8)' })), 
      ]),
    ]),
  ],
  
})
export class TestComponent implements OnInit {
 
  interval: any;
  notselected: number;
  currentCriteria : number;
  currentUserId: string;
  criteriaId: number;
  timerValue: number;
  totalQuestions: number;
  questions: any[] = [];
  testduration : number;
  selectedOptions: any[] = [];
  questionsCheckedLater: boolean[] = Array(this.questions.length).fill(false);
  @ViewChild('questionContainer') questionContainer: ElementRef;
  currentAdmin: Usermodel = new Usermodel();
currentAdminId: string;


  constructor(private router:Router, private candidateservice: CandidateServicesService,
    private compileService: CompileService,private toastr: ToastrService,private authService: AuthService) { }

  currentQuestionIndex = 0; 
  scrollPosition: number = 0;
  cardHeight: number = 0;
  selectedOptionIndex: number;

  ngOnInit(): void {
    this.currentAdmin = this.authService.getUser();
    this.currentUserId = this.currentAdmin.userId;

   
    this.currentAdminId = this.currentAdmin.userId;
    // this.getcriteria(); 
    this.currentCriteria = this.authService.getCriteria();
    this.gettime();
    
    this.startTimer(); 
    // this.totalQuestions = this.questions.length;
  }
    
    getques(){
      // this.criteriaId=this.currentCriteria;
      this.candidateservice.getMcqQuestions(this.currentCriteria).subscribe(
        (data) => {
          this.questions = data;
          // console.log(this.questions)
          this.totalQuestions = this.questions.length;
        },
        (error) => {
          console.error('Error fetching questions:', error);
        }
        );
      }

      gettime(){
        // this.criteriaId=1;
        this.getques();
        this.candidateservice.getTime(this.currentCriteria).subscribe(
          (data) => {
        this.testduration = data;
        this.timerValue = this.testduration*60;
        console.log("Time",this.testduration)
      },
      (error) => {
        console.error('Error fetching questions:', error);
      }
    );
  }

  goToTestCode() {
    this.router.navigate(['/test-code']);
  }

  nextQuestion(): void {
      if(this.currentQuestionIndex!=this.totalQuestions-1){
        this.currentQuestionIndex++;
        if (!this.selectedOptions[this.currentQuestionIndex]) {
          this.selectedOptions[this.currentQuestionIndex] = null;
          // console.log(this.selectedOptions);
        }
      }else {
        console.log('End of Questions!');
      }
  }

  previousQuestion(): void {
    if(this.currentQuestionIndex==0){
      console.log('End of Questions!');
    }else {
      this.currentQuestionIndex--;
      if (!this.selectedOptions[this.currentQuestionIndex]) {
        this.selectedOptions[this.currentQuestionIndex] = null;
      }
    }
  }

  clearOptions(questionIndex: number): void {
    this.selectedOptions[questionIndex] = null;
    const questionIcons = document.querySelectorAll('.question-icon');
    questionIcons[questionIndex].classList.remove('attempted');
    questionIcons[questionIndex].classList.add('not-attempted');
  }
   
  goToQuestion(index: number) {
    console.log(index);
  }

  startTimer(): void {
    this.interval = setInterval(() => {
      this.timerValue--;
      if (this.timerValue === 0) {
        clearInterval(this.interval);
        console.log('Timer expired!');
        this.submitTest();
        this.finalSubmit();
      }
      if(this.timerValue==30){
        $('#timewarning-modal').modal({
          backdrop: false
        });
        $("#timewarning-modal").modal("show");
        setTimeout(() => {
          $("#timewarning-modal").modal("hide");
        }, 5000);
      }
    }, 1000);
  }

  formatTime(seconds: number): string {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${this.pad(minutes)}:${this.pad(remainingSeconds)}`;
  }

  pad(value: number): string {
    return value < 10 ? `0${value}` : `${value}`;
  }

  calculateProgress(): number {
    const totalQuestions = this.questions.length;
    return (this.currentQuestionIndex + 1) / totalQuestions * 100;
  }

  checkLater(i: number): void {
    this.questionsCheckedLater[i] = !this.questionsCheckedLater[i];
  }

  optionSelected(i: number): void {
    this.questionsCheckedLater[i] = false;
  }

  submit(){
    this.notselected=0;
    for (let i = 0; i < this.questions.length; i++) {
      const questionId = this.questions[i].questionId;
      const selectedOption = this.selectedOptions[i];
  
      if (selectedOption == null && selectedOption == undefined) {
        this.notselected = this.notselected+1;
      }
    }
    if(this.notselected>0){
      $('#confirmation-modal').modal({
        backdrop: false
      });
      $("#confirmation-modal").modal("show");
    }
    else{
      this.submitTest();
    }
    
  }

  submitTest(): void {
    this.candidateservice.setremainingTime(this.timerValue);
    const userId = this.currentUserId;
    const submissionData = [];
    
    for (let i = 0; i < this.questions.length; i++) {
      const questionId = this.questions[i].questionId;
      const selectedOption = this.selectedOptions[i];
      
      // if (selectedOption !== null && selectedOption !== undefined) {
        submissionData.push({
          questionId,
          selectedOption
        });
        // }
      }
      submissionData.forEach(item => {
        item.userId = userId;
      });
      
      console.log(submissionData);
      
      this.candidateservice.submitmcq(submissionData).subscribe(
        (response) => {
          // this.router.navigate(['/test-code']);
          this.toastr.success('MCQ Submited', 'Success');
          console.log('Submission successful:', response);
        },
        (error) => {
          console.error('Error submitting test:', error);
        }
        );
        // this.router.navigate(['/test-code']);
        this.router.navigate(['/test-code']);
  }

  finalSubmit(): void {
    const userId = this.currentUserId;
   const finishingTime = Math.floor(this.timerValue / 60);
   const criteriaId = 1; // Replace with the actual criteria ID
   this.compileService.addFinalResult(userId, finishingTime, criteriaId).subscribe(
     (response: Resultmodel) => {
       // Handle the response if needed
       console.log(response);
     },
     (error) => {
       // Handle errors if any
       console.error(error);
     }
   );

   this.router.navigate(['/thankYou']);
  }

  closemodal() 
  {
    $("#confirmation-modal").modal("hide");
  }
  
  ngOnDestroy(): void {
    //Called once, before the instance is destroyed.
    //Add 'implements OnDestroy' to the class.
  }
  
}
