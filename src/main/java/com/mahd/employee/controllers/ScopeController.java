package com.mahd.employee.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mahd.employee.models.Scope;
import com.mahd.employee.repository.ScopeRepo;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*")
public class ScopeController {
	
	@Autowired
	private ScopeRepo scopeRepo;

	
	@PostMapping("/api/v1/scopes/create")
	private ResponseEntity<Scope> addScope(@RequestBody Scope scope){
		
		return ResponseEntity.ok().body(scopeRepo.save(scope));
	}
	
	@GetMapping("/api/v1/scopes")
	private List<Scope> getScopes(){
		return scopeRepo.findAll();
	}
	
	@GetMapping("/api/v1/scopes/{id}")
	private ResponseEntity<Scope> getSingleScope(@PathVariable(value = "id") Long id){
		Scope fetchedScope = scopeRepo.findById(id).orElseThrow(()->new RuntimeException("Scope Not Found for the id "+id));
		return ResponseEntity.ok().body(fetchedScope);
	}
	
	@PutMapping("/api/v1/scopes/{id}")
	private ResponseEntity<Scope> updateScope(@RequestBody Scope scope,@PathVariable(value = "id") Long id){
		Scope updScope = scopeRepo.findById(id).orElseThrow(()->new RuntimeException("Scope Not Found for the id "+id));
		updScope.setScopeName(scope.getScopeName());
		updScope.setModelName(scope.getModelName());
		return ResponseEntity.ok().body(scopeRepo.save(updScope));
	}
	
	@DeleteMapping("/api/v1/scopes/{id}")
	private ResponseEntity<Scope> deleteScope(@PathVariable(value = "id") Long id){
		Scope delScope = scopeRepo.findById(id).orElseThrow(()->new RuntimeException("Scope Not Found for the id "+id));
		delScope.setDeleted(true);
		scopeRepo.save(delScope);
		return ResponseEntity.ok().body(delScope);
	}
}
