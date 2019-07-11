package com.citi.ms.dao.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.citi.ms.model.ReportConfig;

public class ReportConfigRowMapper implements RowMapper<ReportConfig> {

	@Override
	public ReportConfig mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ReportConfig reportConfig = new ReportConfig();
		System.out.println("Hello");
		reportConfig.setFieldName(rs.getString("FIELD_NAME"));
		reportConfig.setColumnName(rs.getString("COLUMN_NAME"));
		reportConfig.setReportName(rs.getString("REPORT_NAME"));
		reportConfig.setSearchCriteria(rs.getInt("SEARCH_CRITERIA"));
		//reportConfig.setWhereClause(rs.getString("WHERE_CLAUSE"));
		reportConfig.setUserName(rs.getString("USER_NAME"));
		reportConfig.setAggregateCriteria(rs.getInt("AGGREGATE_CRITERIA"));
		reportConfig.setReportRefId(rs.getLong("REPORT_REF_ID"));
		
		return reportConfig;
	}

	


}
