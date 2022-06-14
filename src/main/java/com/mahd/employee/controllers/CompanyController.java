package com.mahd.employee.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.mahd.employee.models.Company;
import com.mahd.employee.models.User;
import com.mahd.employee.repository.CompanyRepo;
import com.mahd.employee.repository.UserRepo;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
@Slf4j
public class CompanyController {

	@Autowired
	private CompanyRepo companyRepo;
	
	@Autowired
	private UserRepo userRepo;




	@PostMapping("/api/v1/company/create")
	private ResponseEntity<Company> addCompany(@RequestBody Company company){
		companyRepo.save(company);
		return ResponseEntity.ok().body(company);
	}
	
	@GetMapping("/api/v1/company/admin")
	private List<Company> getCompanies(){
		return companyRepo.findAllByDeleted(false);
	}

	@GetMapping("/api/v1/company")
	private Company getUserCompany(Authentication authentication){
		Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
		authorities = authentication.getAuthorities();
		String userId = authorities.iterator().next().toString();
		Long id = Long.parseLong(userId);
		User loggedUser = userRepo.findById(id).orElseThrow(()-> new RuntimeException("User Not found for this Id:"+id));
		return loggedUser.getCompany();
	}


	@PutMapping("/api/v1/company/{id}")
	private ResponseEntity<Company> updateCompany(@PathVariable(value = "id") Long id,@RequestBody Company company){
		Company updatedCompany = companyRepo.findById(id).orElseThrow(()-> new RuntimeException("Company not found for this id :"+id));
		updatedCompany.setCompanyName(company.getCompanyName());
		updatedCompany.setCompanyCode(company.getCompanyCode());
		updatedCompany.setDescription(company.getDescription());
		updatedCompany.setAvatar(company.getAvatar());
		updatedCompany.setDomains(company.getDomains());
		updatedCompany.setManagedBy(company.getManagedBy());
		updatedCompany.setIndustry(company.getIndustry());
		updatedCompany.setContactPersonName(company.getContactPersonName());
		updatedCompany.setPhoneNo(company.getPhoneNo());
		updatedCompany.setAddress(company.getAddress());
		updatedCompany.setCity(company.getCity());
		updatedCompany.setState(company.getState());
		updatedCompany.setCountry(company.getCountry());
		updatedCompany.setUniqueId(updatedCompany.getUniqueId());
		if(updatedCompany.getUser()!= null){
			updatedCompany.setUser(updatedCompany.getUser());
		}
		companyRepo.save(updatedCompany);
		return ResponseEntity.ok().body(updatedCompany);	
	}
	
	@GetMapping("/api/v1/company/{id}")
	private ResponseEntity<Company> getSingleCompany(@PathVariable(value = "id") Long id){
		Company company = companyRepo.findById(id).orElseThrow(()-> new RuntimeException("Company not found for this id :"+id));
		return ResponseEntity.ok().body(company);
	}
	
	@DeleteMapping("/api/v1/company/{id}")
	private ResponseEntity<Company> deleteCompany(@PathVariable(value = "id") Long id){
		Company company = companyRepo.findById(id).orElseThrow(()-> new RuntimeException("Company not found for this id :"+id));
		company.setDeleted(true);
		companyRepo.save(company);
		return ResponseEntity.ok().body(company);
	}
	
}
