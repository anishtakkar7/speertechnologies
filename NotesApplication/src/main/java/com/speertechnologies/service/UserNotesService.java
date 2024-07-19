package com.speertechnologies.service;

import java.util.List;
import java.util.Optional;

import com.speertechnologies.entity.Notes;
import com.speertechnologies.entity.User;
import com.speertechnologies.entity.UserNotes;
import com.speertechnologies.model.NotesRequest;
import com.speertechnologies.response.NotesResponse;

public interface UserNotesService {

	void addUserNote(long userId, NotesRequest noteRequest);
	
	UserNotes findByNotesAndCreatedByUser(Notes notes, User user);
	
	void shareNoteWithUser(NotesRequest noteRequest, long sharedWithUserId, long createdByUserId);
	
	public NotesResponse getNotesByIdAndUserId(long notesId, long userId);
	
	public NotesResponse getNotesByUserId(long userId);
	
	NotesResponse findNoteNamesByUserIdAndKeyword(long userId, String keyword);
}
