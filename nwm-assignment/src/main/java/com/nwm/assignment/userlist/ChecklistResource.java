package com.nwm.assignment.userlist;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

import com.nwm.assignment.checklistitem.ChecklistItemController;

public class ChecklistResource extends ResourceSupport {
	private final Checklist checklist;

	public ChecklistResource(Checklist checklist) {
		this.checklist = checklist;
		this.add(linkTo(methodOn(ChecklistItemController.class).getAllItems(checklist.getId().toString())).withRel("items"));
	}

	public Checklist getChecklist() {
		return checklist;
	}


}
