import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CandidateServicesService } from '../services/candidate-service.service';
import { Usermodel } from '../Model/usermodel';
import { AuthService } from '../auth.service';

declare var $: any;

@Component({
  selector: 'app-candidate-landing',
  templateUrl: './candidate-landing.component.html',
  styleUrls: ['./candidate-landing.component.css']
})
export class CandidateLandingComponent implements OnInit {

  
  currentUserId: string;
  currentCriteria : number;
  currentAdmin: Usermodel = new Usermodel();
  

  constructor(private router: Router, private candidateservice: CandidateServicesService,private authService: AuthService) { }

  ngOnInit(): void {
    this.currentAdmin = this.authService.getUser();
    this.currentUserId = this.currentAdmin.userId;
    this.getcriteria();
  }

  startTest(){
    this.router.navigate(['/test']);
    const doc = window.document as any;
    const docEl = doc.documentElement;

    const requestFullScreen = docEl.requestFullscreen || docEl.webkitRequestFullscreen || docEl.msRequestFullscreen;

    if (!doc.fullscreenElement && !doc.webkitFullscreenElement && !doc.msFullscreenElement) {
      requestFullScreen.call(docEl);
    } else {
      doc.exitFullscreen();
    }
  }

  getcriteria(){
    this.candidateservice.getCritera(this.currentUserId).subscribe(
      (data) => {
        this.currentCriteria = data;
        if(data==undefined){
          $('#testwarning-modal').modal({
            backdrop: false
          });
          $("#testwarning-modal").modal("show");
        }
        this.authService.setCriteria(this.currentCriteria);
        console.log("The criteria id is",data);
      },
      (error) => {
        console.error('Error fetching questions:', error);
      }
      );
  }

  closemodal() 
  {
    this.router.navigate(['/login']);
  }

  
  

}
