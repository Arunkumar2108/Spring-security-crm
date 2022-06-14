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
@AllArgsConstructor
@NoArgsConstructor
public class Team extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	@Type(type="org.hibernate.type.UUIDCharType")
	private UUID uniqueId = UUID.randomUUID();


	@Column
	private String teamName;
	
	@Column
	private String description;

	@Column
	private boolean deleted;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "team_users")
	private Collection<User> agents = new ArrayList<>();
	
	@OneToOne (fetch = FetchType.EAGER)
	private Company company;
	
	@Column
	private String displayName;
	
	@OneToOne(fetch = FetchType.EAGER)
	private User user;
	
	
}

