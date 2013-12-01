package ru.saintcat.h2.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Client")
public class Client {
	
	private Long id;	
	private String name;	
	private Long telephone;
	
	public Client(){
		name = null;
	}
	
	public Client(Client s){
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
	
	@Column(name="telephone")
	public Long getTelephone(){
		return telephone;
	}
	
	public void setId(Long i){
		id = i;		
	}
	
	public void setName(String s){
		name = s;
	}	
	
	public void setTelephone(Long i){
		telephone = i;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + "]";
	}
}
