package com.citi.ms.dao;

import java.util.List;


import com.citi.ms.model.Payment;

public interface PaymentDao /*extends JpaRepository<Payment, Long>*/{
	
	
	public   String GET_ALL_Record = "SELECT :columnName FROM :tableName";

/*
	@Query(value = GET_ALL_Record, nativeQuery = true)
	List<Object> gellDeatils( @Param("columnName") String columnName, @Param("tableName")String tableName );
	*/
	

}
