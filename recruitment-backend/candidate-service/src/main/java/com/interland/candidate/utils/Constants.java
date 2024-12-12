package com.interland.candidate.utils;

import java.util.ArrayList;
import java.util.List;

public class Constants {

	public final static String AA_DATA = "aaData";
	public final static String DATA = "data";
	public final static String TOTAL_DISPLAY_RECORD = "iTotalDisplayRecords";
	public final static String TOTAL_RECORD = "iTotalRecords";
	public final static String DELETED = "DELETED";
	public final static String VERIFIED = "VERIFIED";
	public final static String PENDING = "PENDING";
	public final static String PROCESSD = "PROCESSD";
	public static final String MESSAGE = "message";
	public static final String STATUS = "status";
	public static final String FAILED = "FAILED";
	public static final String SUCCESS = "SUCCESS";
	public final static String FILE_NAME = "fileName";
	public final static String TYPE = "type";
	public final static String NEW_OBJ = "newObj";
	public final static String OLD_OBJ = "oldObj";
	public final static String NULL = "null";
	public final static String COUNT_BY_STATUS = "countByStatus";
	public final static String NAME= "name";
	public final static String COUNT= "count";
	public final static String ALR_EXIST= "Already Existing Record";

	public final static String MIME_TYPE_OCTET_STREAM = "application/octet-stream";

	public final static String CONTENT_TYPE= "contentType";
	public final static String TEXT_CONTENT_TYPE= "text/csv";
	
	public static class STREAM_DEFINITION{
		public static final String ST = "ST";
		public static final String IN = "IN";
		public static final String UP = "UP";
		public static final String DN = "DN";
		
	}

	public static class PATTERN {
		public static final String ALPHANUMERISPC = "^([a-zA-Z0-9](?!.*  )[a-zA-Z0-9 ]*)*$";
		public static final String ALPHANUMERICONLY = "^[a-zA-Z0-9]*$";
		public static final String NUMERICSONLY = "^[0-9]+$";
		public static final String ALPHABETSONLY = "^[a-zA-Z ]+$";
		public static final String YESORNO = "^(Y|N){1}$";
		public static final String IPADD = "^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$";
		public static final String CONNTYP = "^(S|C){1}$";
		public static final String DIRECTION = "^(I|O){1}$";
		public static final String ALPHANUMERISPCSLASH = "^([a-zA-Z0-9]+(?!(.*  |\\\\\\\\))[a-zA-Z0-9\\\\ ]*)*$";
		public static final String XSD = "(.).+\\.(xsd|XSD)$";
		public static final String EXE = ".+\\.(?!(EXE|exe)$)([^.]+)";
		public static final String NUMERICSWITHDOT = "^[0-9.]+$";
		public static final String FILENAME = "(.*)*.+\\.(png|jpg|gif|bmp|jpeg|PNG|JPG|GIF|BMP|JPEG)$";
		public static final String CUTTIME="^(2[0-3]|[01]?[0-9]):([0-5]?[0-9])$";
		public static final String ALPHANUMERIC_WITH_N_SPACE="^[a-zA-Z0-9]+([a-zA-Z0-9 ]+)*$";
		public static final String ACC_WITH_INST_BIC = "^(?=(?:.{8}|.{11})$)[0-9a-zA-Z]*$";
		public static final String ACC_WITH_INST_AC = "(?:(^/[a-zA-Z0-9]{1,34}$)|(^/[A-Z]{1}/[a-zA-Z0-9]{1,34}$))";
		public static final String CUSTOM_INSTITUTION_BIC="^(?=(?:.{8}|.{11})$)[a-zA-Z0-9]*$";
		public static final String CUSTOM_INSTITUTION_ID="(?:(^/[a-zA-Z0-9]{1,34}$)|(^/[A-Z]{1}/[a-zA-Z0-9]{1,34}$))";
		public static final String CUSTOM_INSTITUTION_ID_C="^/[a-zA-Z0-9]{1,34}$";
		
	}
	
