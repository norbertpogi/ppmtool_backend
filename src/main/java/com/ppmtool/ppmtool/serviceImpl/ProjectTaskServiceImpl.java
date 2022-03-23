package com.ppmtool.ppmtool.serviceImpl;

import com.ppmtool.ppmtool.domain.Backlog;
import com.ppmtool.ppmtool.domain.ProjectTask;
import com.ppmtool.ppmtool.repositories.BacklogRepository;
import com.ppmtool.ppmtool.repositories.ProjectTaskRepository;
import com.ppmtool.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectTaskServiceImpl implements ProjectTaskService {

    private BacklogRepository backlogRepository;
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    public ProjectTaskServiceImpl(BacklogRepository backlogRepository, ProjectTaskRepository projectTaskRepository) {
        this.backlogRepository = backlogRepository;
        this.projectTaskRepository = projectTaskRepository;
    }

    @Override
    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
        //Exceptions: Project not found

        //PTs to be added to a specific project, project != null, BL exists
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        //set the bl to pt
        projectTask.setBacklog(backlog);
        //we want our project sequence to be like this: IDPRO-1  IDPRO-2  ...100 101
        Integer backlogSequence = backlog.getPTSequence();
        // Update the BL SEQUENCE
        backlogSequence++;
        backlog.setPTSequence(backlogSequence);
        //Add Sequence to Project Task
        projectTask.setProjectSequence(projectIdentifier+"-"+backlogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);
        //INITIAL priority when priority null
        if(null == projectTask.getPriority()||projectTask.getPriority() == 0){
            projectTask.setPriority(3);
        }
        //INITIAL status when status is null
        if(projectTask.getStatus()==""|| projectTask.getStatus()==null){
            projectTask.setStatus("TO_DO");
        }
        return projectTaskRepository.save(projectTask);
    }

    @Override
    public Iterable<ProjectTask> findBacklogById(String backlog_id) {
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
    }
}
