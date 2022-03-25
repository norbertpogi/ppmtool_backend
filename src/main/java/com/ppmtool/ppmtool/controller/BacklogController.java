package com.ppmtool.ppmtool.controller;

import com.ppmtool.ppmtool.domain.ProjectTask;
import com.ppmtool.ppmtool.services.ProjectTaskService;
import com.ppmtool.ppmtool.utilService.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/backlog")
public class BacklogController {

    private ProjectTaskService projectTaskService;
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    public BacklogController(ProjectTaskService projectTaskService, MapValidationErrorService mapValidationErrorService) {
        this.projectTaskService = projectTaskService;
        this.mapValidationErrorService = mapValidationErrorService;
    }

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask,
                                            BindingResult result, @PathVariable String backlog_id){
        ResponseEntity<?> erroMap = mapValidationErrorService.mapValidationService(result);
        if (erroMap != null) return erroMap;
        ProjectTask prTask = projectTaskService.addProjectTask(backlog_id, projectTask);

        return new ResponseEntity<ProjectTask>(prTask, HttpStatus.CREATED);
    }
    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id){
        return projectTaskService.findBacklogById(backlog_id);
    }
    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id){
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, pt_id);
        return new ResponseEntity<ProjectTask>( projectTask, HttpStatus.OK);
    }
    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@Valid @RequestBody ProjectTask updatedProjectTask, BindingResult result,
                                               @PathVariable String backlog_id, @PathVariable String pt_id ){

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if (errorMap != null) return errorMap;
        ProjectTask updatedTask = projectTaskService.updateByProjectSequence(updatedProjectTask,backlog_id,pt_id);
        return new ResponseEntity<ProjectTask>(updatedTask,HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id){
        projectTaskService.deletePTByProjectSequence(backlog_id, pt_id);
        return new ResponseEntity<String>("Project Task "+pt_id+" was deleted successfully", HttpStatus.OK);
    }
}
