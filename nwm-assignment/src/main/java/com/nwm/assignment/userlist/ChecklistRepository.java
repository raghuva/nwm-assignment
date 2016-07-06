package com.nwm.assignment.userlist;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ChecklistRepository extends CrudRepository<Checklist, Long> {

}
