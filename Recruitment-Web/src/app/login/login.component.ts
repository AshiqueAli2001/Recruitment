import { TestService } from './../services/test.service';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';
import { Usermodel } from '../Model/usermodel';
import { AuthService } from '../auth.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css','./login.component.scss']
})
export class LoginComponent {

  username: string = '';
  password: string = '';
  errorMessage: string = '';
  verify: boolean;
  responseData: any;
  body:any={};
  userobj : Usermodel = new Usermodel();

  constructor(private authService: AuthService, private router: Router,private toastr: ToastrService,private testService:TestService) { }
ngOnInit(): void {
  
  this.testService.clearVisitedPages();
  this.authService.logout();
}
  login() {
    this.testService.clearVisitedPages();
    this.authService.logout();
     this.body = {
        "userId":this.username,
        "password" : this.password      
     }
    this.authService.login(this.body).subscribe((item: any) => {

      this.responseData = item;
      if(this.responseData.token==null){
        this.toastr.error(this.responseData.message)
      }
      this.authService.loginUser(this.responseData.token);
      this.authService.getCurrentUser().subscribe(
        (user:any)=>{
          console.log(user);
          this.authService.setUser(user);
          console.log("dsdsdds");



          if(this.authService.getUserRole()=="ADMIN"){
            this.router.navigate(['/adminlanding']);
          }
         else if(this.authService.getUserRole()=="USER"){
            
            this.router.navigate(['/candidatelanding']);
          }else{
            this.authService.logout();
            this.router.navigate(['/login']);
          }


        },error => {
          console.log(error.message);
      }, () => {
      }
      );

    }, error => {
      console.log(error.message);

  }, () => {
  });
  }
}
