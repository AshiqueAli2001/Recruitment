import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppConfigurationService } from './app.configuration.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpService {

  constructor(private _app_config_service: AppConfigurationService, private http: HttpClient) { }

  public add<T>(url: string, params?: any): Observable<T> {
    return this.http.post<T>(this._app_config_service.getRemoteServiceBaseUrl() + url, JSON.stringify(params));
}

public delete<T>(url: string, id: any): Observable<T> {
  return this.http.delete<T>( url +"/"+ id);
}

public updatee<T>(url: string, itemToUpdate: any): Observable<T> 
{
  return this.http.get<T>( url +"/"+ itemToUpdate);
}

public update<T>(url: string, itemToUpdate: FormData): Observable<T> {
  return this.http.post<T>( url, itemToUpdate);
}

public login<T>(url: string, userObj:any): Observable<Object> {
  return this.http.post<T>(url,  userObj);
}
public getCurrentUser<T>(url: string): Observable<Object> {

  return this.http.get<T>(url);
}
public compileCode<T>(url: string, requestBody:any): Observable<T> {
  
  return this.http.post<T>(url, requestBody);
}


}
