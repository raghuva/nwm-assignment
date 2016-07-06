package com.nwm.assignment.userlist;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.nwm.assignment.checklistitem.ChecklistItem;

/**
 * <p>Java class to represent Checklist entity</p>
 * Checklist aggregate contains list of items entity
 *
 */
@Entity
public class Checklist  {





	@Id
	@GeneratedValue
	private Long id; 

	@ManyToOne(cascade = {})
	@JoinColumn(name="user_id")
	private User user;

	private String name;

	@OneToMany(mappedBy="checklist")
	private List<ChecklistItem> items =  new ArrayList<ChecklistItem>();

	public Checklist() {
	}

	public Checklist(User user, String name) {
		this.user = user;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ChecklistItem> getItems() {
		return items;
	}

	public void setItems(List<ChecklistItem> items) {
		this.items = items;
	}

}
