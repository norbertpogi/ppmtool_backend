package com.ppmtool.ppmtool.services;

import com.ppmtool.ppmtool.domain.Project;

public interface ProjectService {

    Project saveUpdateProject(Project project, String username);
    Project findProjectByIdentifier(String projectId);
    Iterable<Project> findAllProject();
    void deleteByProjectId(String projectId);
}
