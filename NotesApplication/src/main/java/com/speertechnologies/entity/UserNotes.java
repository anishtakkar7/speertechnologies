package com.speertechnologies.entity;

import java.io.Serializable;
import java.time.Instant;

import com.speertechnologies.model.UserNotesId;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserNotes implements Serializable {
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	//@Column(name = "usernote_id")
	//private long userNoteId;
	@EmbeddedId
	private UserNotesId userNoteId;
	
	
	@ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE)
	@MapsId("noteId")
    @JoinColumn(name = "note_id", nullable = false)
	private Notes notes;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("noteCreatedByUser")
    @JoinColumn(name = "usernote_created_by_user_id", nullable = false)
	private User createdByUser;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("noteSharedWithUser")
    @JoinColumn(name = "usernote_shared_with_user_id", nullable = false)
    private User sharedWithUser;
	
	@Column(name = "usernote_created_date")
	private Instant createdDate;
	
}
