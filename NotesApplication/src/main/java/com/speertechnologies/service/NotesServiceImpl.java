package com.speertechnologies.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.speertechnologies.entity.Notes;
import com.speertechnologies.entity.User;
import com.speertechnologies.exception.NotFoundException;
import com.speertechnologies.model.NotesRequest;
import com.speertechnologies.repository.NotesRepository;

@Service
@Transactional
public class NotesServiceImpl implements NotesService{

	@Autowired
	NotesRepository notesRepository;
	
	//@Autowired
	//UserNotesService userNotesService;
	
	@Autowired
	UserService userService;
	
	@Override
	public Notes addNotes(Notes notes) {
		notes.setNoteCreated(Instant.now());
		return notesRepository.save(notes);
	}

	@Override
	public Notes getNotesByIdAndUserId(long notesId, long userId) {
		
		User user = userService.getUserById(userId);
		Notes notes = notesRepository.findByNoteId(notesId).get();
		return null;//userNotesService.findByNotesAndCreatedByUser(notes, user).get().getNotes();
	}

	@Override
	public List<Notes> getNotesByUserId(long userId) {
		User user = userService.getUserById(userId);
		
		return null;
	}

	@Override
	public void deleteNotesById(long notesId) {
		notesRepository.deleteById(notesId);
	}

	@Override
	public void updateNotesById(long notesId, NotesRequest notesRequest) {
		
		Notes note = notesRepository.findByNoteId(notesId).orElseThrow(() -> new RuntimeException("Note not found exception")); //create custom exception class and handler class
		note.setNoteName(notesRequest.getNoteName());
		note.setNoteUpdated(Instant.now());
		notesRepository.save(note);
	}

	@Override
	public Notes findByNotesId(long noteId) {
		Notes note = notesRepository.findByNoteId(noteId).orElseThrow(() -> new NotFoundException("Note not found exception", "NOTE_NOT_FOUND")); //create custom exception class and handler class
		return note;
	}

}
