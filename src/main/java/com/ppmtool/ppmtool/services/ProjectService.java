package com.ppmtool.ppmtool.services;

import com.ppmtool.ppmtool.domain.Project;

public interface ProjectService {

    Project saveUpdateProject(Project project, String username);
    Project findProjectByIdentifier(String projectId, String username);
    Iterable<Project> findAllProject(String username);
    void deleteByProjectId(String projectId, String username);
}
