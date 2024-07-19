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
	public void addUserNote(long userId, NotesRequest noteRequest) {
		Instant currTime = Instant.now();
		Notes notes = Notes.builder().noteName(noteRequest.getNoteName()).noteCreated(currTime).build();
		notesService.addNotes(notes);
		User user  = userService.getUserById(userId); //remove later
		UserNotesId userNotesId = UserNotesId.builder().noteCreatedByUser(user.getUserId()).noteSharedWithUser(user.getUserId()).noteId(notes.getNoteId()).build();
		UserNotes userNote = UserNotes.builder().createdByUser(user).sharedWithUser(user).notes(notes).userNoteId(userNotesId).createdDate(currTime).build();
		userNotesRepository.save(userNote);
	}

	@Override
	public UserNotes findByNotesAndCreatedByUser(Notes notes, User user) {
		
		return userNotesRepository.findByNotesAndCreatedByUser(notes, user).orElseThrow(() -> new UserServiceException("User not authorized to view this notes.", "USER_NOT_AUTHORIZED"));
	}

	@Override
	public void shareNoteWithUser(NotesRequest noteRequest, long sharedWithUserId, long createdByUserId) {
		Instant currentTime = Instant.now();
		Notes note = Notes.builder().noteCreated(currentTime).noteName(noteRequest.getNoteName()).build();
		User sharedWithuser = userService.getUserById(sharedWithUserId);
		User createdByUser = userService.getUserById(createdByUserId);
		notesService.addNotes(note);
		UserNotes userNotes = UserNotes.builder().createdByUser(createdByUser).sharedWithUser(sharedWithuser).notes(note)
				.createdDate(currentTime).build();
		userNotesRepository.save(userNotes);
	}
	
	public NotesResponse getNotesByIdAndUserId(long notesId, long userId) {
		
		User user = userService.getUserById(userId);
		Notes notes = notesService.findByNotesId(notesId);
		notes  = findByNotesAndCreatedByUser(notes, user).getNotes();
		return  NotesResponse.builder().noteName(notes.getNoteName()).build();
	}

	@Override
	public NotesResponse getNotesByUserId(long userId) {
		User user  = userService.getUserById(userId);
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
}
