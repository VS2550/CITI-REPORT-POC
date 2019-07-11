package com.citi.ms.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.citi.ms.dao.ReportConfigDao;
import com.citi.ms.dao.ReportRepository;
import com.citi.ms.dao.UserPreferenceReportNameDaoImpl;
import com.citi.ms.model.CustomerRequest;
import com.citi.ms.model.ReportConfig;
import com.citi.ms.model.UserPreferenceReportName;
import com.citi.ms.service.MsClientReportService;
import com.citi.ms.utils.CitiCommonMethods;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MsClientReportServiceImpl implements MsClientReportService {

	@Autowired
	private CitiCommonMethods citiCommonMethods;

	@Autowired
	private ReportRepository reportRepository;

	@Autowired
	private UserPreferenceReportNameDaoImpl userPreferenceReportNameDaoImpl;

	@Override
	public String generateReport(CustomerRequest customerRequest) {
		String tableName = null;

		if (customerRequest.getReportName() != null) {
			tableName = customerRequest.getReportName().trim();
		} else {
			return "Must pass the table name";
		}
		String columnName = customerRequest.getColumnName();
		System.out.println(">>>>>>>>>>>>>> " + columnName);
		String trimmedColumnName = columnName.trim();
		System.out.println(trimmedColumnName);

		String processedColumnName = processFieldName(trimmedColumnName);
		if (processedColumnName.length() == 0) {
			return "column name not define";
		}
		String searchCriteria = customerRequest.getSearchCriteria().trim();
		if (searchCriteria.length() == 0) {
			searchCriteria = "0=0";
		}
		log.info("Table name :" + tableName + " column name :" + processedColumnName + " search criteria "
				+ searchCriteria);
		List<Object> reportConfigList = reportRepository.getDeatils(tableName, processedColumnName, searchCriteria);

		String[] columnNameArr = processedColumnName.split(",");
		String columnOrCondition = new String();
		for (String colName : columnNameArr) {
			columnOrCondition += "'" + colName.trim() + "' ,";
		}

		List<ReportConfig> tableUiHead = reportRepository.getUITableHeadName(
				columnOrCondition.toUpperCase().substring(0, columnOrCondition.length() - 1), tableName);
		Map<String, String> tableUiHeadMap = new HashMap<String, String>();
		for (ReportConfig reportConfig : tableUiHead) {
			tableUiHeadMap.put(reportConfig.getWhereClause(), reportConfig.getFieldName());
		}
		String tableUiHeadStr = citiCommonMethods.objectToJson(tableUiHeadMap);
		// imp krushna
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("UI_Table_Heading", tableUiHeadMap);
		map.put("UI_Table_Data", reportConfigList);

		String reportConfigStr = citiCommonMethods.objectToJson(reportConfigList);
		Map<String, String> columnNameMap = new HashMap<String, String>();
		columnNameMap.put("UI_Table_Heading", tableUiHeadStr);
		columnNameMap.put("UI_Table_Data", reportConfigStr);

		return citiCommonMethods.objectToJson(map);

	}

	

	private String processFieldName(String fieldName) {

		if (fieldName.equalsIgnoreCase("all") || fieldName.equalsIgnoreCase("*") || fieldName.length() == 0) {
			return "*";
		}

		return fieldName.toString();

	}

	@Override
	public String getAllTableNameService() {
		List<String> tableNameList = reportRepository.getAllTableName();
		Map<String, String> tableNameMap = new TreeMap<String, String>();
		for (String tableName : tableNameList) {
			tableNameMap.put(tableName, tableName.replaceAll("_", " "));
		}
		String jsonObj = citiCommonMethods.objectToJson(tableNameMap);
		return jsonObj;
	}

	@Override
	public String getColumnsName(String tableName) {

		List<ReportConfig> columnNameList = reportRepository.getAllColumnNames(tableName);
		Map<String, String> columnNameMap = new TreeMap<String, String>();
		for (ReportConfig reportConfig : columnNameList) {
			columnNameMap.put(reportConfig.getColumnName(), reportConfig.getFieldName());
		}
		List<ReportConfig> searchColumnNameList = reportRepository.getSearchColumnNames(tableName);
		Map<String, String> searchColumnNameMap = new TreeMap<String, String>();
		for (ReportConfig reportConfig : searchColumnNameList) {
			searchColumnNameMap.put(reportConfig.getColumnName(), reportConfig.getFieldName());
		}
		Map<String, Object> reportUIColumnName = new TreeMap<String, Object>();
		reportUIColumnName.put("TABLE_COLUMN_NAME", columnNameMap);
		reportUIColumnName.put("SEARCH_COLUMN_NAME", searchColumnNameMap);
		log.debug(reportUIColumnName);
		String jsonObj = citiCommonMethods.objectToJson(reportUIColumnName);
		return jsonObj;
	}

	@Autowired
	ReportConfigDao reportConfigDao;

	@Override
	public List<ReportConfig> getReportConfigData() {
		log.debug("getting all the record from report config");
		List<ReportConfig> allReportRecord = reportConfigDao.findAll();

		return allReportRecord;
	}

	@Override
	public void saveReportConfigData(ReportConfig reportConfig) {

		ReportConfig trimmedReportConfig = new ReportConfig();
		trimmedReportConfig.setColumnName(reportConfig.getColumnName().trim());
		trimmedReportConfig.setReportName(reportConfig.getReportName().trim());
		trimmedReportConfig.setFieldName(reportConfig.getFieldName().trim());
		trimmedReportConfig.setSearchCriteria(reportConfig.getSearchCriteria());
		trimmedReportConfig.setAggregateCriteria(reportConfig.getAggregateCriteria());

		reportConfigDao.save(trimmedReportConfig);

	}

	@Override
	public String saveGenerateReport(CustomerRequest customerRequest) {
		String tableName = null;

		if (customerRequest.getReportName() != null) {
			tableName = customerRequest.getReportName().trim();
		} else {
			return "Must pass the table name";
		}
		String columnName = customerRequest.getColumnName();
		String trimmedColumnName = columnName.trim();

		String processedColumnName = processFieldName(trimmedColumnName);
		if (processedColumnName.length() == 0) {
			return "column name not define";
		}
		String searchCriteria = customerRequest.getSearchCriteria();
		if (searchCriteria == null ) {
			searchCriteria = "0=0";
		}
		if (searchCriteria.length() == 0 ) {
			searchCriteria = "0=0";
		}
		log.info("Table name :" + tableName + " column name :" + processedColumnName + " search criteria "
				+ searchCriteria);

		String sql = "SELECT " + processedColumnName + " FROM " + tableName + " WHERE " + searchCriteria;
		String userPreferenceName = customerRequest.getUserPreferenceReportName();
		long uniqueFileName = citiCommonMethods.getTransactionId();
		String pdfFileName = userPreferenceName +"_" + uniqueFileName +".pdf";

		UserPreferenceReportName userPreferenceReportName = new UserPreferenceReportName();
		userPreferenceReportName.setReportObjectName(userPreferenceName.trim());
		userPreferenceReportName.setSavedSQL(sql);
		userPreferenceReportName.setTableName(tableName.trim());
		userPreferenceReportName.setColumnName(processedColumnName);
		userPreferenceReportName.setPdfFilePath(pdfFileName);

		userPreferenceReportNameDaoImpl.save(userPreferenceReportName);

		return "Saved Successfully";

	}

	@Override
	public List<UserPreferenceReportName> getAllSavedReportsDetails() {
		List<UserPreferenceReportName> savedReportList = userPreferenceReportNameDaoImpl.findAll();
		return savedReportList;
	}

	@Override
	public String getAllSavedReportsData(String reportObjectName) {
		UserPreferenceReportName userPreferenceReportNameFromDb = userPreferenceReportNameDaoImpl
				.findByReportObjectName(reportObjectName);
		List<Object> reportdata = reportRepository.getSavedReportsDataFromSQL(userPreferenceReportNameFromDb);

		String processedColumnName = userPreferenceReportNameFromDb.getColumnName();
		String tableName = userPreferenceReportNameFromDb.getTableName();
System.out.println(" .................. " + processedColumnName );
		String[] columnNameArr = processedColumnName.split(",");
		String columnOrCondition = new String();
		for (String colName : columnNameArr) {
			columnOrCondition += "'" + colName.trim() + "' ,";
		}

		List<ReportConfig> tableUiHead = reportRepository.getUITableHeadName(columnOrCondition.toUpperCase().substring(0, columnOrCondition.length() - 1), tableName);
		//List<ReportConfig> tableUiHead = reportRepository.getUITableHeadName(userPreferenceReportNameFromDb.getColumnName(), tableName);
		Map<String, String> tableUiHeadMap = new HashMap<String, String>();
		for (ReportConfig reportConfig : tableUiHead) {
			tableUiHeadMap.put(reportConfig.getWhereClause(), reportConfig.getFieldName());
		}
		// imp krushna
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("UI_Table_Heading", tableUiHeadMap);
		map.put("UI_Table_Data", reportdata);

		return citiCommonMethods.objectToJson(map);

	}
	
	
	public  String generatePDFFromHTML(String fileName) {
		generatePdf(fileName);
	    Document document = new Document();
	    document.setPageSize(PageSize.A4);
        document.setMargins(2, 2, 2, 2);
	    PdfWriter writer = null;
	    String pdfPath = "C:\\Users\\krush\\Downloads\\html.pdf";
		try {
			writer = PdfWriter.getInstance(document,
			  new FileOutputStream(pdfPath));
			 document.open();
			 XMLWorkerHelper.getInstance().parseXHtml(writer, document,
					  new FileInputStream(fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			document.close();
		}
	    
	    return pdfPath;
	}
	
	 private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,
	            Font.BOLD);
	    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
	            Font.NORMAL, BaseColor.RED);
	    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
	            Font.BOLD);
	    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10,
	            Font.BOLD);
	    private static Font small = new Font(Font.FontFamily.TIMES_ROMAN, 10 );
	
	public void generatePdf(String data) {
		Document document = new Document();
		 String pdfPath = "C:\\Users\\krush\\Downloads\\html2.pdf";
		 try {
        PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
        document.open();
        addMetaData(document);
        addTitlePage(document);
        addContent(document);
		 }
		 catch(Exception e) {
			 log.error("exception: " +e);
		 }
		 finally {
			 document.close();
		 }
        
	}
	
	private static void addMetaData(Document document) {
        document.addTitle("Citi Reports PDF");
        document.addSubject("Customer Reports");
        //document.addKeywords("Java, PDF, iText");
        document.addAuthor("LTI");
        document.addCreator("Krushna");
    }

    private static void addTitlePage(Document document)
            throws DocumentException, MalformedURLException, IOException {
    	String fileLocation = new File("static\\images").getAbsolutePath() + "\\citiLogo.png";
    	Image image = Image.getInstance("C:\\krushna\\Angular-DynamicReports\\src\\assets\\citiLogo.png");
		  image.setAbsolutePosition(400f,730f);
		  image.scaleAbsolute(120f, 40f);
	      document.add(image);
	      
	      Date date = new Date();
	  	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	      String  current_date = dateFormat.format(date);
    	
    	
        Paragraph preface = new Paragraph();
        // We add one empty line
        addEmptyLine(preface, 1);
        // Lets write a big header
        preface.add(new Paragraph("CITI BANK REPORT", catFont));

        addEmptyLine(preface, 1);
        // Will create: Report generated by: _name, _date
        preface.add(new Paragraph(
                "Report generated by:  LTI  " ,
                small));
        preface.add(new Paragraph(
                "Report generated Date:  " + current_date+" ",
                small));
       // preface.setAlignment(Element.ALIGN_LEFT);
        addEmptyLine(preface, 3);

        document.add(preface);
        // Start a new page
       // document.newPage();
    }

    private static void addContent(Document document) throws DocumentException {
        Anchor anchor = new Anchor("First Chapter", catFont);
        anchor.setName("First Chapter");

        // Second parameter is the number of the chapter
        Chapter catPart = new Chapter(new Paragraph(anchor), 1);

        Paragraph subPara = new Paragraph("Subcategory 1", subFont);
        Section subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Hello"));

        subPara = new Paragraph("Subcategory 2", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("Paragraph 1"));
        subCatPart.add(new Paragraph("Paragraph 2"));
        subCatPart.add(new Paragraph("Paragraph 3"));

        // add a list
        //createList(subCatPart);
        Paragraph paragraph = new Paragraph();
        addEmptyLine(paragraph, 5);
        subCatPart.add(paragraph);

        // add a table
        createTable(subCatPart);

        
        // now add all this to the document
        document.add(catPart);

        // Next section
        anchor = new Anchor("Second Chapter", catFont);
        anchor.setName("Second Chapter");

        // Second parameter is the number of the chapter
        catPart = new Chapter(new Paragraph(anchor), 1);

        subPara = new Paragraph("Subcategory", subFont);
        subCatPart = catPart.addSection(subPara);
        subCatPart.add(new Paragraph("This is a very important message"));

        // now add all this to the document
        document.add(catPart);

    }

    private static void createTable(Section subCatPart)
            throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);

    }

    /*private static void createList(Section subCatPart) {
        @SuppressWarnings("rawtypes")
		List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }*/

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    public void generateCitiPdf(String tableData) {
    	Document document = new Document();
		 String pdfPath = "C:\\Users\\krush\\Downloads\\citi2.pdf";
		 try {
       PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
       document.open();
       addMetaData(document);
       addTitlePage(document);
       createTable(document, tableData);
       //addContent(document);
		 }
		 catch(Exception e) {
			 log.error("exception: " +e);
		 }
		 finally {
			 document.close();
		 }
		
	}
    
    private  void createTable(Document doc, String data)
            throws DocumentException {
    	
    	JSONObject json = citiCommonMethods.stringToJSON(data);
        Map<String, String> tableUiHeadMap = (Map<String, String>) json.get("UI_Table_Heading");
        List<Object> reportdata = (List<Object>) json.get("UI_Table_Data");
        PdfPTable pdfTable = new PdfPTable(tableUiHeadMap.size());
        pdfTable.setWidthPercentage(100);
       
        for(String value : tableUiHeadMap.values() ) {
        	PdfPCell d1 = new PdfPCell(new Phrase(value));
            d1.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfTable.addCell(d1);
        	
        }
        
        Map<String, String> valueMap = new HashMap<String, String>(); 
        for(Object o : reportdata ) {
        	
        	Map<String, String> rowMap = (Map<String, String>) o;
        	 
        	for (Map.Entry<String, String> entry : rowMap.entrySet()) {
        	    PdfPCell d2 = new PdfPCell(new Phrase(entry.getValue()));
                d2.setHorizontalAlignment(Element.ALIGN_LEFT);
                pdfTable.addCell(d2);
        	}
        }
       
        

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);


        doc.add(pdfTable);

    }



	@Override
	public String generateCitiPdf(String jsonResult, String reportObjectName) {
		Document document = new Document();
		long uniqueFileName = citiCommonMethods.getTransactionId();
		String pdfFileName =  reportObjectName +"_" + uniqueFileName +".pdf";
		 String pdfPath = "C:\\Users\\krush\\Downloads\\citiPdf\\"+pdfFileName;
		 try {
      PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
      document.open();
      addMetaData(document);
      addTitlePage(document);
      createTable(document, jsonResult);
      //addContent(document);
		 }
		 catch(Exception e) {
			 log.error("exception: " +e);
		 }
		 finally {
			 document.close();
		 }
		 
		 return pdfPath;
	}
}
