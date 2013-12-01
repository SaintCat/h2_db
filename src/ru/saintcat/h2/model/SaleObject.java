package ru.saintcat.h2.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="Object")
public class SaleObject {
	
	private Long id;	
	private String name;	
	private Long idOwner;
	private Long idObjectType;
	private Long price;
	private Long idContract;
	private BooleanProperty selected;
	
	public SaleObject(){
		this.selected = new SimpleBooleanProperty(false);
		name = null;
	}
	
	public SaleObject(SaleObject s){
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
	
	@Column(name="id_owner")
	public Long getIdOwner(){
		return idOwner;
	}
	
	@Column(name="id_objecttype")
	public Long getIdObjectType(){
		return idObjectType;
	}
	@Column(name="price")
	public Long getPrice(){
		return price;
	}
	@Column(name="id_contract")
	public Long getIdContract(){
		return idContract;
	}
	
	public void setId(Long i){
		id = i;		
	}
	
	public void setName(String s){
		name = s;
	}	
	
	public void setIdOwner(Long l){
		idOwner = l;
	}	
	public void setIdObjectType(Long b){
		idObjectType = b;
	}
	
	public void setPrice(Long b){
		price = b;
	}
	public void setIdContract(Long b){
		idContract=b;
	}
	
	public void setSelected(Boolean b){
		if(getIdContract()==null){
			
		this.selected.set(b);
		}
	}
	
	public BooleanProperty selectedProperty(){
		return selected;
	}
	
	
}
