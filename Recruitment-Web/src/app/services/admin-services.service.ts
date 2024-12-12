import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppConfigurationService } from './app.configuration.service';
import { HttpService } from './http.service';
import { Questionmodel } from '../Model/questionmodel';
import { CommonServiceProvider } from './common.service';
import { Criteriamodel } from '../Model/criteriamodel';
import { Usermodel } from '../Model/usermodel';
import { Codemodel } from '../Model/codemodel';

@Injectable({
  providedIn: 'root'
})
export class AdminServicesService {
  
  userobj: Usermodel = new Usermodel();
  question: Questionmodel = new Questionmodel();

  constructor(private http: HttpClient, private appconfigurationservice : AppConfigurationService,private _httpService: HttpService,
    private commonServiceProvider: CommonServiceProvider) { }

addAdmin(user: any): Observable<any> {
  return this.http.post(this.appconfigurationservice.getApiService("addadmin"), user);
}


 downloadFile(userId: any):Observable<any>{
  return this.http.get(this.appconfigurationservice.getApiService("downloadFile")+'/'+userId, {
    responseType: 'blob',
    observe: 'response', // Include the full HTTP response

  });

}


getUserById(userId: any):Observable<any>{
  return this.http.get(this.appconfigurationservice.getApiService("getUserById")+'/'+userId);

}

public upload(candidate: FormData):Observable<any> {
  return this.http.post<any>(this.appconfigurationservice.getApiService("addcandidate"), candidate);
}

public addIndividualCandidate(candidate: any):Observable<any> {
  return this.http.post<any>(this.appconfigurationservice.getApiService("addIndividualCandidate"), candidate);
}

addquestion(question: Questionmodel): Observable<any> {
  return this.http.post(this.appconfigurationservice.getApiService("addquestion"), question);
}

addcodequestion(code: Codemodel): Observable<any> {
  return this.http.post(this.appconfigurationservice.getApiService("addcodequestion"), code);
}

public deletequestion(questionId: any): Observable<any> 
{
  return this._httpService.delete(this.appconfigurationservice.getApiService("deletequestion"), questionId);
}

public deletecodequestion(questionId: any): Observable<any> 
{
  return this._httpService.delete(this.appconfigurationservice.getApiService("deletecodequestion"), questionId);
}

public deletecandidate(candidateId: any): Observable<any> 
{
  return this._httpService.delete(this.appconfigurationservice.getApiService("deletecandidate"), candidateId);
}

public addCriteria(criteria: Criteriamodel): Observable<any> {
  return this.http.post(this.appconfigurationservice.getApiService("addcriteria"), criteria);
}

public deletecriteria(criteriaId: any): Observable<any> 
{
  return this._httpService.delete(this.appconfigurationservice.getApiService("deletecriteria"), criteriaId);
}

public deleteadmin(adminId: any): Observable<any> 
{
  return this._httpService.delete(this.appconfigurationservice.getApiService("deleteadmin"), adminId);
}

public UpdateCandidate(candidateobj: any,file: File): Observable<any> 
{
  const formData: FormData = new FormData();
  formData.append('file',file);
  formData.append('itemToUpdate',new Blob([JSON.stringify(candidateobj)], { type: 'application/json' }) );
  return this._httpService.update(this.appconfigurationservice.getApiService("updatecandidate"), formData);
}

public UpdateCriteria(userId: string): Observable<any> 
{
  return this._httpService.updatee(this.appconfigurationservice.getApiService("updatecriteria"), userId);
}
public verifyCandidate(userId: string): Observable<any> 
{
  return this._httpService.updatee(this.appconfigurationservice.getApiService("verifyCandidate"), userId);
}

public uploadimg(userObj: any,file: File): Observable<any> 
{
  const formData: FormData = new FormData();
      formData.append('file',file);
      formData.append('itemToUpdate', new Blob([JSON.stringify(userObj)], { type: 'application/json' }));
  return this._httpService.update(this.appconfigurationservice.getApiService("uploadimg"), formData);
}

}
