package com.speertechnologies.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.speertechnologies.entity.Notes;
import com.speertechnologies.entity.User;
import com.speertechnologies.entity.UserNotes;
import com.speertechnologies.model.UserNotesId;

@Repository
public interface UserNotesRepository extends JpaRepository<UserNotes, UserNotesId>{

	 Optional<UserNotes> findByNotesAndSharedWithUser(Notes notes, User User);
	 Optional<UserNotes> findByNotesAndCreatedByUser(Notes notes, User User);
	 
	 Optional<List<UserNotes>> findBySharedWithUser(User sharedWithUser);
	 
	 @Query("SELECT n.noteName FROM UserNotes un " +
	           "JOIN un.notes n " +
	           "JOIN un.sharedWithUser u " +
	           "WHERE u.userId = :userId AND n.noteName LIKE %:keyword%")
	 List<String> findNoteNamesByUserIdAndKeyword(@Param("userId") Long userId, @Param("keyword") String keyword);
	 
	 void deleteByNotesAndSharedWithUser(Notes notes, User user);
	 
	 Optional<UserNotes> findByUserNoteId(UserNotesId user);

}
