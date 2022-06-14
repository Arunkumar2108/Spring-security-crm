package com.mahd.employee.models;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tickets extends Auditable<String> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private int ticketNo = new Random().nextInt(900000) + 100000;;
	
	@Column
	private String subject;
	
	@Column
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Priority priority;

	@Column
	private String status;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Company company; 
	
	@OneToOne(fetch = FetchType.EAGER)
	private User agent;

	@OneToOne(fetch = FetchType.EAGER)
	private Tag tag;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Contact contact;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	private Accounts accounts;
	
	@Column
	private String assignedBy;
	
	@Column
	private String assignedTo;
	
	@Column
	private String requesterName;
	
	@Column
	private String requesterEmail;
	
	@Enumerated(EnumType.STRING)
	private TicketType ticketType;
	
	
	
	
}