	public static class ACCOUNT_STATUS {
		public static final String ACTIVE = "ACTIVE";
		public static final String INACTIVE = "INACTIVE";
	}
	
	public static class STANDIN_PAYMENTS {
		public static final String MESSAGE_ID = "messageId";
		public static final String CREDITOR_BIC = "creditorBic";
		public static final String INSTRUCTION_ID = "instructionId";
		public static final String TRANSACTION_ID = "transactionId";
		public static final String ENT2END_ID = "end2endId";
		public static final String DEPARTMENT = "department";
		public static final String TRANS_DATE = "transactionDate";
		public static final String DEBIT_DATE = "debitDate";
		public static final String VALUE_DATE = "valueDate";
		public static final String TRANS_AMT = "transactionAmount";
		public static final String DEBITOR_ID = "debiterId";
		public static final String CREDITOR_ID = "crediterId";
		public static final String BEN_CUST_ACC = "benCustomerAccount";
		public static final String ORDERING_CUST_ACC = "orderingCustomerAccount";
		public static final String REMITANCE_INFO = "remittanceInfo";
		public static final String USER_CMNT = "userComment";
		public static final String STATUS = "status";
		public static final String STATUS_MSG = "statusMessage";
		public static final String BANK_REF = "bankReference";
		
		public static final String DEPARTMENT_NAME = "departmentName";
		
		public static final String PURPOSE_OF_PAYMENT = "purposeOfPayment";
		public static final String ADVICE_LINE1 = "adviceLine1";
		public static final String ADVICE_LINE2 = "adviceLine2";
		public static final String ADVICE_LINE3 = "adviceLine3";
		public static final String ADVICE_LINE4 = "adviceLine4";
		public static final String PAYMEMT_INFO = "paymentInfo";
		public static final String CHARGE = "charge";
		public static final String TRSANSACTION_AMOUNT = "transactionAmount";
		public static final String CUR_CODE ="curCode";
		public static final String DIRECTION = "direction";
		public static final String CRDR_FLAG = "crdrFlag";
		public static final String TRANSACTION_TIME = "transactionTime";
		public static final String TRANSACTION_DATE = "transactionDate";
		public static final String ACCOUNT_NUMBER = "accountNumber";
		public static final String BANK_REFERENCE = "bankRef";
		public static final String TRANSACTION_REFERENCE = "transRef";
		public static final String CHANNEL_ID = "channelId";
		public static final String BANK_ID = "bankId";
		public static final String TRANS_ID = "trnId";
	}
	
	public static class MESSAGE_STATUS{
		public static final String VERIFIED = "VERIFIED";
		public static final String DEFFERED = "DEFFERED";
		public static final String PROCESSED = "PROCESSD";
		public static final String SUCCESS = "SUCCESS";
		public static final String DELETED = "DELETED";
		public static final String DELETE = "DELETE";
		public static final String FAILED = "FAILED";
		public static final String DRAFT = "DRAFT";
		public static final String GENERATED = "GENERATED";
		public static final String RECEIVED = "RECEIVED";
		public static final String REPAIR = "REPAIR";
		public static final String NEW = "NEW";
		public static final String APPROVED = "APPROVED";
		public static final String UPDATED = "UPDATED";
		public static final String RETURN = "RETURN";
		public static final String INITIATED = "INITIATED";
		public static final String COMPLETED = "COMPLETED";
		public static final String REJECT = "REJECT";
		public static final String OK = "OK";
		public static final String NIL = "NIL";
		public static final String ACCUP = "ACCUP";
		public static final String EXCEPT = "EXCEPT";
		public static final String COMPLETE = "COMPLETE";
		public static final String CANCELLED = "CANCELLED";
		public static final String TO_BE_CANCEL = "TO BE CANCEL";
		public static final String CANCEL_INITIATED = "CANCEL_INITIATED";
		public static final String AMENDP_INITIATED = "AMENDP_INITIATED";
		public static final String FRAUD_INITIATED = "FRAUD_INITIATED";
		public static final String FRAUD = "FRAUD";
	}
	
