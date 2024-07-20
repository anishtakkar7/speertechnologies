package com.speertechnologies.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.speertechnologies.entity.Notes;
import com.speertechnologies.entity.User;
import com.speertechnologies.entity.UserNotes;
import com.speertechnologies.exception.NotFoundException;
import com.speertechnologies.exception.UserServiceException;
import com.speertechnologies.model.NotesRequest;
import com.speertechnologies.model.UserNotesId;
import com.speertechnologies.repository.UserNotesRepository;
import com.speertechnologies.response.NotesResponse;

@Service
@Transactional
public class UserNotesServiceImpl implements UserNotesService {

	@Autowired
	UserNotesRepository userNotesRepository;
	
	@Autowired
	NotesService notesService;
	
	@Autowired
	UserService userService;

	@Override
	public Notes addUserNote(long userId, NotesRequest noteRequest) {
		Instant currTime = Instant.now();
		Notes notes = Notes.builder().noteName(noteRequest.getNoteName()).noteCreated(currTime).build();
		notesService.addNotes(notes);
		User user  = User.builder().userId(userId).build(); //userService.getUserById(userId); //remove later
		UserNotesId userNotesId = UserNotesId.builder().noteCreatedByUser(user.getUserId()).noteSharedWithUser(user.getUserId()).noteId(notes.getNoteId()).build();
		UserNotes userNote = UserNotes.builder().createdByUser(user).sharedWithUser(user).notes(notes).userNoteId(userNotesId).createdDate(currTime).build();
		userNotesRepository.save(userNote);
		return notes;
	}

	@Override
	public UserNotes findByNotesAndSharedWithUser(Notes notes, User user) {
		
		return userNotesRepository.findByNotesAndSharedWithUser(notes, user).orElseThrow(() -> new UserServiceException("User not authorized for this notes.", "USER_NOT_AUTHORIZED"));
	}

	@Override
	public UserNotes shareNoteWithUser(NotesRequest noteRequest, long sharedWithUserId, long createdByUserId) {
		Instant currentTime = Instant.now();
		Notes note = Notes.builder().noteCreated(currentTime).noteName(noteRequest.getNoteName()).build();
		User sharedWithuser = userService.getUserById(sharedWithUserId);
		User createdByUser = userService.getUserById(createdByUserId);
		notesService.addNotes(note);
		UserNotesId userNotesId = UserNotesId.builder().noteCreatedByUser(createdByUserId).noteSharedWithUser(sharedWithUserId).noteId(note.getNoteId()).build();
		UserNotes userNotes = UserNotes.builder().createdByUser(createdByUser).sharedWithUser(sharedWithuser).notes(note)
				.createdDate(currentTime).userNoteId(userNotesId).build();
		return userNotesRepository.save(userNotes);
	}
	
	public NotesResponse getNotesByIdAndUserId(long notesId, long userId) {
		
		User user = User.builder().userId(userId).build();// userService.getUserById(userId);
		Notes notes =  Notes.builder().noteId(notesId).build(); //notesService.findByNotesId(notesId);
		notes  = findByNotesAndSharedWithUser(notes, user).getNotes();
		return  NotesResponse.builder().noteName(notes.getNoteName()).build();
	}

	@Override
	public NotesResponse getNotesByUserId(long userId) {
		User user  = User.builder().userId(userId).build(); //userService.getUserById(userId);
		List<UserNotes> userNotes = userNotesRepository.findBySharedWithUser(user).orElseThrow(() -> new NotFoundException("User Notes not found in database", "USER_NOTES_NOT_FOUND" ));
		NotesResponse notesResponse = new NotesResponse();
		List<String> noteNames = userNotes.stream().map(usernote -> usernote.getNotes().getNoteName()).collect(Collectors.toList());
		notesResponse.setNoteNames(noteNames);
		return notesResponse;
	}

	@Override
	public NotesResponse findNoteNamesByUserIdAndKeyword(long userId, String keyword) {
		return NotesResponse.builder().noteNames(userNotesRepository.findNoteNamesByUserIdAndKeyword(userId, keyword)).build();
	}

	@Override
	public void deleteNotesById(long noteId, long userId) {
		User user = User.builder().userId(userId).build();
		Notes notes = Notes.builder().noteId(noteId).build();
		notes  = findByNotesAndSharedWithUser(notes, user).getNotes();
		userNotesRepository.deleteByNotesAndSharedWithUser(notes, user);
	}

	@Override
	public void updateNotesById(long notesId, long userId, NotesRequest notesRequest) {
		User user = User.builder().userId(userId).build();
		Notes notes = Notes.builder().noteId(notesId).build();
		notes  = findByNotesAndSharedWithUser(notes, user).getNotes();
		notes.setNoteName(notesRequest.getNoteName());
		notes.setNoteUpdated(Instant.now());
		notesService.addNotes(notes);
	}
	
	
}
