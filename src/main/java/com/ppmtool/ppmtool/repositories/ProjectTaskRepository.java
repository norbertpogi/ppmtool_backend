package com.ppmtool.ppmtool.repositories;

import com.ppmtool.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository  extends CrudRepository<ProjectTask, Long> {
}
