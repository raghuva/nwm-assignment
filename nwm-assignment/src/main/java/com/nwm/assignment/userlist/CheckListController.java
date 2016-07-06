package com.nwm.assignment.userlist;

import static com.nwm.common.ResourceUtils.entityOrNotFoundException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{id}/checklists")
public class CheckListController {
	@Autowired
	private ChecklistRepository checklistRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EntityLinks entityLinks;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Resources<ChecklistResource>> getAllLists(@PathVariable String id) {
		User user = entityOrNotFoundException(userRepository.findOne(Long.valueOf(id)));
		List<ChecklistResource> checklists =  user.getChecklists()
				.stream()
				.map(ChecklistResource::new)
				.collect(Collectors.toList());
		return ResponseEntity.ok(new Resources<ChecklistResource>(checklists));
	}

	@RequestMapping(value = "/{checklistid}", method = RequestMethod.GET)
	public ResponseEntity<ChecklistResource> getList(@PathVariable String checklistid) {
		return ResponseEntity.ok(new ChecklistResource(checklistRepository.findOne(Long.valueOf(checklistid))));
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Checklist checklist) {
		final Checklist newList = checklistRepository.save(checklist);
		final HttpHeaders headers = new HttpHeaders();
		final Link linkToNewChecklist = entityLinks.linkToSingleResource(ChecklistResource.class,newList.getId());
		headers.add("Location", linkToNewChecklist.getHref() );
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{checklistid}", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateName(@PathVariable String checklistid, @RequestBody String name)
			throws IOException {
		final Checklist existing = checklistRepository.findOne(Long.valueOf(checklistid));
		existing.setName(name);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@RequestBody Long checklistId) {
		final Checklist checklist = checklistRepository.findOne(checklistId);
		if(checklist!=null){
			checklistRepository.delete(checklistId);
		}
		//return 204
		return ResponseEntity.noContent().build();
	}

}
