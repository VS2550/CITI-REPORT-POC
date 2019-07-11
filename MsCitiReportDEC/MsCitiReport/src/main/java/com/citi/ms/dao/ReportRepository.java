package com.citi.ms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.citi.ms.dao.rowMapper.ReportConfigRowMapper;
import com.citi.ms.model.CustomerAccountDetails;
import com.citi.ms.model.Payment;
import com.citi.ms.model.ReportConfig;
import com.citi.ms.model.UserPreferenceReportName;

import lombok.extern.log4j.Log4j2;

@Repository @Log4j2
public class ReportRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public List<Object> getDeatils(String tableName, String columnNames, String searchCriteria){
		
		String sql = "SELECT "+columnNames +" FROM " + tableName + " WHERE " +searchCriteria;
		System.out.println(sql);
		List<Object> result = null;
		
		if(tableName.equalsIgnoreCase("REPORT_CONFIG")) {
			result = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ReportConfig.class));
		}
		if(tableName.equalsIgnoreCase("CUSTOMER_ACCOUNT_DETAILS")) {
			result = jdbcTemplate.query(sql, new BeanPropertyRowMapper(CustomerAccountDetails.class));
		}
		
		if(tableName.equalsIgnoreCase("PAYMENT")) {
			result = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Payment.class));
		}
		//List<ReportConfig> reportConfigList =  jdbcTemplate.query(sql,  new ReportConfigRowMapper());
		//result.forEach(item -> System.out.println(item));
		return result;
	}


	public List<String> getAllTableName() {
		String sql = "SELECT DISTINCT REPORT_NAME FROM REPORT_CONFIG";
		List<String> reportConfigList =  jdbcTemplate.query(sql,  new RowMapper() {

			  public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			        return rs.getString(1);
			  }

			});
		return reportConfigList;
	}


	public List<ReportConfig> getAllColumnNames(String tableName) {
		String sql = "SELECT COLUMN_NAME, FIELD_NAME FROM REPORT_CONFIG WHERE REPORT_NAME =" +"'"+tableName +"'"+ " AND REPORT_SHOW_UI = 1" ;
		log.info("SQL query for retrive all column names: "+sql);
		List<ReportConfig> reportConfigList =  jdbcTemplate.query(sql, new BeanPropertyRowMapper(ReportConfig.class));
		return reportConfigList;
	}


	public List<ReportConfig> getUITableHeadName(String columnOrCondition, String tableName) {
		String sql = null;
		String matchString = "'"+"*"+"'";
		if(columnOrCondition.trim().equalsIgnoreCase(matchString)) {
			sql = "SELECT COLUMN_NAME, WHERE_CLAUSE, FIELD_NAME FROM REPORT_CONFIG WHERE REPORT_NAME =" +"'"+tableName +"'" ;
		}else {
			 sql = "SELECT COLUMN_NAME, WHERE_CLAUSE, FIELD_NAME FROM REPORT_CONFIG WHERE COLUMN_NAME IN " +"( "+columnOrCondition +" )" +" AND REPORT_NAME =" +"'"+tableName +"'" ; 
		}
		log.info("SQL query for retrive field name and column names: "+sql);
		List<ReportConfig> reportConfigList =  jdbcTemplate.query(sql, new BeanPropertyRowMapper(ReportConfig.class));
		return reportConfigList;
	}


	public List<ReportConfig> getSearchColumnNames(String tableName) {
		String sql = "SELECT COLUMN_NAME, FIELD_NAME FROM REPORT_CONFIG WHERE REPORT_NAME =" +"'"+tableName +"'"+ " AND SEARCH_CRITERIA = 1" ;
		log.info("SQL query for retrive all column names: "+sql);
		List<ReportConfig> reportConfigList =  jdbcTemplate.query(sql, new BeanPropertyRowMapper(ReportConfig.class));
		return reportConfigList;
	}


	public List<Object> saveDeatils(String tableName, String processedColumnName, String searchCriteria) {
		String sql = "SELECT "+processedColumnName +" FROM " + tableName + " WHERE " +searchCriteria;

		
		return null;
	}


	public List<Object> getSavedReportsDataFromSQL(UserPreferenceReportName userPreferenceReportNameFromDb) {
		
		String sql = userPreferenceReportNameFromDb.getSavedSQL();
				System.out.println(sql);
		List<Object> result = null;
		String tableName = userPreferenceReportNameFromDb.getTableName();
		
		System.out.println("Table name : " +tableName +"SQL : "+sql);
		
		if(tableName.equalsIgnoreCase("REPORT_CONFIG")) {
			result = jdbcTemplate.query(sql, new BeanPropertyRowMapper(ReportConfig.class));
		}
		if(tableName.equalsIgnoreCase("CUSTOMER_ACCOUNT_DETAILS")) {
			result = jdbcTemplate.query(sql, new BeanPropertyRowMapper(CustomerAccountDetails.class));
		}
		
		if(tableName.equalsIgnoreCase("PAYMENT")) {
			result = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Payment.class));
		}
		//List<ReportConfig> reportConfigList =  jdbcTemplate.query(sql,  new ReportConfigRowMapper());
		//result.forEach(item -> System.out.println(item));
		return result;
	}
}
