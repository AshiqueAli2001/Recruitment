export interface IAppConfiguration {

    envName: string,
    apiServices: {
        remoteServiceBaseUrl: string,
        notificationRemoteServiceBaseUrl : string,
    },
    version: string,

}
