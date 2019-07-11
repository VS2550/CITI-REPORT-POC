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
@Table(name="PAYMENT")
public class Payment implements Serializable {

	private static final long serialVersionUID = 8048834424070196923L;
	@Column(name = "PAYMENT_ID")
	private String paymentId;
	@Id
	@Column(name = "REF_ID") @JsonIgnore
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long refId;
	@Column(name = "TRANSACTION_TYPE")
	private String transactionType;
	@Column(name = "AMOUNT")
	private String amount;
	@Column(name = "BANK_NAME")
	private String bankName;
	@Column(name = "CUSTOMER_NAME")
	private String customerName;
	@Column(name = "CREATE_DATE")
	private String createDate;
}
