package com.mahd.employee.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

import lombok.Data;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag extends Auditable<String> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID uniqueId = UUID.randomUUID();
	
	@Column
	private String tagName;
	
	@Column
	private String description;

	@Column
	private boolean deleted;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Company company;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User user;

}
