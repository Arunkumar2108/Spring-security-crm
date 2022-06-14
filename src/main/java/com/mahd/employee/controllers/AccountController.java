package com.mahd.employee.controllers;

import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.mahd.employee.models.Accounts;
import com.mahd.employee.models.Company;
import com.mahd.employee.models.User;
import com.mahd.employee.repository.AccountRepo;
import com.mahd.employee.repository.CompanyRepo;
import com.mahd.employee.repository.UserRepo;

@RestController
@RequestMapping("/")
@Slf4j
@CrossOrigin(origins = "*")
public class AccountController {


	@Autowired
	private AccountRepo accountRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CompanyRepo companyRepo;

	@PostMapping("/api/v1/account/create")
	private ResponseEntity<Accounts> addAccount(@RequestBody Accounts accounts,Authentication authentication){
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		String userId = authorities.iterator().next().toString();
		Long id = Long.parseLong(userId);
		User loggedUser = userRepo.findById(id).orElseThrow(()-> new RuntimeException("User Not found for this Id:"+id));
		Company company = loggedUser.getCompany();
		accounts.setUser(loggedUser);
		accounts.setCompany(company);
		accountRepo.save(accounts);
		return ResponseEntity.ok().body(accounts);
	}


	@GetMapping("/api/v1/account")
	private List<Accounts> getAccounts(){
		return accountRepo.findAll();
	}

	@PutMapping("/api/v1/account/{id}")
	private ResponseEntity<Accounts> updateAccount(@PathVariable(value = "id") Long id,@RequestBody Accounts accounts){
		Accounts updatedAccounts = accountRepo.findById(id).orElseThrow(()-> new RuntimeException("Account not found for this id :"+id));
		updatedAccounts.setCustomerName(accounts.getCustomerName());
		updatedAccounts.setCustomerCode(accounts.getCustomerCode());
		updatedAccounts.setDescription(accounts.getDescription());
		updatedAccounts.setAvatar(accounts.getAvatar());
		updatedAccounts.setIndustry(accounts.getIndustry());
		updatedAccounts.setEmail(accounts.getEmail());
		updatedAccounts.setContactPersonName(accounts.getContactPersonName());
		updatedAccounts.setPhoneNo(accounts.getPhoneNo());
		updatedAccounts.setAddress(accounts.getAddress());
		updatedAccounts.setCity(accounts.getCity());
		updatedAccounts.setState(accounts.getState());
		updatedAccounts.setCountry(accounts.getCountry());
		updatedAccounts.setReferenceNo(updatedAccounts.getReferenceNo());
		updatedAccounts.setUser(updatedAccounts.getUser());
		updatedAccounts.setCompany(updatedAccounts.getCompany());
		return ResponseEntity.ok().body(updatedAccounts);
	}

	@GetMapping("/api/v1/account/{id}")
	private Accounts getSingleAccounts(@PathVariable(value = "id") Long id){
		Accounts accounts = accountRepo.findById(id).orElseThrow(()-> new RuntimeException("Accounts not found for this id :"+id));
		return accounts;
	}

	@DeleteMapping("/api/v1/account/{id}")
	private ResponseEntity<Accounts> deleteAccounts(@PathVariable(value = "id") Long id){
		Accounts accounts = accountRepo.findById(id).orElseThrow(()-> new RuntimeException("Accounts not found for this id :"+id));
		accounts.setDeleted(true);
		accountRepo.save(accounts);
		return ResponseEntity.ok().body(accounts);
	}
}
