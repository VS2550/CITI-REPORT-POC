package com.citi.ms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder @ToString
public class CustomerRequest {
	
	//private String fieldName;
	private String whereClause;
	private String reportName;
	private String userName;
	private String searchCriteria;
	private String aggrigateCriteria;
	private String columnName;
	private String userPreferenceReportName;
	

}
