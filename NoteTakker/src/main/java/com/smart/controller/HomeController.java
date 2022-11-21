package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.PersonRepository;
import com.smart.entities.Person;
import com.smart.helper.Message;

@Controller
public class HomeController {
	@Autowired
	private  BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private PersonRepository personRepository;
	@RequestMapping("/")
	public String home(Model model) {
	model.addAttribute("title", "Home page");
	
		return "home";
	}
	
	@RequestMapping("/signup")
	public String sign(Model model) {
		model.addAttribute("title", "Signup page");
		model.addAttribute("person", new Person());
	return "signup";
	}
	@RequestMapping("/do_register")
	public String registerUser(@Valid @ModelAttribute("person") Person person,BindingResult result1, Model model,HttpSession session) {
		try {
			
			if(result1.hasErrors()) {
				System.out.println("Error" +result1.toString());
				model.addAttribute("person", person);
				return "signup";
			}
			
		
			person.setRole("ROLE_USER");
			person.setPassword(passwordEncoder.encode(person.getPassword()));
		System.out.println("USER" +person);
		
		Person result = this.personRepository.save(person);
		model.addAttribute("user", new Person());
		session.setAttribute("message",new Message("Successfully registered", "alert-success"));
		
		return "signup";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			session.setAttribute("message",new Message("Something went wrong" +e.getMessage(), "alert-danger"));
			return "signup";
		}
	}
	@GetMapping("/signin")
	public String customLogin(Model model) {
		
		model.addAttribute("title", "login page");
		return "login";
	}


}
