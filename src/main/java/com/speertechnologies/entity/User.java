package com.speertechnologies.entity;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users_tbl")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
	@SequenceGenerator(name = "user_seq_generator", sequenceName = "user_seq", allocationSize = 1)
	@Column(name = "user_id")
	private long userId;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "user_password", nullable = false)
	private String password;

	@Column(name = "user_created_date", nullable = false)
	private Instant userCreated;

	@Column(name = "user_updated_date")
	private Instant userUpdated;

	@Column(name = "last_login_date")
	private Instant lastLogin;

	@OneToMany(mappedBy = "createdByUser")
	private Set<UserNotes> createdNotes = new HashSet<>();

	@OneToMany(mappedBy = "sharedWithUser")
	private Set<UserNotes> sharedNotes = new HashSet<>();
	
	@Transient
	private String token;

}
