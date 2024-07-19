package com.speertechnologies.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UserNotesId implements Serializable{

	private Long noteId;
	private Long noteCreatedByUser;
	private Long noteSharedWithUser;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserNotesId that = (UserNotesId) o;
		return Objects.equals(noteId, that.noteId) && Objects.equals(noteCreatedByUser, that.noteCreatedByUser) && Objects.equals(noteSharedWithUser, that.noteSharedWithUser) ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(noteId, noteCreatedByUser, noteSharedWithUser);
	}
}
