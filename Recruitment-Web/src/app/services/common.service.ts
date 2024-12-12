import { Injectable } from '@angular/core';
import { HttpService } from './http.service';
import { Observable, BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';
declare var $: any;
@Injectable({ providedIn: 'root' })
export class CommonServiceProvider {
    public isLoading = new BehaviorSubject(false);
    public LoadActivity = new BehaviorSubject("");
    mapperJsonData: any;
    constructor(private router: Router, private _httpService: HttpService) {

    }
    savedToast: any = {};
    toastPosition = "";
    toastType = "";
    userInfo: any = {};
    customStompClient: any;
    localMessages: any = {};
    menus: any = [];
    public static CutOfList: any = [
        { id: 1, name: "Single" },
        { id: 2, name: "Bulk" },
    ];
    public static statusList: any = [
        { code: "VERIFIED", display: "VERIFIED" },
        { code: "DEFFERED", display: "DEFFERED" },
        { code: "PROCESSD", display: "PROCESSED" },
        { code: "SUCCESS", display: "SUCCESS" },
        { code: "DELETED", display: "DELETED" },
        { code: "DELETE", display: "DELETE" },
        { code: "FAILED", display: "FAILED" },
        { code: "DRAFT", display: "DRAFT" },
        { code: "GENERATED", display: "GENERATED" },
        { code: "RECEIVED", display: "RECEIVED" },
        { code: "REPAIR", display: "REPAIR" },
        { code: "NEW", display: "NEW" },
        { code: "APPROVED", display: "APPROVED" },
        { code: "REJECT", display: "REJECT" },
        { code: "CANCEL", display: "CANCEL" },
        { code: "REFUND", display: "REFUND" }];

    public static statusListEnq: any = [
        { code: "VERIFIED", display: "Approved" },
        { code: "PROCESSD", display: "Pending Approval" },
        { code: "SUCCESS", display: "Success" },
        { code: "DELETED", display: "Deleted" },
        { code: "DELETE", display: "Pending Delete Approval" },
        { code: "FAILED", display: "Failed" },
        { code: "DRAFT", display: "Draft" },
        { code: "GENERATED", display: "Generated" },
        { code: "RECEIVED", display: "Received" },
        { code: "REPAIR", display: "Pending Correction" },
        { code: "NEW", display: "New" },
        { code: "REJECT", display: "Rejected" }];

    public static statusListCommon: any = [
        { code: "VERIFIED", display: "Approved" },
        { code: "PROCESSD", display: "Pending Approval" },
        { code: "DELETED", display: "Deleted" },
        { code: "DELETE", display: "Pending Delete Approval" },
        { code: "REPAIR", display: "Pending Correction" },
        { code: "REJECT", display: "Rejected" }];



    public static payTypeList: any = [
        { code: "I", display: "Incoming Payment" },
        { code: "O", display: "Outgoing Payment" }];

    public static direction: any = [
        { code: "I", display: "Incoming" },
        { code: "O", display: "Outgoing" }];

    public getDrection(): any {
        return CommonServiceProvider.direction;
    }

    public static crossCurrency: any = [
        { code: "1", display: "STP" },
        { code: "2", display: "Repair" },
        { code: "3", display: "Reject" }];

    public getCrossCurrencyList(): any {
        return CommonServiceProvider.crossCurrency;
    }


    public static possibleDuplicate: any = [
        { code: "R", display: "Reject Payment" },
        { code: "S", display: "Send To Repair" },
        { code: "A", display: "Send Anyway" },
        { code: "P", display: "Send With Tag" }];

    public getPosiibleDuplicateList(): any {
        return CommonServiceProvider.possibleDuplicate;
    }

    public static debitComputation: any = [
        { code: "P", display: "On Value Date" },
        { code: "V", display: "On Process Date" }];

    public getDebitComputationList(): any {
        return CommonServiceProvider.debitComputation;
    }

    public static postCutOff: any = [
        { code: "P", display: "Repair Queue" },
        { code: "C", display: "Change Value Date" },
        { code: "R", display: "Reject Payment" }];

    public getPostCutOffList(): any {
        return CommonServiceProvider.postCutOff;
    }

    public static yesOrNo: any = [
        { code: "Y", display: "Yes" },
        { code: "N", display: "No" }];

    public getYesOrNo(): any {
        return CommonServiceProvider.yesOrNo;
    }


    public static crdrFlagList: any = [
        { code: "C", display: "Credited" },
        { code: "D", display: "Debited" }];

    public static runningStatList: any = [
        { code: "STOPPED", display: "STOPPED" },
        { code: "STARTING", display: "STARTING" },
        { code: "STARTED", display: "STARTED" },
        { code: "STOPPING", display: "STOPPING" }];

    public static benStandInsStatList: any = [
        { code: "VERIFIED", display: "Approved" },
        { code: "PROCESSD", display: "Pending Approval" },
        // { code: "DELETED", display: "Deleted" },
        { code: "REJECT", display: "Rejected" }
    ];

    public static statusListLiqMangParm: any = [
      { code: "VERIFIED", display: "Approved" },
      { code: "PROCESSD", display: "Pending Approval" },
      { code: "DELETED", display: "Deleted" },
    ];

    public static statusSadadBillerData: any = [
      { code: "VERIFIED", display: "Approved" },
      { code: "PROCESSD", display: "Pending Approval" },
      { code: "DELETE", display: "Delete Approval" },
    ];

    months = [
        { id: "1", value: "January" },
        { id: "2", value: "February" },
        { id: "3", value: "March" },
        { id: "4", value: "April" },
        { id: "5", value: "May" },
        { id: "6", value: "June" },
        { id: "7", value: "July" },
        { id: "8", value: "August" },
        { id: "9", value: "September" },
        { id: "10", value: "October" },
        { id: "11", value: "November" },
        { id: "12", value: "December" }];

    freqencyArr = [{ id: 1, value: "Weekly" }, { id: 4, value: "Biweekly" }, { id: 2, value: "Monthly" }, { id: 3, value: "Yearly" }];


    ShowToasts(title: string, subtitle: string, message: string, type: string) {
        let iconName = ""
        //  let currentUserDetail = JSON.parse(localStorage.getItem("userDetails"));
        //  let currentConfigDetail = JSON.parse(localStorage.getItem("themeConfig"));
        if (type == "Success") {
            this.toastType = "bg-success";
            iconName = "fas fa-info-circle";
        } else if (type == "Info") {
            this.toastType = "bg-info";
            iconName = "fas fa-info-circle";
        } else if (type == "Warning") {
            this.toastType = "bg-warning";
            iconName = "fas fa-exclamation-circle";
        }
        else if (type == "Danger") {
            this.toastType = "bg-danger";
            iconName = "fas fa-exclamation-triangle";
        }
        if (this.savedToast.position != undefined) {
            if (this.savedToast.position == "TL") {
                this.toastPosition = "topLeft";
            } else if (this.savedToast.position == "TR") {
                this.toastPosition = "topRight";
            } else if (this.savedToast.position == "BL") {
                this.toastPosition = "bottomLeft";
            }
            else if (this.savedToast.position == "BR") {
                this.toastPosition = "bottomRight";
            }
            else if (this.savedToast.position == "TC") {
                this.toastPosition = "topCenter";
            }
            else if (this.savedToast.position == "BC") {
                this.toastPosition = "bottomCenter";
            }
        } else {
            this.toastPosition = "topCenter";
        }


        $(document).Toasts('create', {
            class: this.toastType,
            title: message,
            subtitle: '',
            position: this.toastPosition,
            icon: iconName,
            autohide: true,
            delay: 5000,
        })
        return 1;
    }
    public SetToastConfig(data) {
        this.savedToast = data;
    }

    // public GetActivities(screenid): Observable<any> {

    //   return Observable.create((observer) => {
    //     this._httpService.getAll<any>(this._app_config_service.getApiService("getActivies").toString() + screenid)
    //       .subscribe((tokenData: any) => {
    //         observer.next(tokenData);
    //       }, (error) => {
    //         this.GoToErrorHandling(error);
    //         // console.log(error.status);
    //         observer.error(error)
    //       },
    //         () => {
    //           observer.complete()
    //         });
    //   });
    // }

    // public GetScreenList(): Observable<any> {
    //     return Observable.create((observer) => {
    //         this._httpService.getAll<any>(this._app_config_service.getApiService("menuList").toString())
    //             .subscribe((tokenData: any) => {
    //                 observer.next(tokenData);
    //             }, (error) => {
    //                 this.GoToErrorHandling(error);
    //                 // console.log(error.status);
    //                 observer.error(error)
    //             },
    //                 () => {
    //                     observer.complete()
    //                 });
    //     });
    // }

    // public GoToErrorHandling(error) {
    //     console.log(error.status);
    //     if (error.status == "401") {
    //         this.router.navigate(['./auth/login']);
    //     }
    //     return true;
    // }

    public GoToErrorHandling(error) {
    //   if (error.status == "403") {
    //     this.logout().subscribe((data: any) => {

    //       if (data.code == "SUCCESS") {
    //         this.SetUserInfo(null);
    //         let lang = localStorage.getItem(this._app_config_service.getAppName() + "-" + "currentLanguage");
    //         localStorage.removeItem(this._app_config_service.getAppName() + "-" + "tokendetails");
    //         localStorage.clear();
    //         localStorage.setItem(this._app_config_service.getAppName() + "-" + "currentLanguage", lang);
    //         this.ShowToasts('', '', "You are not authorized.", 'Danger');
    //         this.router.navigate(['./auth/login']);
    //       }

    //     },
    //      error => {
    //       console.log(error);
    //     }, () => {
    //     });
    //   } else if (error.status == "401") {
    //     this.SetUserInfo(null);
    //     let lang = localStorage.getItem(this._app_config_service.getAppName() + "-" + "currentLanguage");
    //     localStorage.removeItem(this._app_config_service.getAppName() + "-" + "tokendetails");
    //     localStorage.clear();
    //     localStorage.setItem(this._app_config_service.getAppName() + "-" + "currentLanguage", lang);
    //     this.ShowToasts('', '', "You are not authorized.", 'Danger');
    //     this.router.navigate(['./auth/login']);
    //   }
      return true;
    }

    public SetUserInfo(data) {
        this.userInfo = data;
        return true;
    }

    public GetUserInfo() {
        //this.userInfo = data;
        return this.userInfo;
    }

    // public GetLoggedInUser(): Observable<any> {
    //     return Observable.create((observer) => {
    //         this._httpService.getAll<any>(this._app_config_service.getApiService("getLoggedInUser").toString())
    //             .subscribe((tokenData: any) => {
    //                 observer.next(tokenData);
    //             }, (error) => {
    //                 this.GoToErrorHandling(error);
    //                 // console.log(error.status);
    //                 observer.error(error)
    //             },
    //                 () => {
    //                     observer.complete()
    //                 });
    //     });
    // }

    // public GetNotifications(groupid, department): Observable<any> {

    //     return Observable.create((observer) => {
    //         this._httpService.getAll<any>(this._app_config_service.getApiService("getNotifications").toString() + 'groupId=' + groupid + '&department=' + department)
    //             .subscribe((tokenData: any) => {
    //                 observer.next(tokenData);
    //             }, (error) => {
    //                 this.GoToErrorHandling(error);
    //                 // console.log(error.status);
    //                 observer.error(error)
    //             },
    //                 () => {
    //                     observer.complete()
    //                 });
    //     });
    // }

    // public GetNotificationsByCategory(notificationObj): Observable<any> {
    //     let url = "";
    //     switch (notificationObj.serviceType) {
    //         case "DEPARTMENT":
    //             url = this._app_config_service.getApiService("getNotificationsOfDepartment").toString();
    //             break;
    //         case "WEB SERVICE":
    //             url = this._app_config_service.getApiService("getNotificationsOfWebService").toString();
    //             break;
    //         case "MQM Definition":
    //             url = this._app_config_service.getApiService("getNotificationsOfMqmDefinition").toString();
    //             break;
    //         case "SIGNATURE":
    //             url = this._app_config_service.getApiService("getNotificationsOfSignature").toString();
    //             break;
    //         case "COUNTRY":
    //             url = this._app_config_service.getApiService("getNotificationsOfCountry").toString();
    //             break;
    //         case "PRODUCT":
    //             url = this._app_config_service.getApiService("getNotificationsOfProductCode").toString();
    //             break;
    //         case "EVENT CODE":
    //             url = this._app_config_service.getApiService("getNotificationsOfEventCode").toString();
    //             break;
    //         case "IBAN CHECK":
    //             url = this._app_config_service.getApiService("getNotificationsOfIBANcheck").toString();
    //             break;
    //         case "CHANNEL DEF":
    //             url = this._app_config_service.getApiService("getNotificationsOfChannelDef").toString();
    //             break;
    //         case "TCP IP DEF":
    //             url = this._app_config_service.getApiService("getNotificationsOfTCPIPDef").toString();
    //             break;
    //         case "FILESYSTEM DEF":
    //             url = this._app_config_service.getApiService("getNotificationsOfFileSystemServiceDef").toString();
    //             break;
    //         case "Bank Code":
    //             url = this._app_config_service.getApiService("getNotificationsOfBankCode").toString();
    //             break;
    //         case "Security Group":
    //             url = this._app_config_service.getApiService("getNotificationsOfSecurityGroup").toString();
    //             break;
    //         case "NETWORK DEFINITION":
    //             url = this._app_config_service.getApiService("getNotificationsOfNetworkDefinition").toString();
    //             break;
    //         case "ERROR NOTIFICATION":
    //             url = this._app_config_service.getApiService("getNotificationsOfErrorNotification").toString();
    //             break;
    //         case "EXTERNAL ERROR CODE":
    //             url = this._app_config_service.getApiService("getNotificationsOfExternalErrorCode").toString();
    //             break;
    //         case "Currency Spread":
    //             url = this._app_config_service.getApiService("getNotificationsOfCurrencySpread").toString();
    //             break;
    //         case "Notification Definition":
    //             url = this._app_config_service.getApiService("getNotificationsOfNOTDefinition").toString();
    //             break;
    //         case "SADADMOI":
    //             url = this._app_config_service.getApiService("getNotificationsOfSadadMoi").toString();
    //             break;
    //         case "Currency Cut-off Definition":
    //             url = this._app_config_service.getApiService("getNotificationCurrencyCutOffDef").toString();
    //             break;
    //         case "Business Date":
    //             url = this._app_config_service.getApiService("getNotificationsOfBusinessDate").toString();
    //             break;
    //         case "Account Definition":
    //             url = this._app_config_service.getApiService("getNotificationsOfAccountDefinition").toString();
    //             break;
    //         case "One Time Payment":
    //             url = this._app_config_service.getApiService("getNotificationsOfOneTimePay").toString();
    //             break;
    //         case "BILL MAINTENANCE":
    //             url = this._app_config_service.getApiService("getNotificationsOfSadadBiller").toString();
    //             break;
    //         case "StandingInstruction":
    //             url = this._app_config_service.getApiService("getNotificationsOfSI").toString();
    //             break;

    //         case "HOLIDAYS":
    //             url = this._app_config_service.getApiService("getNotificationsOfHoliday").toString();
    //             break;
    //         case "Channel Onboarding":
    //             url = this._app_config_service.getApiService("getNotificationsChannelOnboarding").toString();
    //             break;
    //         case "Interbank Payment":
    //             url = this._app_config_service.getApiService("getNotificationsInterbankPayment").toString();
    //             break;
    //         case "Outgoing Customer Payment":
    //             url = this._app_config_service.getApiService("getNotificationsOutgoingCustomerPayment").toString();
    //             break;
    //         case "TSM Message":
    //             url = this._app_config_service.getApiService("getNotificationsOfTsmMessage").toString();
    //             break;
    //         case "Duplicate Check":
    //             url = this._app_config_service.getApiService("getNotificationsAlmSys").toString();
    //             break;
    //         case "SADAD BILLER DATA":
    //             url = this._app_config_service.getApiService("getNotificationsSadadBillerData").toString();
    //             break;

    //         default:
    //             url = "";
    //             break;
    //     }

    //     return Observable.create((observer) => {
    //         this._httpService.getAll<any>(url + notificationObj.notificationId)
    //             .subscribe((tokenData: any) => {
    //                 observer.next(tokenData);
    //             }, (error) => {
    //                 this.GoToErrorHandling(error);
    //                 // console.log(error.status);
    //                 observer.error(error)
    //             },
    //                 () => {
    //                     observer.complete()
    //                 });
    //     });
    // }


    show() {
        if (!this.isLoading.value) {
            this.isLoading.next(true);
        }
    }
    hide() {
        if (this.isLoading.value) {
            this.isLoading.next(false);
        }
    }

    // public GetsecurityScreenPermission(grpid: string, screenId: string): Observable<any> {
    //   this.LoadActivity.next(screenId);
    //     return Observable.create((observer) => {
    //         this._httpService.getAll<any>(this._app_config_service.getApiService("getsecurityButtonPermission").toString() + 'groupId=' + grpid + '&screenId=' + screenId)
    //             .subscribe((tokenData: any) => {
    //                 observer.next(tokenData);
    //             }, (error) => {
    //                 observer.error(error)
    //             },
    //                 () => {
    //                     observer.complete()
    //                 });
    //     });
    // }

    public getcutTypeList(): any {
        return CommonServiceProvider.CutOfList;
    }

    public getStatusList(): any {
        return CommonServiceProvider.statusList;
    }
    public getStatusListCommon(): any {
        return CommonServiceProvider.statusListCommon;
    }
    public getStatusListEnq(): any {
        return CommonServiceProvider.statusListEnq;
    }

    public getPayTypeList(): any {
        return CommonServiceProvider.payTypeList;
    }

    public getCrdrFlagList(): any {
        return CommonServiceProvider.crdrFlagList;
    }

    public getRunningStatusList(): any {
        return CommonServiceProvider.runningStatList;
    }
    public getBenSIStatusList(): any {
      return CommonServiceProvider.benStandInsStatList;
    }

    public getStatusListLiqMangParm(): any {
      return CommonServiceProvider.statusListLiqMangParm;
    }
    public getStatusSadadBillerData(): any {
      return CommonServiceProvider.statusSadadBillerData;
    }

    public sendMessage(message) {
        this.customStompClient.send('/app/send/message', {}, message);
    }

    public setCustomStompClient(stompClient: any) {
        this.customStompClient = stompClient;
    }

    public getCustomStompClient() {
        return this.customStompClient;
    }


    // public GetMapPropertyFromLocal(): Observable<any> {
    //     return Observable.create((observer) => {
    //         this._httpService.getLocal<any>("assets/mapprop.json")
    //             .subscribe((tokenData: any) => {
    //                 observer.next(tokenData);
    //             }, (error) => {
    //                 observer.error(error)
    //             },
    //                 () => {
    //                     observer.complete()
    //                 });
    //     });
    // }

    // public GetDyanamicDropDownValuesForMap(data): Observable<any> {
    //     return Observable.create((observer) => {
    //         this._httpService.getAll<any>(data.xhr)
    //             .subscribe((tokenData: any) => {
    //                 observer.next(tokenData);
    //             }, (error) => {
    //                 observer.error(error)
    //             },
    //                 () => {
    //                     observer.complete()
    //                 });
    //     });
    // }


    // public SaveTableConfig(screenId, data): Observable<any> {
    //     return Observable.create((observer) => {
    //         this._httpService.add<any>(this._app_config_service.getApiService("tableconfig").toString(), data)
    //             .subscribe((tokenData: any) => {
    //                 observer.next(tokenData);
    //             }, (error) => {
    //                 observer.error(error)
    //             },
    //                 () => {
    //                     observer.complete()
    //                 });
    //     });
    // }

    // public GetTableConfig(screenId): Observable<any> {
    //     return Observable.create((observer) => {
    //         this._httpService.getAll<any>(this._app_config_service.getApiService("tableconfig").toString() + "?screenId=" + screenId)
    //             .subscribe((tokenData: any) => {
    //                 observer.next(tokenData);
    //             }, (error) => {
    //                 observer.error(error)
    //             },
    //                 () => {
    //                     observer.complete()
    //                 });
    //     });
    // }

    setMapperJsonData(data) {
        this.mapperJsonData = data;
    }
    getMapperJsonData() {
        return this.mapperJsonData;
    }

    // savePageDataWithencryption(data, screenid, pagetype) {
    //     let encryptedSaveData = CryptoJS.AES.encrypt(JSON.stringify(data), this._app_config_service.getEncryptDecryptLocalStorageKey()).toString();
    //     localStorage.setItem(this._app_config_service.getAppName() + "-" + screenid + "-" + pagetype + "-" + "Data", encryptedSaveData);
    // }

    // getPageDataWithDecryption(screenid, pagetype) {
    //     let localStorageEncryptedData = localStorage.getItem(this._app_config_service.getAppName() + "-" + screenid + "-" + pagetype + "-" + "Data");
    //     let decryptedData = {};
    //     if (localStorageEncryptedData != null) {
    //         decryptedData = JSON.parse(CryptoJS.AES.decrypt(localStorageEncryptedData, this._app_config_service.getEncryptDecryptLocalStorageKey()).toString(CryptoJS.enc.Utf8));

    //     }
    //     return decryptedData;
    // }

    // clearPageDataFromLocalStorage(screenid, pagetype) {
    //     localStorage.removeItem(this._app_config_service.getAppName() + "-" + screenid + "-" + pagetype + "-" + "Data")
    // }

    setMessageData(data) {
        this.localMessages = data;
    }

    public GetFrequency() {
        return this.freqencyArr;
    }

    public GetMonths() {
        return this.months;
    }

    public setStatusCount(statusCountList, data) {
        console.log(data);
          let total = 0;
          statusCountList.failed = 0;
          for (let index = 0; index < data.length; index++) {
              if (data[index].name == 'PROCESSD') {
                  statusCountList.processd = data[index].count;
                  total = total + data[index].count;
              } else if (data[index].name == 'VERIFIED') {
                  statusCountList.verified = data[index].count;
                  total = total + data[index].count;
              } else if (data[index].name == 'REJECT') {
                  statusCountList.reject = data[index].count;
                  total = total + data[index].count;
              } else if (data[index].name == 'SUCCESS') {
                  statusCountList.success = data[index].count;
                  total = total + data[index].count;
              } else if (data[index].name.toUpperCase() == 'FAILED') {
                  statusCountList.failed = statusCountList.failed + data[index].count;
                  total = total + data[index].count;
              }
              else if (data[index].name == 'REPAIR') {
                  statusCountList.repair = data[index].count;
                  total = total + data[index].count;
              } else if (data[index].name == 'DELETE') {
                  statusCountList.delete = data[index].count;
                  total = total + data[index].count;
              }
  
              else if (data[index].name == 'APPLIED') {
                  statusCountList.applied = data[index].count;
                  total = total + data[index].count;
              } else if (data[index].name == 'EXCEPT') {
                  statusCountList.except = data[index].count;
                  total = total + data[index].count;
              } else if (data[index].name == 'ACCUP') {
                  statusCountList.accup = data[index].count;
                  total = total + data[index].count;
              } else if (data[index].name == 'ACCEPTED') {
                  statusCountList.accepted = data[index].count;
                  total = total + data[index].count;
              } else if (data[index].name == 'RETURN') {
                  statusCountList.return = data[index].count;
                  total = total + data[index].count;
              }
  
              else if (data[index].name == 'REJECTED') {
                  statusCountList.rejected = data[index].count;
                  total = total + data[index].count;
              } else if (data[index].name == 'SENT') {
                  statusCountList.sent = data[index].count;
                  total = total + data[index].count;
              }
  
              else if (data[index].name == 'CANCELLED') {
                  statusCountList.cancelled = data[index].count;
                  total = total + data[index].count;
              } else if (data[index].name == 'DELETED') {
                  statusCountList.deleted = data[index].count;
                  total = total + data[index].count;
              } else if (data[index].name == 'ACCEPT') {
                  statusCountList.accept = data[index].count;
                  total = total + data[index].count;
              // } else if (data[index].name == 'CENCELLED') {
              //     statusCountList.cancelled = data[index].count;
              //     total = total + data[index].count;
              }
              else if (data[index].name == 'ACTIVE') {
                  statusCountList.active = data[index].count;
                  total = total + data[index].count;
              }
  
              else if (data[index].name == 'RECEIVED') {
                  statusCountList.received = data[index].count;
                  total = total + data[index].count;
              }
  
              else if (data[index].name == 'INACTIVE') {
                  statusCountList.inactive = data[index].count;
                  total = total + data[index].count;
              }
              else if (data[index].name == 'TO BE CANCEL') {
                statusCountList.toBeCancel = data[index].count;
                total = total + data[index].count;
              }
              else if (data[index].name == 'COMPLETE') {
                  statusCountList.complete = data[index].count;
                  total = total + data[index].count;
              }
      else if (data[index].name == 'COMPLETE') {
                  statusCountList.complete = data[index].count;
                  total = total + data[index].count;
              }
              else if (data[index].name == 'PENDING') {
                  statusCountList.pending = data[index].count;
                  total = total + data[index].count;
              }
              else if (data[index].name.toUpperCase() == 'COMPLETED') {
                  statusCountList.completed = data[index].count;
                  total = total + data[index].count;
              }
              else if (data[index].name == 'TOBE') {
                  statusCountList.tobe = data[index].count;
                  total = total + data[index].count;
              }
              else if (data[index].name == 'DEBITR') {
                  statusCountList.debitr = data[index].count;
                  total = total + data[index].count;
              }
              else if (data[index].name == 'AMENDED') {
                  statusCountList.amended = data[index].count;
                  total = total + data[index].count;
              }
              else if (data[index].name == 'NEW') {
                  statusCountList.new = data[index].count;
                  total = total + data[index].count;
              }
              else if (data[index].name == 'REPROCESS') {
                  statusCountList.reprocess = data[index].count;
                  total = total + data[index].count;
              }
              else if (data[index].name == 'PENDING REPROCESS') {
                  statusCountList.pendingReprocess = data[index].count;
                  total = total + data[index].count;
              }
  
          }
          statusCountList.totalStatus = total;
      }
    // public getBankIdDropDown(): Observable<any> {
    //     return Observable.create((observer) => {
    //         this._httpService.getAll<any>(this._app_config_service.getApiService("getBankIdDrop").toString())
    //             .subscribe((tokenData: any) => {
    //                 observer.next(tokenData);
    //             }, (error) => {
    //                 observer.error(error);
    //             },
    //                 () => {
    //                     observer.complete();
    //                 });
    //     });
    // }

    // public getDepartmentDropdown(): Observable<any> {
    //     return Observable.create((observer) => {
    //         this._httpService.getAll<any>(this._app_config_service.getApiService("getDepartmentDropdown").toString())
    //             .subscribe((tokenData: any) => {
    //                 observer.next(tokenData);
    //             }, (error) => {
    //                 observer.error(error);
    //             },
    //                 () => {
    //                     observer.complete();
    //                 });
    //     });
    // }



    // public setUserDetailsWithToken(data) {

    //     let encryptedSaveData = CryptoJS.AES.encrypt(JSON.stringify(data), this._app_config_service.getEncryptDecryptLocalStorageKey()).toString();
    //     localStorage.setItem(this._app_config_service.getAppName() + "-" + "tokendetails", encryptedSaveData);


    // }

    // public getUserDetailsWithToken() {


    //     let localStorageEncryptedData = localStorage.getItem(this._app_config_service.getAppName() + "-" + "tokendetails");
    //     let decryptedData = {};
    //     if (localStorageEncryptedData != null) {
    //         decryptedData = JSON.parse(CryptoJS.AES.decrypt(localStorageEncryptedData, this._app_config_service.getEncryptDecryptLocalStorageKey()).toString(CryptoJS.enc.Utf8));
    //     }
    //     return decryptedData;
    // }


    // public setTabDetails(data) {

    //     let encryptedSaveData = CryptoJS.AES.encrypt(JSON.stringify(data), this._app_config_service.getEncryptDecryptLocalStorageKey()).toString();
    //     localStorage.setItem(this._app_config_service.getAppName() + "-" + "tabbedMenus", encryptedSaveData);


    // }

    // public getTabDetails() {


    //     let localStorageEncryptedData = localStorage.getItem(this._app_config_service.getAppName() + "-" + "tabbedMenus");
    //     //let decryptedData = [];
    //     if (localStorageEncryptedData != null) {
    //         let decryptedData = JSON.parse(CryptoJS.AES.decrypt(localStorageEncryptedData, this._app_config_service.getEncryptDecryptLocalStorageKey()).toString(CryptoJS.enc.Utf8));
    //         return decryptedData;
    //     }else{
    //         return null;
    //     }

    // }

    // public getTabDetails(): Observable<any> {
    //     return Observable.create((observer) => {

    //         let localStorageEncryptedData = localStorage.getItem(this._app_config_service.getAppName() + "-" + "tabbedMenus");
    //         let decryptedData;
    //         if (localStorageEncryptedData != null) {
    //             decryptedData = JSON.parse(CryptoJS.AES.decrypt(localStorageEncryptedData, this._app_config_service.getEncryptDecryptLocalStorageKey()).toString(CryptoJS.enc.Utf8));
    //            // return decryptedData;
    //         } else {
    //             decryptedData = null;
    //         }
    //         observer.next(decryptedData);

    //     });
    // }

    // public getHomeScreenUrlDropDown(): Observable<any> {
    //     return Observable.create((observer) => {
    //         this._httpService.getAll<any>(this._app_config_service.getApiService("getHomeScreenUrlDropDown").toString())
    //             .subscribe((tokenData: any) => {
    //                 observer.next(tokenData);
    //             }, (error) => {
    //                 observer.error(error);
    //             },
    //                 () => {
    //                     observer.complete();
    //                 });
    //     });
    // }

//     public logout(): Observable<any> {
//       return Observable.create((observer) => {
//           this._httpService.logout<any>(this._app_config_service.getApiService("logout").toString())
//               .subscribe((tokenData: any) => {
//                   observer.next(tokenData);
//               }, (error) => {
//                   observer.error(error)
//               },
//                   () => {
//                       observer.complete()
//                   });
//       });
//   }

  public SetMenu(data) {
     this.menus = data;
     return true;
 }

 public GetMenu() {
    return this.menus;
  }

  public static statusListBen: any = [
    { code: "ACTIVE", display: "Active" },
    { code: "INACTIVE", display: "Inactive" },
    ];

public getStatusListBenAcc(): any {
    return CommonServiceProvider.statusListBen;
}

public static countryList: any = [
    { code: "BAHRAIN", display: "Bahrain" },
    { code: "IRAN", display: "Iran" },
    { code: "KSA", display: "Saudi Arabia" },
    { code: "KUWAIT", display: "Kuwait" },
    { code: "QATAR", display: "Qatar" },
    { code: "UAE", display: "United Arab Emirates" },
    { code: "OMAN", display: "Oman" },
    { code: "USA", display: "USA" }];
public getCountryListType(): any {
    return CommonServiceProvider.countryList;
}

public getAccessTypeList(): any {
    return CommonServiceProvider.accessTypeList;
}

public static accessTypeList: any = [
    { code: "0", display: "Global" },
    { code: "1", display: "Local" }];

    public getCurrencyList(): any {
        return CommonServiceProvider.currencyTypeList;
    }

    public static currencyTypeList: any = [
        { code: "EUR", display: "EUR" },
        { code: "SAR", display: "SAR" },
        { code: "USD", display: "USD" }];

        public static commissionTypeArray: any = [
            { code: "BEN", display: "BEN" },
            { code: "SHA", display: "SHA" },
            { code: "OUR", display: "OUR" }];


        public getCommissionTypeArray(): any {
            return CommonServiceProvider.commissionTypeArray;
        }

        public static payTypeListBen: any = [
            { code: "1", display: "Account to Account" },
            { code: "2", display: "RTGS" },
            // { code: "3", display: "Instant Payment" },
            { code: "4", display: "SWIFT" }];

            public getPayTypeBenList(): any {
                return CommonServiceProvider.payTypeListBen;
            }

            public GetFrequencyBenSi() {
                return this.freqencyArrBenSi;
            }

            freqencyArrBenSi = [{ id: 1, value: "Weekly" },{ id: 4, value: "Biweekly" }, { id: 2, value: "Monthly" }, { id: 5, value: "Quarterly" }, { id: 6, value: "Half Yearly" }, { id: 3, value: "Yearly" }];

            public static payementTypeListBen: any = [
                { code: "1", display: "Account to Account" },
                { code: "2", display: "RTGS" },
                // { code: "3", display: "Instant Payment" },
                { code: "4", display: "SWIFT" }];

            public getPayTypeListBen(): any {
                return CommonServiceProvider.payementTypeListBen;
            }
}
