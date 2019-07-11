package com.citi.ms.service;

import java.util.List;

import com.citi.ms.model.CustomerRequest;
import com.citi.ms.model.ReportConfig;
import com.citi.ms.model.ReportResponse;
import com.citi.ms.model.UserPreferenceReportName;

public interface MsClientReportService {

	String generateReport(CustomerRequest customerRequest);

	String getAllTableNameService();

	String getColumnsName(String tableName);

	List<ReportConfig> getReportConfigData();

	void saveReportConfigData(ReportConfig reportConfig);

	String saveGenerateReport(CustomerRequest customerRequest);

	List<UserPreferenceReportName> getAllSavedReportsDetails();

	String getAllSavedReportsData(String reportObjectName);

	String generatePDFFromHTML(String htmlPath);

	void generateCitiPdf(String jsonResult);

	String generateCitiPdf(String jsonResult, String reportObjectName);
	
	/*public CustomerResponse saveClientReport(ClientReport clientReport);
	public CustomerResponse getClientReport(CustomerRequest customerRequest);
	public CustomerResponse getDynamicClientReport(CustomerRequest customerRequest);

   	public List<String> getProgramList(String program);*/
}
