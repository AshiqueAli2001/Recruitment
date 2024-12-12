import { HttpService } from './services/http.service';
import { HttpClient } from '@angular/common/http';
// auth.service.ts
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConfigurationService } from './services/app.configuration.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  user: string;
  pass: string;

  constructor(private http:HttpClient,private httpService: HttpService,private appconfigurationservice : AppConfigurationService) { }


  public login(loginUser: any): Observable<any> {
    return Observable.create((observer) => {
        this.httpService.login<any>(this.appconfigurationservice.getApiService("login").toString(), loginUser)
            .subscribe((tokenData: any) => {
                observer.next(tokenData);
            }, (error) => {
                observer.error(error)
            },
                () => {
                    observer.complete()
                });
    });

     
    }
  public getCurrentUser(): Observable<any> {
    return Observable.create((observer) => {
        this.httpService.getCurrentUser<any>(this.appconfigurationservice.getApiService("getCurrentUser").toString())
            .subscribe((tokenData: any) => {
                observer.next(tokenData);
            }, (error) => {
                observer.error(error)
            },
                () => {
                    observer.complete()
                });
    });

     
    }


  public loginUser(token:any){
    localStorage.setItem('token',token);
    return true;
  }

  public isLoggedIn(){
      let tokenStr = localStorage.getItem('token');
      if(tokenStr==undefined || tokenStr==''|| tokenStr==null){
        return false;
      }
      else{
        return true;
      }
    }


  public logout(){
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    return true;
  }

  public getToken(){
    return localStorage.getItem('token')||'';
  }

  public setUser(user:any){
    localStorage.setItem('user',JSON.stringify(user));
  }

  public getUser(){
    let userStr=localStorage.getItem('user');
    if(userStr!=null){
      return JSON.parse(userStr);
    }
    else{
      this.logout();
      return null;
    }
  }

  public setCriteria(user:any){
    localStorage.setItem('criteria',JSON.stringify(user));
  }

  public getCriteria(){
    let userStr=localStorage.getItem('criteria');
    return JSON.parse(userStr);
  }


  public getUserRole(){
    let user = this.getUser();
 
    return user.role;
  }
}














  // login(username: string, password: string): boolean {

  //   this.user=username;
  //   this.pass=password;

    

  //   if(username=="admin" && password=="admin")
  //   {
  //     return true;
  //   }
  //   else if(username=="user" && password=="user")
  //   {
  //     return false;
  //   }
  //   else
  //   {
  //     return false;
  //   }
  // }

  // role(): boolean
  // {
  //   if(this.user=="admin")
  //   {
  //     return false;
  //   }
  //   else{
  //     return true;
  //   }
  // }


