package com.qa.springDatabase.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.springDatabase.exception.ResourceNotFoundException;
import com.qa.springDatabase.model.SpringDatabaseApplicationModel;
import com.qa.springDatabase.repository.SpringBootRepository;

@RestController
@RequestMapping("/api")
public class SpringBootController {

	@Autowired
	SpringBootRepository myRepository;

	@PostMapping("/person")
	public SpringDatabaseApplicationModel createPerson(@Valid @RequestBody SpringDatabaseApplicationModel sDAM) {
		return myRepository.save(sDAM);
	}

	@GetMapping("person/{id}")
	public SpringDatabaseApplicationModel getPersonID
	(@PathVariable(value = "id")Long personID) {
		return myRepository.findById(personID).orElseThrow(()->new 
				ResourceNotFoundException("SpringDatabaseApplicationModel", "id", personID)); 
	}

	@GetMapping("/person")
	public List<SpringDatabaseApplicationModel> getAllPeople() {
		return myRepository.findAll();
	}
	
	@PutMapping("/person/{id}")
	public SpringDatabaseApplicationModel updatePerson(@PathVariable(value ="id")Long personID,
			@Valid @RequestBody SpringDatabaseApplicationModel personDetails) {
		SpringDatabaseApplicationModel sDAM = myRepository.findById(personID).orElseThrow(()->
		new ResourceNotFoundException("Person", "id", personID));
		
		sDAM.setName(personDetails.getName());
		sDAM.setAddress(personDetails.getAddress());
		sDAM.setAge(personDetails.getAge());
		
		SpringDatabaseApplicationModel updateData = myRepository.save(sDAM);
		return updateData;
	}
	
	@DeleteMapping("/person/{id}")
	public ResponseEntity<?> deletePerson(@PathVariable(value = "id")Long personID){
		SpringDatabaseApplicationModel sDAM = myRepository.findById(personID).orElseThrow(()->
		new ResourceNotFoundException("Person", "id", personID));
		
		myRepository.delete(sDAM);
		return ResponseEntity.ok().build();
	}
}
