package com.smart.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Note;

public interface NoteRepository  extends JpaRepository<Note,Integer>{
	
	@Query("from Note as d where d.person.id =:personId")
	//current page
	//note per page
	public Page<Note> findNotesByUser(@Param("personId") int personId, Pageable pePageable);
	

}
