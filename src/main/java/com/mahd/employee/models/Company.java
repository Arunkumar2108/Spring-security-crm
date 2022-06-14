package com.mahd.employee.models;


import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;


@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String companyName;
	
	@Column
	private String companyCode;
	
	@Column
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID uniqueId = UUID.randomUUID();

	@Column
	private String description;
	
	@Column
	private String avatar;
	
	@Column
	private String domains;

	@Column
	private String managedBy;
	
	@Column
	private String industry;
	
	@Column
	private String contactPersonName;
	
	@Column
	private String phoneNo;
	
	@Column
	private String address;
	
	@Column
	private String city;
	
	@Column
	private String state;

	@Column
	private String country;

	@Column
	private boolean deleted;
	
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "companies_user")
	private Collection<User> user = new ArrayList<>();
}
