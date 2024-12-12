import { AfterViewInit, ElementRef } from '@angular/core';
import * as CodeMirror from 'codemirror';
import 'codemirror/mode/clike/clike';
import 'codemirror/theme/dracula.css';
import 'codemirror/addon/edit/closebrackets';
import { CompileService } from '../services/compile.service';
import { Codemodel } from '../Model/codemodel';
import { HttpClient } from '@angular/common/http';
// import { Codermodel } from '../Model/codermodel';
import { CandidateServicesService } from '../services/candidate-service.service';
import { Component } from '@angular/core';
import { resultDTO } from '../Model/resultDto';
import { interval } from 'rxjs';
import { Resultmodel } from '../Model/resultmodel';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';


declare var $:any;



@Component({
  selector: 'app-test-code',
  templateUrl: './test-code.component.html',
  styleUrls: ['./test-code.component.css'],
})
export class TestCodeComponent implements AfterViewInit {
  questions: Codemodel[] = [];
  result:resultDTO[]=[];
  finalresult:Resultmodel[]=[];
  // code: Codermodel[] = [];
  editor: CodeMirror.Editor;
  currentQuestionIndex: number = 0;
  statusCounter: number = 0;
  codequestions: any[] = [];
  timerValue: number
  criteriaId: number;
  interval: any;  totalQuestions: number;
  JSON: any;
  currentUserId:string;
  currentQuestion:Codemodel;
  // interval: NodeJS.Timeout;
  



  constructor(
    private elementRef: ElementRef,
    private compileService: CompileService,
    private http: HttpClient,
    private authService: AuthService,
    private candidateservice: CandidateServicesService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const storedUser = this.authService.getUser();

   
      this.currentUserId = storedUser.userId;
    // this.startTimer();  
    this.totalQuestions = this.questions.length;
    this.getques();
    this.gettime();
    this.startTimer();
  }

  getques() {
    this.criteriaId = this.authService.getCriteria();
    this.candidateservice.getCodeQuestions(this.criteriaId).subscribe(
      (data) => {
        this.codequestions = data;
        this.totalQuestions = this.codequestions.length;
        this.setQuestionData(this.currentQuestionIndex);
      },
      (error) => {
        console.error('Error fetching questions:', error);
      }
    );
  }

  ngAfterViewInit(): void {
    console.log('Initializing CodeMirror');
    this.editor = CodeMirror.fromTextArea(
      this.elementRef.nativeElement.querySelector('#editor'),
      {
        mode: 'text/x-c++src',
        theme: 'dracula',
        lineNumbers: true,
        autoCloseBrackets: true,
      }
    );

    const width = window.innerWidth;
    this.editor.setSize(0.417 * width, 450);

    this.compileService.getAllCaseData().subscribe(
      (response: Codemodel[]) => {
        this.questions = response;
        this.setQuestionData(this.currentQuestionIndex);
      },
      (error) => {
        console.error('Error loading test cases from backend:', error);
      }
    );

    const runButton = document.getElementById('run');
    const languageSelect = document.getElementById('inlineFormSelectPref') as HTMLSelectElement;

    runButton.addEventListener('click', () => this.runCode());
    languageSelect.addEventListener('change', () => {
      if (languageSelect.value === 'Java') {
        this.editor.setOption('mode', 'text/x-java');
      } else if (languageSelect.value === 'Python') {
        this.editor.setOption('mode', 'text/x-python');
      } else {
        this.editor.setOption('mode', 'text/x-c++src');
      }
    });

    // this.questions.forEach((question) => this.checkTestCases(question));
  }

  setQuestionData(index: number): void {
    
    this.currentQuestion = this.codequestions[index];
    this.setInputValue('input1', this.currentQuestion.testCase1);
    this.setInputValue('expected-output-test1', this.currentQuestion.expected1);
    this.setInputValue('input2', this.currentQuestion.testCase2);
    this.setInputValue('expected-output-test2', this.currentQuestion.expected2);
    this.setInputValue('input3', this.currentQuestion.testCase3);
    this.setInputValue('expected-output-test3', this.currentQuestion.expected3);
    this.setInputValue('input4', this.currentQuestion.testCase4);
    this.setInputValue('expected-output-test4', this.currentQuestion.expected4);
    this.setInputValue('input5', this.currentQuestion.testCase5);
    this.setInputValue('expected-output-test5', this.currentQuestion.expected5);
  }


