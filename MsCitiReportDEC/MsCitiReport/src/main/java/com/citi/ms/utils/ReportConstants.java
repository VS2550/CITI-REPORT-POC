package com.citi.ms.utils;

public interface ReportConstants {

	
	public static final String CITI_URL = "/citi";
	public static final String GET_REPORT_REQUEST_HANDLE_URL = "/getReportDetails";
	public static final String SET_REPORT_REQUEST_HANDLE_URL = "/setReportDetails";
	public static final String GET_PROGRAM_HANDLE_URL = "/getProgram";
	public static final String GET_PROGRAM_HANDLE_URL_PATH = "/getField/{fieldName}";
	public static final String GET_ALL_TABLE_NAME = "/getAllTableName";
	public static final String GET_ALL_COLUMN_NAMES = "/getAllColumnNames/{tableName}";
	public static final String GET_TABLE_RECORDS_URL = "/getTableRecords";
	public static final String GET_REPORT_CONFIG_RECORDS_URL = "/getReportConfigData";
	public static final String SAVE_REPORT_CONFIG_RECORDS_URL = "/saveReportConfigData";
	public static final String SAVE_USER_SELECTION_REPORTS = "/saveUserPreference";
	public static final String GET_SAVED_REPORTS = "/getAllSavedReports";
	public static final String GET_SAVED_REPORTS_DATA = "/getSavedReportsData/{reportObjectName}";
	
	//download
	public static final String CITI_DOWNLOAD_URL = "/download";
	public static final String DOWNLOAD_SAVED_REPORTS_DATA = "/downloadSavedReportsData/{reportObjectName}";
	public static final String DOWNLOAD_SAVED_REPORTS_DATA_1 = "/downloadSavedReportsData1/{reportObjectName}";
	
}
