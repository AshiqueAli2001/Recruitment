import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Codemodel } from '../Model/codemodel';
import { resultDTO } from '../Model/resultDto';
import { AppConfigurationService } from './app.configuration.service';
import { HttpService } from './http.service';
import { CommonServiceProvider } from './common.service';
import { Resultmodel } from '../Model/resultmodel';
interface CompilationResponse {
  output: string;
  // Add other properties if needed
}
interface ServiceResponse{
  output:string;
}
@Injectable({
  providedIn: 'root',
})
export class CompileService {
  constructor(private http: HttpClient, private appconfigurationservice : AppConfigurationService,private _httpService: HttpService,
    private commonServiceProvider: CommonServiceProvider) { }
    
    
    
    private apiUrl = 'http://localhost:8060/candidate/compile';  

  private baseUrl = 'http://localhost:8060/candidate/code'; 

  private codeUrl = 'http://localhost:8060/candidate/addCodingResults'; 

  private submitUrl='http://localhost:8060/candidate/addFinalResult';

  compileCode(code: string, questionId:number, language: string): Observable<CompilationResponse> {
    console.log("Sending request to backend");
    const requestBody = {
      code: code,
      questionId:questionId,
      language: language,
    };
    return this.http.post<CompilationResponse>(this.apiUrl, requestBody);
  }

  getAllCaseData(): Observable<Codemodel[]> {
    return this.http.get<Codemodel[]>(this.baseUrl);
  }

  addCodingResults(resultDTO: resultDTO): Observable<resultDTO> {
    return this.http.post<resultDTO>(`${this.codeUrl}`, resultDTO);
  }

  
  addFinalResult(userId: string, finishingTime: number, criteriaId: number): Observable<Resultmodel> {
    // const body = { userId, finishingTime, criteriaId };
    return this.http.get<Resultmodel>(`${this.submitUrl}/${userId}/${finishingTime}/${criteriaId}`);
  }


}