package com.speertechnologies.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.speertechnologies.entity.Notes;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {
	
	Optional<Notes> findByNoteId(long id);
}
