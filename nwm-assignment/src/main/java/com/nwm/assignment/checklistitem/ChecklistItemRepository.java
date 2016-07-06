package com.nwm.assignment.checklistitem;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistItemRepository extends CrudRepository<ChecklistItem, Long> {

}
