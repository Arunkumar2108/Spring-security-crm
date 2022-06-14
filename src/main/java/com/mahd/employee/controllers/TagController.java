package com.mahd.employee.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.mahd.employee.models.Company;
import com.mahd.employee.models.Tag;
import com.mahd.employee.models.User;
import com.mahd.employee.repository.CompanyRepo;
import com.mahd.employee.repository.TagRepo;
import com.mahd.employee.repository.UserRepo;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class TagController {

	@Autowired
	private TagRepo tagRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CompanyRepo companyRepo;
	
	@PostMapping("/api/v1/tag/create")
	private ResponseEntity<Tag> addtag(@RequestBody Tag tag, Authentication authentication){
			String userId = authentication.getAuthorities().iterator().next().toString();
			Long id = Long.parseLong(userId);
			User user = userRepo.findById(id).orElseThrow(()->new RuntimeException("User Not Found for this id:"+id));
			Company company = user.getCompany();
			tag.setUser(user);
			tag.setCompany(company);
			tagRepo.save(tag);

		return ResponseEntity.ok().body(tag);
	}
	
	@GetMapping("/api/v1/tag")
	private List<Tag> getTags(){
		return tagRepo.findAllByDeleted(false);
	}

	@PutMapping("/api/v1/tag/{id}")
	private ResponseEntity<Tag> updateTag(@PathVariable(value = "id") Long id,@RequestBody Tag tag){
		Tag updatedTag = tagRepo.findById(id).orElseThrow(()-> new RuntimeException("Tag not found for this id :"+id));
		updatedTag.setTagName(tag.getTagName());
		updatedTag.setDescription(tag.getDescription());
		if(tag.getCompany()== null && tag.getUser()== null) {
			tagRepo.save(updatedTag);
		}
		else {
			updatedTag.setUser(updatedTag.getUser());
			updatedTag.setCompany(updatedTag.getCompany());
			tagRepo.save(updatedTag);
		}
		
		return ResponseEntity.ok().body(updatedTag);	
	}
	
	@GetMapping("/api/v1/tag/{id}")
	private ResponseEntity<Tag> getSingletag(@PathVariable(value = "id") Long id){
		Tag tag = tagRepo.findById(id).orElseThrow(()-> new RuntimeException("tag not found for this id :"+id));
		return ResponseEntity.ok().body(tag);
	}
	
	@DeleteMapping("/api/v1/tag/{id}")
	private ResponseEntity<Tag> deletetag(@PathVariable(value = "id") Long id){
		Tag tag = tagRepo.findById(id).orElseThrow(()-> new RuntimeException("tag not found for this id :"+id));
		tag.setDeleted(true);
		tagRepo.save(tag);
		return ResponseEntity.ok().body(tag);
	}
}
