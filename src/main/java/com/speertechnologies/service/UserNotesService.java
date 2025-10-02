package com.speertechnologies.service;

import com.speertechnologies.entity.Notes;
import com.speertechnologies.entity.User;
import com.speertechnologies.entity.UserNotes;
import com.speertechnologies.model.NotesRequest;
import com.speertechnologies.response.NotesResponse;

public interface UserNotesService {

	Notes addUserNote(long userId, NotesRequest noteRequest);
	
	UserNotes findByNotesAndSharedWithUser(Notes notes, User user);
	
	UserNotes shareNoteWithUser(NotesRequest noteRequest, long sharedWithUserId, long createdByUserId);
	
	public NotesResponse getNotesByIdAndUserId(long notesId, long userId);
	
	public NotesResponse getNotesByUserId(long userId);
	
	NotesResponse findNoteNamesByUserIdAndKeyword(long userId, String keyword);
	
	void updateNotesById(long notesId, long userId, NotesRequest notesRequest);
			
	void deleteNotesById(long noteId, long userId);
}
