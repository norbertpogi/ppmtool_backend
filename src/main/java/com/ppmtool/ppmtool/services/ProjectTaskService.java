package com.ppmtool.ppmtool.services;

import com.ppmtool.ppmtool.domain.ProjectTask;

public interface ProjectTaskService {

    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask);

    Iterable<ProjectTask> findBacklogById(String backlog_id);

    ProjectTask findPTByProjectSequence(String backlog_id, String pt_id);

    ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask, String backlog_id, String pt_id);
}
