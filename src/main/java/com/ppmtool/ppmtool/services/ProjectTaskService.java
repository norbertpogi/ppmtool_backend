package com.ppmtool.ppmtool.services;

import com.ppmtool.ppmtool.domain.ProjectTask;

public interface ProjectTaskService {

    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username);

    Iterable<ProjectTask> findBacklogById(String backlog_id, String username);

    ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username);

    ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask, String backlog_id, String pt_id, String username);

    void deletePTByProjectSequence(String backlog_id, String pt_id, String username);
}
