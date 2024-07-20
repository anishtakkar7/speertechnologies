package com.speertechnologies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.speertechnologies.constants.NotesConstants;
import com.speertechnologies.entity.Notes;
import com.speertechnologies.entity.UserNotes;
//import com.speertechnologies.entity.User;
import com.speertechnologies.model.NotesRequest;
import com.speertechnologies.response.NotesResponse;
import com.speertechnologies.service.UserNotesService;
import com.speertechnologies.util.NotesUtil;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

	
	
	@Autowired
	private UserNotesService userNotesService;
	
	
	@GetMapping
	public MappingJacksonValue getAllNotesForAuthUser()
	{
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		NotesResponse notesResponse= userNotesService.getNotesByUserId(Long.valueOf(user.getUsername()));
		return NotesUtil.filterateFields(NotesConstants.NOTE_RESPONSE_FILTER, notesResponse, NotesConstants.NOTE_NAMES_FIELD_STR );
	}
	
	@GetMapping("/{id}")
	public MappingJacksonValue getNotesByIdForAuthUser(@PathVariable("id") Long id)
	{
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		NotesResponse notesResponse =  userNotesService.getNotesByIdAndUserId(id,Long.valueOf(user.getUsername()));
		return NotesUtil.filterateFields(NotesConstants.NOTE_RESPONSE_FILTER, notesResponse, NotesConstants.NOTE_NAME_FIELD_STR );
		
	}
	
	@PostMapping
	public ResponseEntity<String> saveNotesForAuthUser(@RequestBody NotesRequest noteRequest)
	{
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Notes notes = userNotesService.addUserNote(Long.valueOf(user.getUsername()), noteRequest);
		return new ResponseEntity<String>("Note is created successfully with note id - "+notes.getNoteId()+" !!!", HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateNotesByIdForAuthUser(@PathVariable Long id, @RequestBody NotesRequest noteRequest)
	{
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userNotesService.updateNotesById(id, Long.valueOf(user.getUsername()), noteRequest);
		return new ResponseEntity<String>("Note is updated successfully !!!", HttpStatus.CREATED);
		
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteNotesByIdForAuthUser(@PathVariable Long id)
	{
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userNotesService.deleteNotesById(id, Long.valueOf(user.getUsername()));
		return new ResponseEntity<String>("Note is deleted successfully !!!", HttpStatus.CREATED);
		
	}
	
	
	@PostMapping("/{id}/share")
	public ResponseEntity<String> shareNotesWithAnotherUser(@PathVariable("id") Long shareWithUserId, @RequestBody NotesRequest noteRequest)
	{	
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long createByUserId = Long.valueOf(user.getUsername());
		UserNotes userNotes = userNotesService.shareNoteWithUser(noteRequest, shareWithUserId, createByUserId);
		return new ResponseEntity<String>("Note with note id - "+ userNotes.getUserNoteId().getNoteId() +" shared successfully with user !!!", HttpStatus.CREATED);		
	}
	
	
	@GetMapping("/search")
	public MappingJacksonValue searchNotesByKeyWordForAuthUser(@RequestParam String key)
	{
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		NotesResponse notesResponse =  userNotesService.findNoteNamesByUserIdAndKeyword(Long.valueOf(user.getUsername()) , key);
		return NotesUtil.filterateFields(NotesConstants.NOTE_RESPONSE_FILTER, notesResponse, NotesConstants.NOTE_NAMES_FIELD_STR );
	}
	
	
}
