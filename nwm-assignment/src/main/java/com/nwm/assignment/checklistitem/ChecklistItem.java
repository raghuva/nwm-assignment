package com.nwm.assignment.checklistitem;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.nwm.assignment.userlist.Checklist;

/**
 * <p>Java class to represent Item entity</p>
 *
 */
@Entity
public class ChecklistItem   {
	/**
	 * 
	 */
	@Id
	@GeneratedValue
	private Long id; 
	@ManyToOne
	@JoinColumn(name = "checklist_id")
	private Checklist checklist;
	private String description;
	private boolean completed;
	public ChecklistItem() {
		// TODO Auto-generated constructor stub
	}
	public ChecklistItem(Checklist checklist,String description) {
		this.checklist = checklist;
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}


}
