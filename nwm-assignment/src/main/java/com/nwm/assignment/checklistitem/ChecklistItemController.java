package com.nwm.assignment.checklistitem;

import static com.nwm.common.ResourceUtils.entityOrNotFoundException;

import java.util.ArrayList;
import java.util.List;

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

import com.nwm.assignment.userlist.Checklist;
import com.nwm.assignment.userlist.ChecklistRepository;


@RestController
@RequestMapping("/checklist/{id}")
public class ChecklistItemController {

	@Autowired
	private ChecklistRepository checklistRepository;

	@Autowired
	private ChecklistItemRepository itemRepository;

	@Autowired
	private EntityLinks entityLinks;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Resources<ChecklistItemResource>> getAllItems(@PathVariable String id) {
		final Checklist checklist = checklistRepository.findOne(Long.valueOf(id));
		List<ChecklistItem> items = checklist.getItems();
		List<ChecklistItemResource> resources = new ArrayList<ChecklistItemResource>();
		for(ChecklistItem item:items){
			resources.add(new ChecklistItemResource(id,item));
		}
		/*List<ChecklistItemResource> checklistItems =  checklist.getItems()
				.stream()
				.map(ChecklistItemResource::new)
				.collect(Collectors.toList());
		 */
		return ResponseEntity.ok(new Resources<ChecklistItemResource>(resources));
	}

	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.GET)
	public ResponseEntity<ChecklistItemResource> getItem(@PathVariable String id,@PathVariable String itemId) {
		final ChecklistItem item = entityOrNotFoundException(itemRepository.findOne(Long.valueOf(itemId)));
		return ResponseEntity.ok(new ChecklistItemResource(id,item));
	}

	@RequestMapping(value = "/item", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@PathVariable String id, @RequestBody ChecklistItem item) {
		final Checklist checklist = entityOrNotFoundException(checklistRepository.findOne(Long.valueOf(id)));
		final ChecklistItem newItem = new ChecklistItem(checklist,item.getDescription());
		itemRepository.save(item);
		final HttpHeaders headers = new HttpHeaders();
		final Link linkToNewItem = entityLinks.linkToSingleResource(ChecklistItemResource.class,newItem.getId());
		headers.add("Location", linkToNewItem.getHref() );
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/item/{itemId}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@PathVariable String id, @PathVariable String itemId, @RequestBody ChecklistItem item) {
		entityOrNotFoundException(checklistRepository.findOne(Long.valueOf(id)));
		final ChecklistItem existing = entityOrNotFoundException(itemRepository.findOne(Long.valueOf(id)));
		existing.setCompleted(item.isCompleted());
		existing.setDescription(item.getDescription());
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/item/{itemId}",method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id,@PathVariable Long itemId) {
		entityOrNotFoundException(checklistRepository.findOne(Long.valueOf(id)));
		final ChecklistItem item = itemRepository.findOne(itemId);
		if(item!=null){
			itemRepository.delete(itemId);
		}
		//return 204
		return ResponseEntity.noContent().build();
	}

}
