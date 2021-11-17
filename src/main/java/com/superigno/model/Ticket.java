/**
 * 
 */
package com.superigno.model;

/**
 * @author gquetua
 *
 */
public class Ticket {
	
	private String id;
	private String productName;
	private String productTime;
	private String passenger;
	private String localFees;
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductName() {
		return this.productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductTime() {
		return this.productTime;
	}
	public void setProductTime(String productTime) {
		this.productTime = productTime;
	}
	public String getPassenger() {
		return this.passenger;
	}
	public void setPassenger(String passenger) {
		this.passenger = passenger;
	}
	public String getLocalFees() {
		return this.localFees;
	}
	public void setLocalFees(String localFees) {
		this.localFees = localFees;
	}
	

}
