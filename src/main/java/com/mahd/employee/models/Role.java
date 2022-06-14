package com.mahd.employee.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;

	@Column
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID uniqueId = UUID.randomUUID();

	@Column
	private String roleName;
	
	@Column
	private String description;
	
	@Column
	private boolean deleted;
	
	@Column
	private boolean isActive;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "roles_Scopes",
			joinColumns = {@JoinColumn(name = "role_id")},
			inverseJoinColumns = {@JoinColumn(name = "scope_id")})
	private Collection<Scope> scopes = new ArrayList<>(); 
	
}
