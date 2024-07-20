package com.speertechnologies.service;

import java.util.List;

import com.speertechnologies.entity.Notes;
import com.speertechnologies.model.NotesRequest;

public interface NotesService {

	 Notes addNotes(Notes notes);
	 Notes getNotesByIdAndUserId(long noteId, long userId); 
	 List<Notes> getNotesByUserId(long userId);
	 Notes findByNotesId(long noteId);
	 void updateNotesById(long noteId, long userId, NotesRequest notesRequest);
	 void deleteNotesById(long noteId);
}
