package com.aricionur.springmvc.service;

import org.springframework.stereotype.Service;

import com.aricionur.springmvc.entity.Person;

@Service("prototype")
public class PersonService {

	Person person;
	
	public void setPerson(Person person) {
		this.person = person;
	}
	public Person getPerson() {
		return person;
	}
}
