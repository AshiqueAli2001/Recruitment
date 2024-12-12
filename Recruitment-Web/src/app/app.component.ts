import { Component } from '@angular/core';
import { Route, Router } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { AuthService } from './auth.service';

interface SideNavToggle{
  ScreenWidth: number;
  collapsed: boolean;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {

  constructor(private router: Router,private auth: AuthService){}

  isLoginPage(): boolean {
    return this.router.url === '/login';
  }

  isUserLoggedIn(): boolean{
    return this.router.url === '/candidatelanding' ;
  }

  testlog(): boolean{
    return this.router.url === '/test';
  }

  testcode(): boolean{
    return this.router.url === '/test-code';
  }

  thankyou(): boolean{
    return this.router.url === '/thankYou';
  }

  isSideNavCollapsed = false;
  screenwidth = 0;

  onToggleSideNav(data: SideNavToggle): void{
    this.screenwidth=data.ScreenWidth;
    this.isSideNavCollapsed=data.collapsed;
  }

  title = 'Recruitment';
}
