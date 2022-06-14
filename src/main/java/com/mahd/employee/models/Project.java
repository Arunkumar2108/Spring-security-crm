package com.mahd.employee.models;

import java.util.Date;
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
public class Project extends Auditable<String>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID uniqueId = UUID.randomUUID();

	@Column
	private String projectName;
	
	@Column
	private String projectCode;
	

	
	@Column
	private Date startDate;
	
	@Column
	private Date endDate;
	
	@Column
	private String description;

	@Column
	private boolean deleted;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Company company;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	
	@Column
	private boolean isActive;
	
}

