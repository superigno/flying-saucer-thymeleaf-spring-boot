/**
 * 
 */
package com.superigno.controller;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.superigno.service.PdfService;

/**
 * @author gquetua
 *
 */

@Controller
public class PdfController {

	//https://stackoverflow.com/a/62862021/3747493
	@GetMapping("/pdf")
	public ResponseEntity<Resource> downloadPdf() {
		FileSystemResource resource = new FileSystemResource("example/test.pdf");
		
		MediaType mediaType = MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);
		
		ContentDisposition disposition = ContentDisposition
				.inline() 
				//.attachment()				
				.filename(resource.getFilename()).build();
		headers.setContentDisposition(disposition);
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
	
	//https://stackoverflow.com/a/54463278/3747493
	@GetMapping(value="pdf2", produces= MediaType.APPLICATION_PDF_VALUE)
	public @ResponseBody byte[] print() {

	    try {
	        FileInputStream fis= new FileInputStream(new File("example/test.pdf"));
	        byte[] targetArray = new byte[fis.available()];
	        fis.read(targetArray);
	        return targetArray;
	    } catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    return null;
	}
	
	@Autowired
	PdfService pdfService;
	
	@GetMapping("/pdf3")
	public ResponseEntity<Resource> test2() throws Exception {
		ByteArrayResource resource = new ByteArrayResource(this.pdfService.generatePdf());		
		HttpHeaders headers = new HttpHeaders();
		ContentDisposition disposition = ContentDisposition.inline().filename("test.pdf").build();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(disposition);
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
	
	@GetMapping(value = "/pdf4", produces= MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<Resource> test3() throws Exception {
		ByteArrayResource resource = new ByteArrayResource(this.pdfService.generatePdf());		
		//ContentDisposition disposition = ContentDisposition.inline().filename("test.pdf").build();
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}
	
	@GetMapping("/ticket")
	public String ticket(Model model) {
		model.addAttribute("booking", this.pdfService.exampleData());
		return "ticket";
	}

}
