package com.mahd.employee.models;

import java.util.UUID;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
public class User extends Auditable<String> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	@Column
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID refrenceNo = UUID.randomUUID();

	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String middleName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false)
	private String suffix;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String mobileNo;
	
	@Column(nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String avatar;
	
	@Column
	private boolean isAdmin;
	
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	@Column
	private boolean deleted;
	
	@Column
	private boolean isActive;
	
	@Column
	private boolean isVerified;
	

	@OneToOne(fetch = FetchType.EAGER)
	private Role role; 

	@OneToOne(fetch = FetchType.EAGER)
	private Company company;
	
}


