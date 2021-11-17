/**
 * 
 */
package com.superigno.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.w3c.tidy.Tidy;
//import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.superigno.model.Booking;
import com.superigno.model.PickupDropoff;
import com.superigno.model.Ticket;

/**
 * @author gquetua
 * Reference: on https://github.com/tuhrig/Flying_Saucer_PDF_Generation
 */

@Service
public class PdfService {
	
	@Autowired
    private SpringTemplateEngine springTemplateEngine;
    
	public byte[] generatePdf() throws Exception {
		
		Booking booking = exampleData();
		Context context = new Context();
		context.setVariable("booking", booking);
		context.setLocale(LocaleContextHolder.getLocale());
		
		String renderedHtmlContent = this.springTemplateEngine.process("ticket", context);
		String xHtml = convertToXhtml(renderedHtmlContent);
		
		ITextRenderer renderer = new ITextRenderer();
				
		String baseUrl = FileSystems.getDefault().getPath("src", "main", "resources", "pdf", "static").toUri().toURL().toString();
		renderer.setDocumentFromString(xHtml, baseUrl);
		
		//For svg to work
		//ChainedReplacedElementFactory chainedReplacedElementFactory = new ChainedReplacedElementFactory();
		//chainedReplacedElementFactory.addFactory(new SVGSalamanderReplacedElementFactory());
		//renderer.getSharedContext().setReplacedElementFactory(chainedReplacedElementFactory);				
				
		//ChainingReplacedElementFactory chainingReplacedElementFactory = new ChainingReplacedElementFactory();
		//chainingReplacedElementFactory.addReplacedElementFactory(new SVGReplacedElementFactory());
		//renderer.getSharedContext().setReplacedElementFactory(chainingReplacedElementFactory);
		       
		
		renderer.layout();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		renderer.createPDF(outputStream);
		
		return outputStream.toByteArray();
	}

	public Booking exampleData() {
        Booking booking = new Booking();
        booking.setBookingDate("2021/11/11");
        booking.setBookingEmail("john.smith@gmail.com");
        booking.setBookingName("John Smith");
        
        booking.setSupplierEmail("xyz@aatkings.com");
        booking.setSupplierName("AAT Kings");
        booking.setSupplierPhone("1800 8123");
        booking.setSupplierRef("99-108630-208681");
        
        Ticket ticket = new Ticket();
        ticket.setId("4025");
        ticket.setLocalFees("50 AUD");
        ticket.setPassenger("John Smith");
        ticket.setProductName("A Town Like Alice Springs Tour");
        ticket.setProductTime("01:50pm");
        
        Ticket ticketTwo = new Ticket();
        ticketTwo.setId("4026");
        ticketTwo.setLocalFees("50 AUD");
        ticketTwo.setPassenger("Jane Smith");
        ticketTwo.setProductName("A Town Like Alice Springs Tour");
        ticketTwo.setProductTime("01:50pm");
        
        booking.setTickets(List.of(ticket, ticketTwo));
        
        PickupDropoff pickupDropoff = new PickupDropoff();
        pickupDropoff.setPickUp("Islander Hotel and Resort");
        pickupDropoff.setPickUpTime("05:30:00");
        pickupDropoff.setPickupNotes("Wait for the bus to arrive");
        pickupDropoff.setDropOff("Astor Metropole Hotel BEST WESTERN");
        pickupDropoff.setDropoffTime("18:00:00");        
        booking.setPickupDropoff(pickupDropoff);
        
        booking.setPassengerNotes(List.of("Allergic to peanuts", "Wheelchair bound"));
        booking.setSpecialNotes("This is an example of a special note");
        booking.setCancellationPolicy("This is a cancellation policy");
        
        return booking;
    }

    private String convertToXhtml(String html) throws UnsupportedEncodingException {
    	String utf8 = StandardCharsets.UTF_8.name();
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(utf8);
        tidy.setOutputEncoding(utf8);
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(utf8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString(utf8);        
    }
    
}