	public static class NOTIFICATION_TYPE {
		public static final String TASK = "TASK";
		public static final String INFO = "INFO";
	}
	public static class SCREEN_ID {
		public static final String INTERBANK_PAYMENT = "INTBKPY";
		public static final String OUTGOING_CUSTOMER_PAYMENT = "OUTCSTPY";
		public static final String TSM_MESSAGE = "TSMMSG";
		public static final String OUTGOING_CST_INQ = "OUTRDET";
		public static final String RTGS_BULK_INQ = "BLKENQ";
		public static final String INCOMING_TRN_INQ = "INTRDET";

		
	}
	
	public static class SERVICE_TYPE {
		public static final String INTERBANK_PAYMENT = "Interbank Payment";
		public static final String OUTGOING_CUSTOMER_PAYMENT = "Outgoing Customer Payment";
		public static final String TSM_MESSAGE = "TSM Message";
	}
	public static class PSH_CORE_SADAD {
		public static final String BANK_ID = "bankId";
		public static final String BILLER_ID = "billerId";
		public static final String SADAD_PAYMENT_ID = "sadadPaymentId";
		public static final String CHANNEL_ID = "channelId";
		public static final String INSTRUCTION_UUID = "instructionUuid";
		public static final String SERVICE_ID = "serviceId";
		public static final String TRANSACTION_AMOUNT = "transactionAmount";
		public static final String BANK_REFERENCE = "bankReference";
		public static final String ORDERING_CUSTOMER_NAME = "orderingCustomerName";
		public static final String ORDERING_CUSTOMER_ACCOUNT = "orderingCustomerAccount";
		public static final String DEP = "dep";
		public static final String PRODUCT_ID = "productId";
		public static final String CURRENCY_CODE = "currencyCode";
		public static final String BENEFICIARY_CUSTOMER_ACCOUNT = "beneficiaryCustomerAccount";
		public static final String BENEFICIARY_CUSTOMER_NAME = "beneficiaryCustomerName";
		public static final String STATUS = "status";
		public static final String VALUE_DATE = "valueDate";
		public static final String DEBIT_DATE = "debitDate";
		public static final String DATE_CRE = "dateCre";
		public static final String PSH_REF = "pshReference";
		public static final String BILL_ACC = "billingAccount";
		public static final String SADAD_CUS_ID = "sadadCustomerId";
		
		
	}
	public static class PSH_CORE_RECON{
		public static final String ID = "id";
		public static final String PROCESS_DATE ="processDate";
		public static final String TOTAL_RECORDS = "totalRecords";
		public static final String FINAL_REPORT = "finalReportFilePath";
		public static final String SUBMITTED_REPORT= "submittedReportFilePath";
		public static final String RECONCILLATION_TYPE="reconcillationType";
		public static final String FAILED_RECORDS ="failedRecords";
		public static final String STATUS ="status";
		public static final String REQUEST ="request";
		public static final String RESPONSE ="response";
		public static final String APPLICATION_ID ="applicationId";
	}
	public static class PSH_CORE_RECONDET{
		public static final String HEADER_ID ="headerId";
		public static final String RECORD_ID ="recordId";
		public static final String BANK_TRANSACTION_REF ="bankTrasactionRef";
		public static final String CUSTOMER_TRANSACTION_REF = "customerTransactionRef";
		public static final String SAMA_TRANSACTION_REF = "samaTransactionRef";
		public static final String BANK_REVERSAL_REF ="bankReversalRef";
		public static final String SAMA_STATUS = "samaStatus";
		public static final String AMOUNT = "amount";
		public static final String DEBIT_ACCOUNT = "debitAccount";
		public static final String CREDIT_ACCOUNT = "creditAccount";
		public static final String SERVICE_TYPE = "serviceType";
		public static final String STATUS ="status";
		
	}
	public static final String BENFADD = "BENFADD";
	public static final String ALPHA_NUMER_SPACES = "^[a-zA-Z0-9 ]+$";
	public static final String PATTERN_SPECIAL = "^[a-zA-Z0-9 ]*$";
	public static final String PATTERN_ZIP = "^[0-9]{5}|^$";
	public static final String PATTERN_NUMBER = "^[0-9]+(\\.[0-9]*)?$";
	public static final String PATTERN_FAX = "[\\\\+? *[1-9]+]?[0-9 ]+|^$";
	public static final String PATTERN_ACCOUNT = "^[A-Z0-9]*$";
	public static final String PATTERN_MOBILE = "^[0-9]{10}|^$";
	public static final String PATTERN_ARABIC = "^[\u0621-\u064A ]*$";
	public static final String PATTERN_ENGLISH = "^[a-zA-Z ]*$";
	public static final String PATTERN_CEILING_AMOUNT = "^\\d+(\\.\\d{1,4})?$";
	public static final String PATTERN_PROXY_ID ="^[a-zA-Z0-9]+$|^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	public static final String PATTERN_ROUTING_CODE = "^(([/]{1,2})?[a-zA-Z0-9]+([/]{1,2})?)*$";
	public static final String PATTERN_BEN_ACCOUNT = "^[A-Z0-9]{24}$";
	public static final String ERROR_CODE = "VALERRCOD";
	
