package com.citi.ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.citi.ms.model.CustomerRequest;
import com.citi.ms.model.ReportConfig;
import com.citi.ms.model.ReportResponse;
import com.citi.ms.model.UserPreferenceReportName;
import com.citi.ms.service.MsClientReportService;
import com.citi.ms.utils.ReportConstants;
import lombok.extern.log4j.Log4j2;

@CrossOrigin
@RestController
@Log4j2
@RequestMapping(ReportConstants.CITI_URL)
public class CitiReportController {

	@Autowired
	private MsClientReportService msClientReportService;

	@RequestMapping(value = ReportConstants.GET_ALL_TABLE_NAME, method = RequestMethod.GET, produces = {
			"application/json" })
	public ResponseEntity<String> getTableList() {

		String tableName = msClientReportService.getAllTableNameService();

		return new ResponseEntity<String>(tableName, HttpStatus.OK);
	}

	@RequestMapping(value = ReportConstants.GET_ALL_COLUMN_NAMES, method = RequestMethod.GET, produces = {
			"application/json" })
	public ResponseEntity<String> getProgramList1(@PathVariable String tableName) {
		String columnNames = null;
		if (tableName.isEmpty()) {
			return new ResponseEntity<>("TABLE NAME MISSING", HttpStatus.BAD_REQUEST);
		} else {
			columnNames = msClientReportService.getColumnsName(tableName);
		}
		return new ResponseEntity<>(columnNames, HttpStatus.OK);
	}

	@RequestMapping(value = ReportConstants.GET_TABLE_RECORDS_URL, method = RequestMethod.POST, produces = {
			"application/json" })
	public ResponseEntity<String> getReportDetailsHandler(@RequestBody CustomerRequest customerRequest) {
		log.debug("Get client Data: " + customerRequest.toString());

		String jsonResult = msClientReportService.generateReport(customerRequest);
		msClientReportService.generateCitiPdf(jsonResult);
		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = ReportConstants.GET_REPORT_CONFIG_RECORDS_URL, method = RequestMethod.GET, produces = {
			"application/json" })
	public ResponseEntity<List<ReportConfig>> getReportConfigHandler() {
		log.debug("Get report config  Data: ");
		List<ReportConfig> jsonResult = msClientReportService.getReportConfigData();
		return new ResponseEntity<List<ReportConfig>>(jsonResult, HttpStatus.OK);
	}

	@RequestMapping(value = ReportConstants.SAVE_REPORT_CONFIG_RECORDS_URL, method = RequestMethod.POST, produces = {
			"application/json" })
	public ResponseEntity<String> saveReportConfigHandler(@RequestBody ReportConfig reportConfig) {
		log.debug("Save report config  Data: ");
		msClientReportService.saveReportConfigData(reportConfig);
		return new ResponseEntity<String>("Record save Successfully", HttpStatus.CREATED);
	}

	@RequestMapping(value = ReportConstants.SAVE_USER_SELECTION_REPORTS, method = RequestMethod.POST, produces = {
			"application/json" })
	public ResponseEntity<String> saveUserPreferenceHandler(@RequestBody CustomerRequest customerRequest) {
		log.debug("Get client Data: " + customerRequest.toString());

		String jsonResult = msClientReportService.saveGenerateReport(customerRequest);

		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);

	}
	
	@RequestMapping(value = ReportConstants.GET_SAVED_REPORTS, method = RequestMethod.GET, produces = {
			"application/json" })
	public ResponseEntity<List<UserPreferenceReportName>> getAllSavedReportsHandler() {
		log.debug("Get all saved reports  Data: ");
		List<UserPreferenceReportName> jsonResult = msClientReportService.getAllSavedReportsDetails();
		return new ResponseEntity<List<UserPreferenceReportName>>(jsonResult, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = ReportConstants.GET_SAVED_REPORTS_DATA, method = RequestMethod.GET, produces = {
			"application/json" })
	public ResponseEntity<String> getSavedReportsDataHandler(@PathVariable String reportObjectName) {
		log.debug("Get  saved reports  Data from dynamic table ");
		String jsonResult = msClientReportService.getAllSavedReportsData(reportObjectName.trim());
		msClientReportService.generateCitiPdf(jsonResult, reportObjectName.trim());
		return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}
	
	
}
