package com.mahd.employee.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.mahd.employee.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.mahd.employee.models.Company;
import com.mahd.employee.models.Team;
import com.mahd.employee.models.User;
import com.mahd.employee.repository.CompanyRepo;
import com.mahd.employee.repository.TeamRepo;
import com.mahd.employee.repository.UserRepo;

@RestController
@RequestMapping("/")
@Slf4j
@CrossOrigin(origins = "*")
public class TeamController {

	@Autowired
	private UserService userService;

	@Autowired
	private TeamRepo teamRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CompanyRepo companyRepo;


	@PostMapping("/api/v1/team/create")
	private ResponseEntity<Team> addteam(@RequestBody Team team, Authentication authentication){

		team.setDeleted(false);
		User user = userService.getUserById(authentication);
		log.info("User:{}",user);
		Company company = user.getCompany();
		team.setUser(user);
		team.setCompany(company);
		company.getUser().forEach(user1->{
			team.getAgents().add(user1);
		});
		teamRepo.save(team);
		return ResponseEntity.ok().body(team);
	}
	
	@GetMapping("/api/v1/team")
	private List<Team> getTeams(){
		return teamRepo.findAll();
	}

	@PutMapping("/api/v1/team/{id}")
	private ResponseEntity<Team> updateTeam(@PathVariable(value = "id") Long id,@RequestBody Team team){
		Team updatedTeam = teamRepo.findById(id).orElseThrow(()-> new RuntimeException("Team not found for this id :"+id));
		updatedTeam.setTeamName(team.getTeamName());
		updatedTeam.setDisplayName(team.getDisplayName());
		updatedTeam.setDescription(team.getDescription());
		if(team.getUser() == null && team.getCompany() == null) {
			teamRepo.save(updatedTeam);	
		}
		else {
			updatedTeam.setUser(updatedTeam.getUser());
			updatedTeam.setCompany(updatedTeam.getCompany());
			teamRepo.save(updatedTeam);
		}
		return ResponseEntity.ok().body(updatedTeam);	
	}
	
	@GetMapping("/api/v1/team/{id}")
	private ResponseEntity<Team> getSingleteam(@PathVariable(value = "id") Long id){
		Team team = teamRepo.findById(id).orElseThrow(()-> new RuntimeException("team not found for this id :"+id));
		return ResponseEntity.ok().body(team);
	}
	
	@DeleteMapping("/api/v1/team/{id}")
	private ResponseEntity<Team> deleteteam(@PathVariable(value = "id") Long id){
		Team team = teamRepo.findById(id).orElseThrow(()-> new RuntimeException("team not found for this id :"+id));
		team.setDeleted(true);
		teamRepo.save(team);
		return ResponseEntity.ok().body(team);
	}
	
}
