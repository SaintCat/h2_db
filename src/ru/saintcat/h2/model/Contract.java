package ru.saintcat.h2.model;

import java.util.Calendar;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import ru.saintcat.h2.util.CalendarUtil;

@Entity
@Table(name = "Contract")
public class Contract {

	private Long id;
	private Long idClient;
	private Long idWorker;
	private Long sum;
	private Calendar date;

	public Contract() {
		date = Calendar.getInstance();
	}

	public Contract(Contract s) {
	
	}

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "id")
	public Long getId() {
		return id;
	}

	@Column(name = "id_client")
	public Long getIdClient() {
		return idClient;
	}

	public void setIdClient(Long idClient) {
		this.idClient = idClient;
	}

	@Column(name = "id_worker")
	public Long getIdWorker() {
		return idWorker;
	}

	public void setIdWorker(Long idWorker) {
		this.idWorker = idWorker;
	}

	@Column(name = "sum")
	public Long getSum() {
		return sum;
	}

	public void setSum(Long sum) {
		this.sum = sum;
	}

	@Column(name = "date")
	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public StringProperty date2Property(){
		return new SimpleStringProperty(CalendarUtil.format(date));
	}

	@Override
	public String toString() {
		return "Contract [id=" + id + ", sum=" + sum + "]";
	}
	

}
