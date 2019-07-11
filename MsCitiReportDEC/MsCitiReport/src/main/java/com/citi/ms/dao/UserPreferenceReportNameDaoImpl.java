package com.citi.ms.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citi.ms.model.UserPreferenceReportName;

public interface UserPreferenceReportNameDaoImpl extends JpaRepository<UserPreferenceReportName, Long> {

	UserPreferenceReportName findByReportObjectName(String reportObjectName);

}
