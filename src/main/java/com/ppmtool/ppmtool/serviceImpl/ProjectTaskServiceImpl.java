package com.ppmtool.ppmtool.serviceImpl;

import com.ppmtool.ppmtool.domain.Backlog;
import com.ppmtool.ppmtool.domain.ProjectTask;
import com.ppmtool.ppmtool.exceptions.ProjectIdException;
import com.ppmtool.ppmtool.repositories.BacklogRepository;
import com.ppmtool.ppmtool.repositories.ProjectTaskRepository;
import com.ppmtool.ppmtool.services.ProjectTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        //PTs to be added to a specific project, project != null, BL exists
        Backlog backlog = this.getBacklogById(projectIdentifier);
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
        Iterable<ProjectTask> projectTask = this.getProjectTaskRecord(backlog_id);
        return projectTask;
    }

    @Override
    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id) {
            //make sure we are searching on the right backlog
            this.getBacklogById(backlog_id);
            ProjectTask prTask = getProjectTaskSequence(pt_id);
            return prTask;
    }

    @Override
    public ProjectTask updateByProjectSequence(ProjectTask updatedProjectTask, String backlog_id, String pt_id) {
            this.getBacklogById(backlog_id);
            ProjectTask projectTask = this.getProjectTaskSequence(pt_id);
            projectTask = updatedProjectTask;
        return projectTaskRepository.save(projectTask);
    }

    @Override
    public void deletePTByProjectSequence(String backlog_id, String pt_id) {
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);
        Backlog backlog = projectTask.getBacklog();
        List<ProjectTask> pts = backlog.getProjectTasks();
        pts.remove(projectTask);
        backlogRepository.save(backlog);

        projectTaskRepository.delete(projectTask);
    }

    private Iterable<ProjectTask> getProjectTaskRecord(String backlog_id) {
        List<ProjectTask> pr = projectTaskRepository.findByProjectIdentifierOrderByPriority(backlog_id);
        if(pr.size() == 0) {
            throw new ProjectIdException("Project Id " + backlog_id + " is not exists!");
        }
        return pr;
    }

    private Backlog getBacklogById(String projectIdentifier) {
        Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
        if (null == backlog) {
            throw new ProjectIdException("Project Id " + projectIdentifier + " is not exists!");
        }
        return backlog;
    }

    private ProjectTask getProjectTaskSequence(String pt_id) {
        ProjectTask ptSeq = projectTaskRepository.findByProjectSequence(pt_id);
        if(null == ptSeq) {
            throw new ProjectIdException("projectSequence " + pt_id + " is not exist");
        }
        return ptSeq;
    }
}
