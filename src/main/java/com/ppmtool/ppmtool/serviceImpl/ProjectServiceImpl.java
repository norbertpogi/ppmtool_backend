package com.ppmtool.ppmtool.serviceImpl;

import com.ppmtool.ppmtool.domain.Project;
import com.ppmtool.ppmtool.exceptions.ProjectIdException;
import com.ppmtool.ppmtool.repositories.ProjectRepository;
import com.ppmtool.ppmtool.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project saveUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception ex) {
            throw new ProjectIdException("Project Id "
                    + project.getProjectIdentifier().toUpperCase()
                    + " is already exists!");
        }
    }

    @Override
    public Project findProjectByIdentifier(String projectId) {
        Project project = getProjectByIdentfier(projectId);
        return projectRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase());
    }

    @Override
    public Iterable<Project> findAllProject() {
        return projectRepository.findAll();
    }

    @Override
    public void deleteByProjectId(String projectId) {
        Project project = getProjectByIdentfier(projectId);
        projectRepository.delete(project);
    }

    private Project getProjectByIdentfier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if(null == project) {
            throw new ProjectIdException("Project Id " + projectId + " is not exists!");
        }
        return project;
    }

}
