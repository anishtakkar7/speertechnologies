package com.speertechnologies.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.speertechnologies.entity.Notes;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonFilter("NotesResponseFilter")
public class NotesResponse {
	List<String> noteNames;
	
	String noteName;
}
