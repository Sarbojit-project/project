package com.smart.dao;

import java.security.Principal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.Person;

public interface PersonRepository extends JpaRepository<Person,Integer>{
	@Query("select u from Person u where u.email = :email")
	public Person getUserByUserName(@Param("email") String email);

}