  nextButtonClick(): void {
    this.submit();
    if (this.currentQuestionIndex < this.questions.length - 1) {
      // Reset the badges to "Pending"
      this.resetBadges();
      this.statusCounter = 0;
         // Clear the editor
      this.editor.setValue('');
      // Move to the next question
      this.currentQuestionIndex++;
      this.setQuestionData(this.currentQuestionIndex);
      // this.submit();
    }
  }

  

  submit() {


    const code = this.editor.getValue();
    const questionId = this.codequestions[this.currentQuestionIndex].questionId;
    const userId = this.currentUserId
  
    // Assuming you have a way to determine the test case status, adjust this accordingly
    const testCase1Status = this.getTestCaseStatus('test1');
    const testCase2Status = this.getTestCaseStatus('test2');
    const testCase3Status = this.getTestCaseStatus('test3');
    const testCase4Status = this.getTestCaseStatus('test4');
    const testCase5Status = this.getTestCaseStatus('test5');

    
  
    // Call your service method here
    const resultDTO: resultDTO =  
    {
    resultId:null,
    userId:userId,
    questionId:questionId,
    testCase1Status:testCase1Status,
    testCase2Status:testCase2Status,
    testCase3Status:testCase3Status,
    testCase4Status:testCase4Status,
    testCase5Status:testCase5Status,
    selectedOption:null,
    code:code,
    }

    
    
    this.compileService.addCodingResults(resultDTO).subscribe(
      (response: resultDTO) => {
        // Handle the response if needed
        console.log(response);
      },
      (error) => {
        // Handle errors if any
        console.error(error);
      }
    );
  }

