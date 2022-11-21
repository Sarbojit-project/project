package com.smart.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="NOTE")
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int nId;
	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@ManyToOne
	private Person person;

	public Note() {
		// TODO Auto-generated constructor stub
	}

	

	public int getnId() {
		return nId;
	}



	public void setnId(int nId) {
		this.nId = nId;
	}



	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}



	@Override
	public String toString() {
		return "Note [nId=" + nId + ", title=" + title + ", content=" + content + ", person=" + person + "]";
	}

	
	


}
