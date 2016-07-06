package com.nwm.assignment.checklistitem;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.ResourceSupport;

public class ChecklistItemResource extends ResourceSupport {
	private final ChecklistItem checklistItem;

	public ChecklistItemResource(String checklistId,ChecklistItem checklistItem) {
		this.checklistItem = checklistItem;
		this.add(linkTo(methodOn(ChecklistItemController.class).getItem(checklistId ,checklistItem.getId().toString()))
				.withSelfRel());
	}

	public ChecklistItem getChecklistItem() {
		return checklistItem;
	}

}
