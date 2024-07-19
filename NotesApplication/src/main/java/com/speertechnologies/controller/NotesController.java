package com.speertechnologies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.speertechnologies.entity.Notes;
//import com.speertechnologies.entity.User;
import com.speertechnologies.model.NotesRequest;
import com.speertechnologies.response.NotesResponse;
import com.speertechnologies.service.NotesService;
import com.speertechnologies.service.UserNotesService;
import org.springframework.security.core.userdetails.User;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

	
	@Autowired
	private NotesService notesService;
	
	@Autowired
	private UserNotesService userNotesService;
	
	
	@GetMapping
	public ResponseEntity<NotesResponse> getAllNotesForAuthUser()
	{
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		NotesResponse notesResponse= userNotesService.getNotesByUserId(Long.valueOf(user.getUsername()));
		
		return new ResponseEntity<NotesResponse>(notesResponse, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NotesResponse> getNotesByIdForAuthUser(@PathVariable("id") Long id)
	{
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		NotesResponse notesResponse =  userNotesService.getNotesByIdAndUserId(id,Long.valueOf(user.getUsername()));
		return new ResponseEntity<NotesResponse>(notesResponse, HttpStatus.OK);
		
	}
	
	@PostMapping
	public ResponseEntity<String> saveNotesForAuthUser(@RequestBody NotesRequest noteRequest)
	{
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userNotesService.addUserNote(Long.valueOf(user.getUsername()), noteRequest);
		
		return new ResponseEntity<String>("Note is created successfully !!!", HttpStatus.CREATED);
		
	}
	
	@PutMapping("/notes/{id}")
	public ResponseEntity<String> updateNotesByIdForAuthUser(@PathVariable Long id, @RequestBody NotesRequest noteRequest)
	{
		notesService.updateNotesById(id, noteRequest);
		return new ResponseEntity<String>("Note is updated successfully !!!", HttpStatus.CREATED);
		
	}
	
	
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<String> deleteNotesByIdForAuthUser(@PathVariable Long id)
	{
		notesService.deleteNotesById(id);
		return new ResponseEntity<String>("Note is deleted successfully !!!", HttpStatus.CREATED);
		
	}
	
	
	@PostMapping("/notes/{id}/share")
	public ResponseEntity<String> shareNotesWithAnotherUser(@PathVariable("id") Long shareWithUserId, @RequestBody NotesRequest noteRequest)
	{	
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		long createByUserId = Long.valueOf(user.getUsername());
		userNotesService.shareNoteWithUser(noteRequest, shareWithUserId, createByUserId);
		return new ResponseEntity<String>("Note shared successfully !!!", HttpStatus.CREATED);		
	}
	
	
	@GetMapping("/search")
	public MappingJacksonValue searchNotesByKeyWordForAuthUser(@RequestParam String key)
	{
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		NotesResponse notesResponse =  userNotesService.findNoteNamesByUserIdAndKeyword(Long.valueOf(user.getUsername()) , key);
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(notesResponse);
		
		SimpleBeanPropertyFilter propertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("noteNames");
		
		FilterProvider filter = new SimpleFilterProvider().addFilter("NotesResponseFilter", propertyFilter);
		
		mappingJacksonValue.setFilters(filter);
		return mappingJacksonValue;
		//return new ResponseEntity<NotesResponse>(notesResponse, HttpStatus.OK);
	}
}
