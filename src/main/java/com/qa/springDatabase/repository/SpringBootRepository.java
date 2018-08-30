package com.qa.springDatabase.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.springDatabase.model.SpringDatabaseApplicationModel;

@Repository
public interface SpringBootRepository extends JpaRepository<SpringDatabaseApplicationModel, Long> {

}
