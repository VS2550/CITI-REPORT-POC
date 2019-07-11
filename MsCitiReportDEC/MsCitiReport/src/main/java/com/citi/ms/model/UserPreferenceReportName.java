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

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="UserPreferenceReportName")
public class UserPreferenceReportName implements Serializable {

	private static final long serialVersionUID = -6277018163417796613L;
	
	@Id
	@Column(name = "PREFERENCE_ID") 
	//@JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long preferenceId;
	
	@Column(name = "REPORT_OBJECT_NAME")
	private String reportObjectName;
	
	@Column(name = "SAVED_SQL")
	private String savedSQL;
	
	@Column(name = "TABLE_NAME")
	private String tableName;
	
	@Column(name = "COLUMN_NAME")
	private String columnName;
	
	@Column(name = "PDF_FILE_PATH")
	private String pdfFilePath;
	

}
