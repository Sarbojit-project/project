package com.smart.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.smart.dao.NoteRepository;
import com.smart.dao.PersonRepository;
import com.smart.entities.Note;
import com.smart.entities.Person;
import com.smart.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private NoteRepository noteRepository;
	
	@ModelAttribute
	public void addCommonData(Model model,Principal principal) {
		String userName = principal.getName();
		System.out.println("USERNAME"+userName);
		//get the user using username
		Person person = this.personRepository.getUserByUserName(userName);
		System.out.println("USER" +person);
		model.addAttribute("person", person);
	}
	
	
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal) {
		
		return "normal/user_dashboard";
	}
	
	@GetMapping("/addNotes")
	public String home(Model model,Principal principal) {
		model.addAttribute("title","Add contact");
		return "normal/add_note";
	}
	//processing add note from
	@PostMapping("/saveNotes")
	public String processNote(@ModelAttribute Note note,Principal principal,HttpSession session) {
		try {
		String name= principal.getName();
		Person person =  this.personRepository.getUserByUserName(name);
		note.setPerson(person);
		person.getNotes().add(note);
		this.personRepository.save(person);
		System.out.println("added to database");
		//message 
		session.setAttribute("message", new Message("Your content added","success"));
		System.out.println("data" +note);
		}
		catch(Exception e) {
			e.printStackTrace();
			session.setAttribute("message", new Message("Something wrong","danger"));
		}
		return "normal/add_note";
		
	}
	@GetMapping("/showNotes/{page}")
	public String showNote(@PathVariable("page") Integer page, Model m,Principal principal) {
		m.addAttribute("title", "Show user notes");
		String username = principal.getName();
		Person person =this.personRepository.getUserByUserName(username);
		
		//current page and contact per page
		Pageable pageable= PageRequest.of(page, 2);
		Page<Note> notes =  this.noteRepository.findNotesByUser(person.getId(),pageable);
		m.addAttribute("notes", notes);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", notes.getTotalPages());
		return "normal/show_notes";
	}
	@GetMapping("/delete/{nid}")
	public String deleteNote(@PathVariable("nid") Integer nId,Model model,HttpSession session) {
		System.out.println("nid" +nId);
		Note note = this.noteRepository.findById(nId).get();
	//check..
		System.out.println("note" +note.getnId());
		this.noteRepository.delete(note);
		System.out.println("deleted successfully");
		session.setAttribute("message", new Message("successfully deleted","success"));
		return "redirect:/user/showNotes/0";
		
	}
	@PostMapping("/update-contact/{nid}")
	public String updateForm(@PathVariable("nid") Integer nid, Model m,HttpSession session) {
		m.addAttribute("title", "Update contact");
		Note note =  this.noteRepository.findById(nid).get();
		m.addAttribute("note", note);
		return "normal/update_form";
	}
	@RequestMapping(value = "/process-update",method= RequestMethod.POST)
	public String updateHandler(@ModelAttribute Note note,HttpSession session,Principal principal) {
		try {
			Person person = this.personRepository.getUserByUserName(principal.getName());
			note.setPerson(person);
			this.noteRepository.save(note);
		} catch (Exception e) {
		}
		System.out.println("note" +note.getTitle());
		System.out.println("note" +note.getContent());
		return "redirect:/user/showNotes/0";
	}
	

}
