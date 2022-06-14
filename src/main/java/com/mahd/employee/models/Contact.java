package com.mahd.employee.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String contactName;
	
	@Column
	private String contactCode;
	
	@OneToOne
	@JoinTable(name = "account_contact")
	private Accounts accounts;
	
	
	@Column
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID uniqueId = UUID.randomUUID();

	@Column
	private String description;
	
	@Column
	private String avatar;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinTable(name = "user_contact")
	private User user;
		
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
	
	@OneToOne(fetch = FetchType.EAGER)
	private Company company;
	
}