	public static final String REJ_AMOUNT = "^[0-9]{1,11}(\\.[0-9]{1,4})?$";
	public static final String REJ_SPECFICDAY = "^([1-9]|[12][0-9]|3[01])?$";
	public static final String REJ_MONTH = "^(1[0-2]|[1-9])$";
	
	public static List<String> statusList() {
		List<String> statusList = new  ArrayList<String>();
		statusList.add(MESSAGE_STATUS.VERIFIED);
		statusList.add(MESSAGE_STATUS.PROCESSED);
		statusList.add(MESSAGE_STATUS.DELETED);
		statusList.add(MESSAGE_STATUS.APPROVED);
		statusList.add(MESSAGE_STATUS.DELETE);
		return statusList;
		
	}
	
	public final static String ACC_MST_ID="id";
	public final static String ACC_MST_VALUE="value";
	public final static String ACC_MST_BALANCE="balance";
	public final static String ACC_MST_NAME="name";
	public final static String ACC_MST_ADDRESS1="address1";
	public final static String ACC_MST_ADDRESS2="address2";
	public final static String ACC_MST_ADDRESS3="address3";
	public final static String ACC_MST_CUR_CODE="curCode";
	public final static String NICK_NAME="nickName";
	public final static String BEN_BIC="benBic";
	public final static String POP="pop";
	
	
	public static class TSMMESSAGE{
		public final static String SAMA="SAMASARIXXX";
		public final static String RTGS="RTGS";
		public final static String TSM="TSM";
		public final static String PROCESSED="PROCESSD";
		public final static String CRE_MSG="TSM Message created successfully";
		public final static String OUTGOING="O";
		
	}
	
	public static class EVENT_ID {
		public static final String FIN_POST = "FIN_POST";
		public static final String PAYMENT_CREATE = "PAYMENT_CREATE";
		public static final String PAYMENT_VERIFICATION = "PAYMENT_VERIFICATION";
		public static final String TRN_UPDATE = "TRANSACTION_UPDATE";
		public static final String TRN_VERIFY = "TRANSACTION_VERIFY";
		public static final String FIN_POST_REQ = "FIN_POST_REQ";
		public static final String FIN_POST_RES = "FIN_POST_RES";
		public static final String TRN_RETURN = "TRANSACTION_RETURN";
		public static final String TRN_COMPLETE = "TRANSACTION_COMPLETE";
	}
	
	public static final String BIPAY = "BIPAY";
}
