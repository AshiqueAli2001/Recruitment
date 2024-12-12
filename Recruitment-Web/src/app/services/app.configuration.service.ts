import { Injectable } from "@angular/core";
import { ApiServicesHandlerService } from "../shared/app-constants";

@Injectable({ providedIn: 'root' })

export class AppConfigurationService 
{

  private envName: number;
  
//   private readonly _app_config: IAppConfiguration;
//   private _api_services: any;
//   private envName: number;

  constructor() 
  {
    // this.envName = environment.DEV;
  }

  getRemoteServiceBaseUrl() 
  {
    return ApiServicesHandlerService._app_config[this.envName].apiServices.remoteServiceBaseUrl;
  }

  getApiService(value) 
  {
    return ApiServicesHandlerService._api_services[value].api;
  }
  
}
