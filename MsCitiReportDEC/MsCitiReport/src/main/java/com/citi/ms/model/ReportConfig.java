package com.citi.ms.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
@Entity
@Table(name="REPORT_CONFIG")
public class ReportConfig implements Serializable {
	
	private static final long serialVersionUID = 5172151544689171186L;
	
	@Id
	@Column(name = "REPORT_REF_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@JsonIgnore
	private long reportRefId;
	
	@Column(name = "FIELD_NAME")
	private String fieldName;
	
	@Column(name = "COLUMN_NAME")
	private String columnName;
	
	@Column(name = "WHERE_CLAUSE")
	private  String  whereClause;
	
	@Column(name = "REPORT_NAME")
	private String reportName;
	
	@Column(name = "USER_NAME")
	private String userName;
	//@JsonIgnore
	@Column(name = "SEARCH_CRITERIA")
	private int searchCriteria;
	//@JsonIgnore
	@Column(name = "REPORT_SHOW_UI")
	private int aggregateCriteria;

}
