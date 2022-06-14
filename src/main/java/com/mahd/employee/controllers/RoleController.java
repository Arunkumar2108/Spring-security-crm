package com.mahd.employee.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mahd.employee.models.Role;
import com.mahd.employee.models.Scope;
import com.mahd.employee.repository.RoleRepo;
import com.mahd.employee.repository.ScopeRepo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class RoleController {

	@Autowired
	private RoleRepo roleRepo;
	
	@Autowired
	private ScopeRepo scopeRepo;

	@PostMapping("/api/v1/roles/create")
	private ResponseEntity<Role> addRole(@RequestBody Role role){
		
		return ResponseEntity.ok().body(roleRepo.save(role));
	}
	
	@GetMapping("/api/v1/roles")
	private List<Role> getRoles(){
		return roleRepo.findAll();
	}
	
	@GetMapping("/api/v1/roles/{id}")
	private ResponseEntity<Role> getSingleRole(@PathVariable(value = "id") Long id){
		Role fetchedRole = roleRepo.findById(id).orElseThrow(()->new RuntimeException("Role Not Found for the id "+id));
		return ResponseEntity.ok().body(fetchedRole);
	}
	
	@PutMapping("/api/v1/roles/{id}")
	public ResponseEntity<Role> updateRole(@RequestBody Role role,@PathVariable(value = "id") Long id){
		Role updRole = roleRepo.findById(id).orElseThrow(()->new RuntimeException("Role Not Found for the id "+id));
		updRole.setRoleName(role.getRoleName());
		updRole.setDescription(role.getDescription());
		updRole.setDeleted(role.isDeleted());
		updRole.setActive(role.isActive());
		return ResponseEntity.ok().body(roleRepo.save(updRole));
	}
	
	@DeleteMapping("/api/v1/roles/{id}")
	private ResponseEntity<Role> deleteRole(@PathVariable(value = "id") Long id){
		Role delRole = roleRepo.findById(id).orElseThrow(()-> new RuntimeException("Role Not found for this id "+id));
		delRole.setDeleted(true);
		roleRepo.save(delRole);
		return ResponseEntity.ok().body(delRole); 
	}
	
	@PostMapping("/api/v1/roles/scopeToRole")
	private ResponseEntity<?> addScopeToRole(@RequestParam(value = "roleId") Long roleId,@RequestParam(value = "scopeId") Long scopeId  ){
		
		
		Role role = roleRepo.findById(roleId).orElseThrow(()-> new RuntimeException("Role Not Found"));
		Scope scope = scopeRepo.findById(scopeId).orElseThrow(()-> new RuntimeException("Scope Not Found"));
		role.getScopes().add(scope);
		roleRepo.save(role);
		return ResponseEntity.ok().build();
	}
	
}


