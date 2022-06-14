package com.mahd.employee.controllers;



import java.util.*;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mahd.employee.models.Company;
import com.mahd.employee.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.mahd.employee.models.Role;
import com.mahd.employee.models.User;
import com.mahd.employee.models.UserType;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@Slf4j
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private PipelineRepo pipelineRepo;

	@Autowired
	private TagRepo tagRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@PostMapping("/api/v1/users/create")
	private ResponseEntity<User> addUser(@RequestBody User user,Authentication authentication,@RequestParam String company,String role){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setDeleted(false);
		user.setAdmin(false);
		user.setActive(false);
		user.setVerified(true);
		Long roleId = Long.parseLong(role);
		Role roleName = roleRepo.findById(roleId).orElseThrow(()-> new RuntimeException("Role not found for this id:"+roleId));
		user.setRole(roleName);
		Long companyId = Long.parseLong(company);
		Company companyName = companyRepo.findById(companyId).orElseThrow(()-> new RuntimeException("Company Not found for this id"));
		user.setCompany(companyName);
		userRepo.save(user);
		return ResponseEntity.ok().body(user);
	}
	@PostMapping("/signup")
	private ResponseEntity<User> signUpUser(@RequestBody User user){

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Company company = new Company();
		company.setCompanyName(user.getCompany().getCompanyName());
		companyRepo.save(company);
		user.setCompany(company);
		user.setDeleted(false);
		user.setUserType(UserType.CUSTOMER);
		user.setActive(false);
		user.setAdmin(false);
		return ResponseEntity.ok().body(userRepo.save(user));
	}


	@GetMapping("/api/v1/users")
	private List<User> getUser(){
		return userRepo.findAllByDeleted(false);
	}
	
	@GetMapping("/api/v1/users/{id}")
	private ResponseEntity<User> getSingleUser(@PathVariable(value = "id") Long id){
		User fetchedUser = userRepo.findById(id).orElseThrow(()-> new RuntimeException("User Not found"));
		return ResponseEntity.ok().body(fetchedUser);
	}

	@PostMapping("/getUser")
	private ResponseEntity<User> getUserById(@RequestHeader(name = "Authorization") String token){
		Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedJWT = verifier.verify(token);
		String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
		Long id = Long.parseLong(roles[0]);
		User user  = userRepo.findById(id).orElseThrow(()-> new RuntimeException("User Not found for this is:"+id));
		return  ResponseEntity.ok().body(user);
	}
	
	@PutMapping("/api/v1/users/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user,@PathVariable(value = "id") Long id){
		User updUser = userRepo.findById(id).orElseThrow(()-> new RuntimeException("User Not found"));
		updUser.setFirstName(user.getFirstName());
		updUser.setLastName(user.getLastName());
		updUser.setMiddleName(user.getMiddleName());
		updUser.setSuffix(user.getSuffix());
		updUser.setEmail(user.getEmail());
		updUser.setMobileNo(user.getMobileNo());
		updUser.setUserName(user.getUserName());
		updUser.setPassword(passwordEncoder.encode(user.getPassword()));
		updUser.setRefrenceNo(updUser.getRefrenceNo());
		updUser.setAvatar(user.getAvatar());
		updUser.setAdmin(user.isAdmin());
		updUser.setDeleted(user.isDeleted());
		updUser.setVerified(user.isVerified());
		updUser.setActive(user.isActive());
		
		return ResponseEntity.ok().body(userRepo.save(updUser));
	}
	
	@DeleteMapping("/api/v1/users/{id}")
	private ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long id){
		User delUser = userRepo.findById(id).orElseThrow(()-> new RuntimeException("User Not found for this id "+id));
		delUser.setDeleted(true);
		userRepo.save(delUser);
		return ResponseEntity.ok().body(delUser); 
	}
	
	@PostMapping("/api/v1/users/roleToUser")
	private ResponseEntity<?> addRoleToUser(@RequestParam(value = "userId") Long uid,@RequestParam(value = "roleId") Long rid ){
		
		User user = userRepo.findById(uid).orElseThrow(()-> new RuntimeException("User not found for this id "+uid));
		Role role = roleRepo.findById(rid).orElseThrow(()-> new RuntimeException("Role not found for this id "+rid));
		user.setRole(role);
		userRepo.save(user);
		return ResponseEntity.ok().body(user);
	}

	@GetMapping("/api/v1/count")
	private Map<String,Long> getCounts(){
		Long userCount = userRepo.count();
		Long companyCount = companyRepo.count();
		Long tagCount = tagRepo.count();
		Long pipelineCount = pipelineRepo.count();
		Map<String ,Long> counts = new HashMap<>();
		counts.put("User_Count",userCount);
		counts.put("Company_Count",companyCount);
		counts.put("Tag_Count",tagCount);
		counts.put("Pipeline_Count",pipelineCount);
		log.info("Count:{}",counts);
		return  counts;
	}
	
	
	
	
}

