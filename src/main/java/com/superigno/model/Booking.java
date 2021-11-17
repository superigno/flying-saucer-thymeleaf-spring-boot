/**
 * 
 */
package com.superigno.model;

import java.util.List;

/**
 * @author gquetua
 *
 */

public class Booking {
    
	private String bookingDate;
	private String bookingName;
	private String bookingEmail;
	private String supplierName;
	private String supplierEmail;
	private String supplierPhone;
	private String supplierRef;
	private List<Ticket> tickets;	
	private PickupDropoff pickupDropoff;
	private List<String> passengerNotes;
	private String specialNotes;
	private String cancellationPolicy;
	
	public String getBookingDate() {
		return this.bookingDate;
	}
	public void setBookingDate(String bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getBookingName() {
		return this.bookingName;
	}
	public void setBookingName(String bookingName) {
		this.bookingName = bookingName;
	}
	public String getBookingEmail() {
		return this.bookingEmail;
	}
	public void setBookingEmail(String bookingEmail) {
		this.bookingEmail = bookingEmail;
	}
	public String getSupplierName() {
		return this.supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierEmail() {
		return this.supplierEmail;
	}
	public void setSupplierEmail(String supplierEmail) {
		this.supplierEmail = supplierEmail;
	}
	public String getSupplierPhone() {
		return this.supplierPhone;
	}
	public void setSupplierPhone(String supplierPhone) {
		this.supplierPhone = supplierPhone;
	}
	public String getSupplierRef() {
		return this.supplierRef;
	}
	public void setSupplierRef(String supplierRef) {
		this.supplierRef = supplierRef;
	}
	public List<Ticket> getTickets() {
		return this.tickets;
	}
	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
	public PickupDropoff getPickupDropoff() {
		return this.pickupDropoff;
	}
	public void setPickupDropoff(PickupDropoff pickupDropoff) {
		this.pickupDropoff = pickupDropoff;
	}
	public List<String> getPassengerNotes() {
		return this.passengerNotes;
	}
	public void setPassengerNotes(List<String> passengerNotes) {
		this.passengerNotes = passengerNotes;
	}
	public String getSpecialNotes() {
		return this.specialNotes;
	}
	public void setSpecialNotes(String specialNotes) {
		this.specialNotes = specialNotes;
	}
	public String getCancellationPolicy() {
		return this.cancellationPolicy;
	}
	public void setCancellationPolicy(String cancellationPolicy) {
		this.cancellationPolicy = cancellationPolicy;
	}
	
}