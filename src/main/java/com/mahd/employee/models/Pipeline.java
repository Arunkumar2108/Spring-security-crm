package com.mahd.employee.models;

import java.util.Date;
import java.util.UUID;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;


@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pipeline extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID uniqueId = UUID.randomUUID();
	
	@Column
	private String name;

	
	@Column
	private String primaryContact;
	
	@Column
	private Date closeDate;
	
	@Column
	private String company;
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Enumerated(EnumType.STRING)
	private Stage stage;

	
	@Enumerated(EnumType.STRING)
	private Source source;
	
	@Column
	private String value;
	
	@Column
	private Priority priority;
	
	@Column
	private String winPercentage;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Tag tag;
	
	@Column
	private String description;

	@Column
	private boolean deleted;
}


