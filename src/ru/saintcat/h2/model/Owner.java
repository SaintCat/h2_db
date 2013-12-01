package ru.saintcat.h2.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Owner")
public class Owner {
	
	private Long id;	
	private String name;	
	private String email;
	
	public Owner(){
		name = null;
	}
	
	public Owner(Owner s){
		name = s.getName();
	}	
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="id")
	public Long getId() {
		return id;
	}
	
	@Column(name="name")
	public String getName(){
		return name;
	}
	
	@Column(name="email")
	public String getEmail(){
		return email;
	}
	
	public void setId(Long i){
		id = i;		
	}
	
	public void setName(String s){
		name = s;
	}	
	
	public void setEmail(String l){
		email = l;
	}

	@Override
	public String toString() {
		return "["+ name + "]";
	}	
}
