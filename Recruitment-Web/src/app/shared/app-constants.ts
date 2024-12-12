import { IAppConfiguration } from "../interface/iapp-configuration"

export abstract class ApiServicesHandlerService 
{

    static readonly _api_services = {

        // Admin Services

        // Insert
        addadmin: { api: "http://localhost:8060/admin/addAdmin" },
        addcriteria: { api: "http://localhost:8060/admin/addCriteria"},
        addcandidate: { api: "http://localhost:8060/admin/addCandidate" },
        addIndividualCandidate: { api: "http://localhost:8060/admin/addIndividualCandidate" },
        
        addquestion: { api: "http://localhost:8060/admin/addMcqQuestion" },
        addcodequestion: { api: "http://localhost:8060/admin/addCodingQuestion" },

        // Display
        adminview: { api: "http://localhost:8060/admin/searchAdmin",},
        criteriaview: { api: "http://localhost:8060/admin/searchCriteria"},
        finalresult: { api: "http://localhost:8060/admin/searchFinalResult"},
        candidateview: { api: "http://localhost:8060/admin/searchCandidate",},
        adminquestionview: {api: "http://localhost:8060/admin/searchMcqQuestion",},
        admincodequestionview: {api: "http://localhost:8060/admin/searchCodingQuestion",},
        searchFinalResultById: {api: "http://localhost:8060/admin/searchFinalResultById",},


        // Delete
        deletequestion: { api: "http://localhost:8060/admin/deleteMcqQuestion" },
        deletecodequestion: { api: "http://localhost:8060/admin/deleteCodingQuestion" },
        deletecandidate: { api: "http://localhost:8060/admin/deleteCandidate"},
        deletecriteria: { api: "http://localhost:8060/admin/deleteCriteria"},
        deleteadmin: { api: "http://localhost:8060/admin/deleteAdmin"},
        
        // Updatee
        updatecandidate: { api: "http://localhost:8060/admin/updateCandidate"},
        updatecriteria: { api: "http://localhost:8060/admin/updateCriteria"},
        uploadimg: { api: "http://localhost:8060/admin/updateAdmin"},
        verifyCandidate: { api: "http://localhost:8060/admin/verifyCandidate"},

        // Candidate Services
        
        getCodingQuestions: {api: "http://localhost:8060/candidate/codingQuestions"},
        getCriteria: {api: "http://localhost:8060/candidate/getActiveCriteriaId"},
        getMcqQuestions: {api: "http://localhost:8060/candidate/mcqQuestions"},
        submitmcq: {api: "http://localhost:8060/candidate/addMcqResults"},

        compile:{api: "http://localhost:8060/candidate/compile"},
        login:{api: "http://localhost:8060/user/login"},
         gettestduration: {api: "http://localhost:8060/candidate/getTime"},
        codequestionview: {api: "http://localhost:8060/candidate/codingQuestions"},   
        getCurrentUser: {api: "http://localhost:8060/user/getCurrentUser"},   
        downloadFile: {api: "http://localhost:8060/admin/downloadFile"},   
        getUserById: {api: "http://localhost:8060/admin/searchById"},   

     }

    static readonly _app_config: Array<IAppConfiguration> = [
        {
            envName: "LOCAL",
            apiServices:
            {
                remoteServiceBaseUrl: "http://localhost:8808/",
                notificationRemoteServiceBaseUrl: "http://localhost:8820/",
            },
            version: "0.0.1"
        },
    ]
}
