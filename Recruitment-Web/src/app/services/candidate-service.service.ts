import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppConfigurationService } from './app.configuration.service';
import { HttpService } from './http.service';
import { CommonServiceProvider } from './common.service';
import { Questionmodel } from '../Model/questionmodel';
import { param } from 'jquery';
import { ListFormat } from 'typescript';

@Injectable({
  providedIn: 'root'
})
export class CandidateServicesService {
    
  question: Questionmodel = new Questionmodel();
  timervalue: number=0;

  constructor(private http: HttpClient, private appconfigurationservice : AppConfigurationService,private _httpService: HttpService,private commonServiceProvider: CommonServiceProvider) { }

  getCodingQuestions(criteriaId: number): Observable<any> {
    const url = `${this.appconfigurationservice.getApiService("getCodingQuestions")}/${criteriaId}`;
    return this.http.get<any>(url);
  }
  
  

  getCritera(userId:any): Observable<any> {
    const url = `${this.appconfigurationservice.getApiService("getCriteria")}/${userId}`;
    return this.http.get<any>(url);
  }

  getMcqQuestions(criteriaId: number): Observable<any> {
    const url = `${this.appconfigurationservice.getApiService("getMcqQuestions")}/${criteriaId}`;
    return this.http.get<any>(url);
  }

  getTime(criteriaId: number): Observable<any> {
    const url = `${this.appconfigurationservice.getApiService("gettestduration")}/${criteriaId}`;
    return this.http.get<any>(url);
  }

  getCodeQuestions(criteriaId: number): Observable<any> {
    const url = `${this.appconfigurationservice.getApiService("codequestionview")}/${criteriaId}`;
    return this.http.get<any>(url);
  }

  submitmcq(mcqans: ListFormat[]): Observable<any> {
    return this.http.post(this.appconfigurationservice.getApiService("submitmcq"), mcqans);
  }

 setremainingTime(time: number){
    this.timervalue= time;
  }

  getremainingTime(){
    return this.timervalue;
  }}
