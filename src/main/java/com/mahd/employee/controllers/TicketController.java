package com.mahd.employee.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mahd.employee.models.Accounts;
import com.mahd.employee.models.Company;
import com.mahd.employee.models.Contact;
import com.mahd.employee.models.Tag;
import com.mahd.employee.models.Tickets;
import com.mahd.employee.models.User;
import com.mahd.employee.repository.AccountRepo;
import com.mahd.employee.repository.CompanyRepo;
import com.mahd.employee.repository.ContactRepo;
import com.mahd.employee.repository.TagRepo;
import com.mahd.employee.repository.TicketRepo;
import com.mahd.employee.repository.UserRepo;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class TicketController {

	@Autowired
	private TicketRepo ticketRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CompanyRepo companyRepo;
	
	@Autowired
	private TagRepo tagRepo;
	
	@Autowired
	private ContactRepo contactRepo;
	
	@Autowired
	private AccountRepo accountRepo;
	
	@PostMapping("/api/v1/ticket/create")
	private ResponseEntity<Tickets> addticket(@RequestBody Tickets ticket ){
		if(ticket.getCompany()!= null && ticket.getAgent()!= null && ticket.getContact()!= null && ticket.getAccounts()!= null && ticket.getTag()!= null) {
			String companyName = ticket.getCompany().getCompanyName();
			String tagName = ticket.getTag().getTagName();
			String contactName = ticket.getContact().getContactName();
			String accountName = ticket.getAccounts().getCustomerName();
			String agentName = ticket.getAgent().getUserName();
			
			Company company = companyRepo.findByCompanyName(companyName);
			Tag tag = tagRepo.findByTagName(tagName);
			Contact contact = contactRepo.findByContactName(contactName);
			Accounts accounts = accountRepo.findByCustomerName(accountName);
			User user = userRepo.findByUserName(agentName);
			
			ticket.setCompany(company);
			ticket.setTag(tag);
			ticket.setContact(contact);
			ticket.setAccounts(accounts);
			ticket.setAgent(user);
			
			ticketRepo.save(ticket);	
		}
		else {
			ticketRepo.save(ticket);
		}
		return ResponseEntity.ok().body(ticket);
	}
	
	@GetMapping("/api/v1/ticket")
	private List<Tickets> getTicketss(){
		return ticketRepo.findAll();
	}

	@PutMapping("/api/v1/ticket/{id}")
	private ResponseEntity<Tickets> updateTickets(@PathVariable(value = "id") Long id,@RequestBody Tickets ticket){
		Tickets updatedTickets = ticketRepo.findById(id).orElseThrow(()-> new RuntimeException("Tickets not found for this id :"+id));
		updatedTickets.setSubject(ticket.getSubject());
		updatedTickets.setPriority(ticket.getPriority());
		updatedTickets.setDescription(ticket.getDescription());
		updatedTickets.setStatus(ticket.getStatus());
		updatedTickets.setCompany(updatedTickets.getCompany());
		updatedTickets.setAgent(updatedTickets.getAgent());
		updatedTickets.setTag(updatedTickets.getTag());
		updatedTickets.setContact(updatedTickets.getContact());
		updatedTickets.setAccounts(updatedTickets.getAccounts());
		updatedTickets.setAssignedBy(ticket.getAssignedBy());
		updatedTickets.setTicketNo(updatedTickets.getTicketNo());
		updatedTickets.setAssignedTo(ticket.getAssignedTo());
		updatedTickets.setRequesterName(ticket.getRequesterName());
		updatedTickets.setRequesterEmail(ticket.getRequesterEmail());
		ticketRepo.save(updatedTickets);
		return ResponseEntity.ok().body(updatedTickets);	 
	}
	
	@GetMapping("/api/v1/ticket/{id}")
	private ResponseEntity<Tickets> getSingleticket(@PathVariable(value = "id") Long id){
		Tickets ticket = ticketRepo.findById(id).orElseThrow(()-> new RuntimeException("ticket not found for this id :"+id));
		return ResponseEntity.ok().body(ticket);
	}
	
	@DeleteMapping("/api/v1/ticket/{id}")
	private ResponseEntity<Tickets> deleteticket(@PathVariable(value = "id") Long id){
		Tickets ticket = ticketRepo.findById(id).orElseThrow(()-> new RuntimeException("ticket not found for this id :"+id));
		ticketRepo.delete(ticket);
		return ResponseEntity.ok().body(ticket);
	}
}
