package com.mahd.employee.controllers;

import java.util.Collection;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.mahd.employee.models.Company;
import com.mahd.employee.models.Project;
import com.mahd.employee.models.User;
import com.mahd.employee.repository.CompanyRepo;
import com.mahd.employee.repository.ProjectRepo;
import com.mahd.employee.repository.UserRepo;



@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class ProjectController {

	@Autowired
	private ProjectRepo projectRepo;
	
	@Autowired
	private CompanyRepo companyRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@PostMapping("/api/v1/project/create")
	private ResponseEntity<Project> addProject(@RequestBody Project project, Authentication authentication, @RequestParam String sampleDate1, String sampleDate2) throws ParseException{
		Date startDate = new SimpleDateFormat("dd/mm/yyyy").parse(sampleDate1);
		project.setStartDate(startDate);
		Date endDate = new SimpleDateFormat("dd/mm/yyyy").parse(sampleDate2);
		project.setEndDate(endDate);
		String userId = authentication.getAuthorities().iterator().next().toString();
		Long id = Long.parseLong(userId);
		User user = userRepo.findById(id).orElseThrow(()-> new RuntimeException("User not found for this id:"+id));
		Company company = user.getCompany();
		project.setUser(user);
		project.setCompany(company);
		projectRepo.save(project);
		return ResponseEntity.ok().body(project);
	}
	
	@GetMapping("/api/v1/project")
	private List<Project> getProjects(){
		return projectRepo.findAll();
	}
	
	@GetMapping("/api/v1/project/{id}")
	private ResponseEntity<Project> getSingleProject(@PathVariable(value = "id") Long id){
		Project fetchedProject = projectRepo.findById(id).orElseThrow(()->new RuntimeException("Project Not Found for the id "+id));
		return ResponseEntity.ok().body(fetchedProject);
	}
	
	@PutMapping("/api/v1/project/{id}")
	public ResponseEntity<Project> updateProject(@RequestBody Project project,@PathVariable(value = "id") Long id){
		Project updProject = projectRepo.findById(id).orElseThrow(()->new RuntimeException("Project Not Found for the id "+id));
		updProject.setProjectName(project.getProjectName());
		updProject.setProjectCode(project.getProjectCode());
		updProject.setStartDate(updProject.getStartDate());
		updProject.setEndDate(updProject.getEndDate());
		updProject.setActive(project.isActive());
		updProject.setDescription(project.getDescription());
		if(project.getCompany()== null && project.getUser()== null) {
			projectRepo.save(updProject);
			
		}
		else {
			updProject.setCompany(updProject.getCompany());
			updProject.setUser(updProject.getUser());
			projectRepo.save(updProject);
		}
		
		return ResponseEntity.ok().body(updProject);
	}
	
	@DeleteMapping("/api/v1/project/{id}")
	private ResponseEntity<Project> deleteProject(@PathVariable(value = "id") Long id){
		Project project = projectRepo.findById(id).orElseThrow(()-> new RuntimeException("Project Not found for this id "+id));
		project.setDeleted(true);
		projectRepo.save(project);
		return ResponseEntity.ok().body(project);
	}
	
}
