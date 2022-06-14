package com.mahd.employee.controllers;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.mahd.employee.models.Company;
import com.mahd.employee.models.User;
import com.mahd.employee.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.mahd.employee.models.CannedMessage;
import com.mahd.employee.repository.CannedMsgRepo;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class CannedMsgController {

	@Autowired
	private CannedMsgRepo cannedMsgRepo;

	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/api/v1/cannedMsg/create")
	private ResponseEntity<CannedMessage> addcannedMsg(@RequestBody CannedMessage cannedMsg, Authentication authentication){
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String userId = authorities.iterator().next().toString();
		Long id = Long.parseLong(userId);
		User loggedUser = userRepo.findById(id).orElseThrow(()-> new RuntimeException("User Not found for this Id:"+id));
		Company company = loggedUser.getCompany();
		cannedMsg.setUser(loggedUser);
		cannedMsg.setCompany(company);
		cannedMsgRepo.save(cannedMsg);
		return ResponseEntity.ok().body(cannedMsg);
	}
	
	@GetMapping("/api/v1/cannedMsg")
	private List<CannedMessage> getCannedMessages(){
		return cannedMsgRepo.findAll();
	}

	@PutMapping("/api/v1/cannedMsg/{id}")
	private ResponseEntity<CannedMessage> updateCannedMessage(@PathVariable(value = "id") Long id,@RequestBody CannedMessage cannedMsg){
		CannedMessage updatedCannedMessage = cannedMsgRepo.findById(id).orElseThrow(()-> new RuntimeException("CannedMessage not found for this id :"+id));
		updatedCannedMessage.setTitle(cannedMsg.getTitle());
		updatedCannedMessage.setShortcut(cannedMsg.getShortcut());
		updatedCannedMessage.setDescription(cannedMsg.getDescription());
		updatedCannedMessage.setCompany(updatedCannedMessage.getCompany());
		updatedCannedMessage.setUser(updatedCannedMessage.getUser());
		cannedMsgRepo.save(updatedCannedMessage);
		return ResponseEntity.ok().body(updatedCannedMessage);	
	}
	
	@GetMapping("/api/v1/cannedMsg/{id}")
	private ResponseEntity<CannedMessage> getSinglecannedMsg(@PathVariable(value = "id") Long id){
		CannedMessage cannedMsg = cannedMsgRepo.findById(id).orElseThrow(()-> new RuntimeException("cannedMsg not found for this id :"+id));
		return ResponseEntity.ok().body(cannedMsg);
	}
	
	@DeleteMapping("/api/v1/cannedMsg/{id}")
	private ResponseEntity<CannedMessage> deletecannedMsg(@PathVariable(value = "id") Long id){
		CannedMessage cannedMsg = cannedMsgRepo.findById(id).orElseThrow(()-> new RuntimeException("cannedMsg not found for this id :"+id));
		cannedMsg.setDeleted(true);
		cannedMsgRepo.save(cannedMsg);
		return ResponseEntity.ok().body(cannedMsg);
	}
}
