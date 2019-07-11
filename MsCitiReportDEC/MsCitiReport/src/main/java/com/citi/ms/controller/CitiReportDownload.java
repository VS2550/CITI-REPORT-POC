package com.citi.ms.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.citi.ms.service.MsClientReportService;
import com.citi.ms.utils.ReportConstants;

import lombok.extern.log4j.Log4j2;

@CrossOrigin
@RestController
@Log4j2
@RequestMapping(ReportConstants.CITI_DOWNLOAD_URL)
public class CitiReportDownload {

	private static final String DIRECTORY = "C:/PDF";
	private static final String DEFAULT_FILE_NAME = "java-tutorial.pdf";
	
	@Autowired
	private MsClientReportService msClientReportService;

	@GetMapping("/Testpdf")
	public ResponseEntity<ByteArrayResource> downloadFile2(
			@RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {
		MediaType mediaType = MediaType.APPLICATION_PDF;
		// MediaType mediaType =
		// MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
		System.out.println("fileName: " + fileName);
		// System.out.println("mediaType: " + mediaType);
		String pdfPath = "C:\\Users\\krush\\Downloads\\LearnJava.pdf";

		Path path = Paths.get(DIRECTORY + "/" + DEFAULT_FILE_NAME);
		Path path1 = Paths.get(pdfPath);
		byte[] data = Files.readAllBytes(path1);
		ByteArrayResource resource = new ByteArrayResource(data);

		return ResponseEntity.ok()
				// Content-Disposition
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path1.getFileName().toString())
				// Content-Type
				.contentType(mediaType) //
				// Content-Lengh
				.contentLength(data.length) //
				.body(resource);
	}
	
	
	@GetMapping("/pdf")
	public ResponseEntity<ByteArrayResource> downloadHtmlToPdf(
			@RequestParam(defaultValue = DEFAULT_FILE_NAME) String fileName) throws IOException {
		MediaType mediaType = MediaType.APPLICATION_PDF;
		// MediaType mediaType =
		// MediaTypeUtils.getMediaTypeForFileName(this.servletContext, fileName);
		System.out.println("fileName: " + fileName);
		// System.out.println("mediaType: " + mediaType);
		//String pdfPath = "C:\\Users\\krush\\Downloads\\LearnJava.pdf";
		String htmlPath = "C:\\Users\\krush\\Downloads\\test4.html";
		String pdfPath =  msClientReportService.generatePDFFromHTML(htmlPath);
		log.debug("pdf path : " + pdfPath);

		Path path = Paths.get(DIRECTORY + "/" + DEFAULT_FILE_NAME);
		Path path1 = Paths.get(pdfPath);
		byte[] data = Files.readAllBytes(path1);
		ByteArrayResource resource = new ByteArrayResource(data);

		return ResponseEntity.ok()
				// Content-Disposition
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path1.getFileName().toString())
				// Content-Type
				.contentType(mediaType) //
				// Content-Lengh
				.contentLength(data.length) //
				.body(resource);
	}
	
	// download pdf from unique pdf file name
	@RequestMapping(value = ReportConstants.DOWNLOAD_SAVED_REPORTS_DATA, method = RequestMethod.GET, produces = {
			"application/json" })
	public ResponseEntity<ByteArrayResource> downloadSavedReportsDataHandler(@PathVariable String reportObjectName) {
		log.debug("DOWNLOAD  saved reports  Data from dynamic table ");
		String jsonResult = msClientReportService.getAllSavedReportsData(reportObjectName.trim());
		//String pdfName = msClientReportService.generateCitiPdf(jsonResult, reportObjectName.trim() );
		
		//DOWNLOAD
		MediaType mediaType = MediaType.APPLICATION_PDF;
		//String htmlPath = "C:\\Users\\krush\\Downloads\\test4.html";
		String pdfPath =  msClientReportService.generateCitiPdf(jsonResult, reportObjectName.trim() );
		log.debug("pdf path : " + pdfPath);

		//Path path = Paths.get(DIRECTORY + "/" + DEFAULT_FILE_NAME);
		Path path1 = Paths.get(pdfPath);
		byte[] data = null;
		try {
			data = Files.readAllBytes(path1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayResource resource = new ByteArrayResource(data);

		return ResponseEntity.ok()
				// Content-Disposition
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path1.getFileName().toString())
				// Content-Type
				.contentType(mediaType) //
				// Content-Lengh
				.contentLength(data.length) //
				.body(resource);
		
		//return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}
	
	
	
	// me test
	
	// download pdf from unique pdf file name
	@RequestMapping(value = ReportConstants.DOWNLOAD_SAVED_REPORTS_DATA_1, method = RequestMethod.GET, produces = {
			"application/json" })
	public String downloadSavedReportsDataHandler1(@PathVariable String reportObjectName) {
		log.debug("DOWNLOAD 2 saved reports  Data from dynamic table ");
		String jsonResult = msClientReportService.getAllSavedReportsData(reportObjectName.trim());
		//String pdfName = msClientReportService.generateCitiPdf(jsonResult, reportObjectName.trim() );
		
		//DOWNLOAD
		MediaType mediaType = MediaType.APPLICATION_PDF;
		//String htmlPath = "C:\\Users\\krush\\Downloads\\test4.html";
		String pdfPath =  msClientReportService.generateCitiPdf(jsonResult, reportObjectName.trim() );
		log.debug("pdf path : " + pdfPath);

		//Path path = Paths.get(DIRECTORY + "/" + DEFAULT_FILE_NAME);
		Path path1 = Paths.get(pdfPath);
		byte[] data = null;
		try {
			data = Files.readAllBytes(path1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayResource resource = new ByteArrayResource(data);

		return pdfPath;
		
		//return new ResponseEntity<String>(jsonResult, HttpStatus.OK);
	}

	

}
