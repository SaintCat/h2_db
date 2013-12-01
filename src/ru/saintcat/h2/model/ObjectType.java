package ru.saintcat.h2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ObjectType")
public class ObjectType {
	
	private Long id;	
	private String type;
	private String description;
	
	public ObjectType(){
		type = null;
	}
	
	public ObjectType(ObjectType s){
		type = s.getType();
	}	
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="id")
	public Long getId() {
		return id;
	}
	
	@Column(name="type")
	public String getType(){
		return type;
	}
	
	
	public void setId(Long i){
		id = i;		
	}
	
	public void setType(String s){
		type = s;
	}
	

	@Override
	public String toString() {
		return "["+type + "]";
	}

	public String getDescription() {
		return description;
	}
	@Column(name = "description")
	public void setDescription(String description) {
		this.description = description;
	}		
}