  finalSubmit(): void {
     this.submit();
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





  finalSubmitModal(){
    $('#confirmation-modal').modal({
      backdrop: false
    });
    $("#confirmation-modal").modal("show");
  }

  closemodal()
  {
    $("#confirmation-modal").modal("hide");
  }



  getTestCaseStatus(testId: string): string {
    const statusBadge = document.getElementById('status-badge-' + testId);
    return statusBadge.innerText.toUpperCase();  // Assuming the status is 'success' or 'failed'
  }


  resetBadges() {
    const testIds = ['test1', 'test2', 'test3','test4','test5'];
  
    testIds.forEach((testId) => {
      const statusBadge = document.getElementById('status-badge-' + testId);
      statusBadge.classList.remove('bg-success', 'bg-danger');
      statusBadge.classList.add('bg-secondary');
      statusBadge.innerText = 'Pending';
    });
  }

  previousButtonClick(): void {
    if (this.currentQuestionIndex > 0) {
      this.currentQuestionIndex--;
      this.setQuestionData(this.currentQuestionIndex);
    }
  }

  async getActualOutput(code: string, questionId:number,language: string): Promise<string> {
    try {
      const response = await this.compileService.compileCode(code, questionId,language).toPromise();
      return response.output;
    } catch (error) {
      console.error('Error during compilation:', error);
      return 'Compilation error';
    }
  }

  async runCode() {
    $('#loading-spinner').show();
    this.resetBadges();
    this.statusCounter = 0;
    await this.copyInputDataAndRun('input1');
    const code = this.editor.getValue();
    const input = (document.getElementById('input') as HTMLInputElement).value;
    const questionId = this.codequestions[this.currentQuestionIndex].questionId;
    const language = (document.getElementById('inlineFormSelectPref') as HTMLSelectElement).value;

    // try {
    //   const response = await this.compileService.compileCode(code, questionId, language).toPromise();
    //   console.log("Response from backend:", response); // Log the response to the console

    //   const outputElement = document.getElementById('output') as HTMLTextAreaElement;
    //   outputElement.value = response.output;

    //   this.updateTestCaseStatus('test1', response.output);
    //   this.updateTestCaseStatus('test2', response.output);
    //   this.updateTestCaseStatus('test3', response.output);
    // } catch (error) {
    //   console.error('Error during compilation:', error);
    // }
}
//set input field of test case
setInputValue(inputId: string, value: string) {
  const inputElement = document.getElementById(inputId) as HTMLTextAreaElement;
  if (inputElement) {
    inputElement.value = value;
  }
}


  async copyInputDataAndRun(inputId: string) {
    await this.copyInputData(inputId);

    const code = this.editor.getValue();
    // const input = (document.getElementById('input') as HTMLInputElement).value;
    const questionId = this.codequestions[this.currentQuestionIndex].questionId;
    const language = (document.getElementById('inlineFormSelectPref') as HTMLSelectElement).value;

    try {
      const response = await this.compileService.compileCode(code,questionId, language).toPromise();
      // const outputElement = document.getElementById('output') as HTMLTextAreaElement;
      // outputElement.value = response.output;

      this.updateTestCaseStatus('test1', response);
      this.updateTestCaseStatus('test2', response);
      this.updateTestCaseStatus('test3', response);
      this.updateTestCaseStatus('test4', response);
      this.updateTestCaseStatus('test5', response);

    } catch (error) {
      console.error('Error during compilation:', error);
    }
    finally {
      $('#loading-spinner').hide(); // Hide the spinner after compilation is complete
    }
  }

  updateTestCaseStatus(testId, response) {
    const statusBadge = document.getElementById('status-badge-' + testId);
  
    console.log('Result:', response);
  
    if (statusBadge.innerText === 'PASS') {
      return;
    }
  
    const result = response[`Test Case ${testId.charAt(testId.length - 1)}`];
  
    // Assuming result contains "PASS" or "FAIL"
    if (result.trim() === 'PASS') {
      // Test case passed
      statusBadge.classList.remove('bg-secondary', 'bg-danger');
      statusBadge.classList.add('bg-success');
      statusBadge.innerText = 'PASS';
      this.statusCounter++;
    } else {
      // Test case failed
      statusBadge.classList.remove('bg-secondary', 'bg-success');
      statusBadge.classList.add('bg-danger');
      statusBadge.innerText = 'FAIL';
    }
  
    // Add the following lines to set the color to green for PASS and red for FAIL
    if (result.trim() === 'PASS') {
      statusBadge.style.color = '#155724'; // Set green color for PASS
    } else {
      statusBadge.style.color = '#721c24'; // Set red color for FAIL
    }
  }
  
  
  // async checkTestCases(question: Codemodel) {
  //   const selectedLanguage = (document.getElementById('inlineFormSelectPref') as HTMLSelectElement).value;
  //   for (let i = 1; i <= 5; i++) {
  //     const actualOutput = await this.getActualOutput(
  //       this.editor.getValue(),
  //       selectedLanguage,
  //       question[`testCase${i}`]
  //     );
  //     this.updateStatus(question, `testCase${i}`, actualOutput);
  //   }
  // }

  updateStatus(question: Codemodel, testCase: string, actualOutput: string) {
    const expectedOutput = question[`expectedTestCase${testCase.charAt(testCase.length - 1)}`];
    const statusBadge = document.getElementById(`status-badge-${testCase}`);
    if (this.isTestCaseSuccessful(expectedOutput, actualOutput)) {
      statusBadge.textContent = 'PASS';
      statusBadge.classList.remove('bg-danger');
      statusBadge.classList.add('bg-success');
    } else {
      statusBadge.textContent = 'FAIL';
      statusBadge.classList.remove('bg-success');
      statusBadge.classList.add('bg-danger');
    }
  }

  isTestCaseSuccessful(expectedOutput: string, actualOutput: string): boolean {
    return expectedOutput === actualOutput;
  }

  copyInputData(sourceInputId: string) {
    const sourceInput = document.getElementById(sourceInputId) as HTMLTextAreaElement;
    const mainInput = document.getElementById('input') as HTMLTextAreaElement;
    mainInput.value = sourceInput.value;
  }
gettime(){
    this.timerValue=this.candidateservice.getremainingTime();
    console.log("Time is",this.timerValue);
  }

  startTimer(): void {
    this.interval = setInterval(() => {
      this.timerValue--;
      if (this.timerValue === 0) {
        clearInterval(this.interval);
        console.log('Timer expired!');
        this.finalSubmit();
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
  }}