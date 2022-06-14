package com.mahd.employee.models;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Table;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accounts extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String customerName;
	
	@Column
	private String customerCode;
	
	@Column
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID referenceNo = UUID.randomUUID();
	
	@Column
	private String description;
	
	@Column
	private String avatar;
	
	@Column
	private String industry;

	@Column
	private String email;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	
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
	
	@OneToOne(fetch = FetchType.EAGER)
	private Company company;
	
}
