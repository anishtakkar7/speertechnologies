package com.speertechnologies.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notes implements Serializable {

	@Id
	@Column(name = "note_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notes_seq_generator")
	@SequenceGenerator(name = "notes_seq_generator", sequenceName = "notes_seq", allocationSize = 1)
	private long noteId;
	
	@Column(name = "note_name", nullable = false)
	private String noteName;
	
	@Column(name = "note_created_date", nullable = false)
	private Instant noteCreated;
	
	@Column(name = "note_updated_date")
	private Instant noteUpdated;

	@OneToMany(mappedBy = "notes", cascade = CascadeType.REMOVE)
	private Set<UserNotes> userNotes = new HashSet<>();
}
