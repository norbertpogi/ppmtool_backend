package com.ppmtool.ppmtool.services;

import com.ppmtool.ppmtool.domain.ProjectTask;

public interface ProjectTaskService {

    ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask);
}
