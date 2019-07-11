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
@ToString @Table(name = "CUSTOMER_ACCOUNT_DETAILS")
public class CustomerAccountDetails implements Serializable {
	private static final long serialVersionUID = 1917341195621639139L;

	@Id
	@Column(name = "CUSTOMER_REF_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long customerRefId;
	
	@Column(name = "CUSTOMER_ADDRESS")
	private String customerAddress;
	
	@Column(name = "AMOUNT")
	private String amount;
	
	@Column(name = "BANK_NAME")
	private String bankName;
	
	@Column(name = "CUSTOMER_NAME")
	private String customerName;
	
	@Column(name = "CREATE_DATE")
	private String createDate;
}
