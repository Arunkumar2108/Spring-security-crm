package com.mahd.employee.controllers;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.mahd.employee.models.Accounts;
import com.mahd.employee.models.Company;
import com.mahd.employee.models.Contact;
import com.mahd.employee.models.User;
import com.mahd.employee.repository.AccountRepo;
import com.mahd.employee.repository.CompanyRepo;
import com.mahd.employee.repository.ContactRepo;
import com.mahd.employee.repository.UserRepo;

@RestController
@RequestMapping("/")
@Slf4j
@CrossOrigin(origins = "*")
public class ContactController {




	@Autowired
	private ContactRepo contactRepo;
	
	@Autowired
	private AccountRepo accountRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CompanyRepo companyRepo;
	
	
	@PostMapping("/api/v1/contact/create/{id}")
	private ResponseEntity<Contact> addAccount(@RequestBody Contact contact, Authentication authentication,@PathVariable(value = "id") Long acId){
		log.info("Account id: {}",acId);
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String userId = authorities.iterator().next().toString();
		Long id = Long.parseLong(userId);
		User loggedUser = userRepo.findById(id).orElseThrow(()-> new RuntimeException("User Not found for this Id:"+id));
		Company company = loggedUser.getCompany();

		Accounts accounts = accountRepo.findById(acId).orElseThrow(()-> new RuntimeException("Account not found for this id :"+acId));
		log.info("{}",accounts);

		contact.setUser(loggedUser);
		contact.setCompany(company);
		contact.setAccounts(accounts);
		contactRepo.save(contact);
		return ResponseEntity.ok().body(contact);
	}
	
	@GetMapping("/api/v1/contact")
	private List<Contact> getContact(){
		return contactRepo.findAll();
	}

	@PutMapping("/api/v1/contact/{id}")
	private ResponseEntity<Contact> updateAccount(@PathVariable(value = "id") Long id,@RequestBody Contact contact){
		Contact updatedContact = contactRepo.findById(id).orElseThrow(()-> new RuntimeException("Account not found for this id :"+id));
		updatedContact.setContactName(contact.getContactName());
		updatedContact.setContactCode(contact.getContactCode());
		updatedContact.setDescription(contact.getDescription());
		updatedContact.setAvatar(contact.getAvatar());
		updatedContact.setUser(updatedContact.getUser());
		updatedContact.setPhoneNo(contact.getPhoneNo());
		updatedContact.setAddress(contact.getAddress());
		updatedContact.setCity(contact.getCity());
		updatedContact.setState(contact.getState());
		updatedContact.setCountry(contact.getCountry());
		updatedContact.setUniqueId(updatedContact.getUniqueId());
		if(contact.getAccounts()== null && contact.getUser()== null && contact.getCompany()== null) {
			contactRepo.save(updatedContact);
		}
		else{
			String accountName = contact.getAccounts().getCustomerName();
			Accounts accounts = accountRepo.findByCustomerName(accountName);
			updatedContact.setAccounts(accounts);
			String userName = contact.getUser().getUserName();
			User user = userRepo.findByUserName(userName);
			updatedContact.setUser(user);
			String companyName = contact.getCompany().getCompanyName();
			Company company = companyRepo.findByCompanyName(companyName);
			updatedContact.setCompany(company);
			contactRepo.save(updatedContact);
		}
		return ResponseEntity.ok().body(updatedContact);	
	}
	
	@GetMapping("/api/v1/contact/{id}")
	protected Contact getSingleContact(@PathVariable(value = "id") Long id){
		Contact contact = contactRepo.findById(id).orElseThrow(()-> new RuntimeException("Contact not found for this id :"+id));
		return contact;
	}
	
	@DeleteMapping("/api/v1/contact/{id}")
	private ResponseEntity<Contact> deleteContact(@PathVariable(value = "id") Long id){
		Contact contact = contactRepo.findById(id).orElseThrow(()-> new RuntimeException("Contact not found for this id :"+id));
		contact.setDeleted(true);
		contactRepo.save(contact);
		return ResponseEntity.ok().body(contact);
	}
}
