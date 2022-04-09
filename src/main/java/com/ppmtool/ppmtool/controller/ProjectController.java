package com.ppmtool.ppmtool.controller;

import com.ppmtool.ppmtool.domain.Project;
import com.ppmtool.ppmtool.services.ProjectService;
import com.ppmtool.ppmtool.utilService.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    private ProjectService projectService;
    private MapValidationErrorService mapValidationService;

    @Autowired
    public ProjectController(ProjectService projectService,MapValidationErrorService mapValidationService) {
        this.projectService = projectService;
        this.mapValidationService = mapValidationService;
    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project,
                                              BindingResult result, Principal principal) {
        ResponseEntity<?> errMap = mapValidationService.mapValidationService(result);
        if(null != errMap) return errMap;

        return new ResponseEntity<>(projectService.saveUpdateProject(project, principal.getName()), HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> findByProjectIdentifier(@PathVariable String projectId) {
        return new ResponseEntity<>(projectService.findProjectByIdentifier(projectId), HttpStatus.OK);

    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Project>> findAllProject() {
        return new ResponseEntity<>(projectService.findAllProject(), HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteByProjectId(@PathVariable String projectId) {
        projectService.deleteByProjectId(projectId);
        return new ResponseEntity<String>("Project Id: " + projectId + " was successfully deleted!", HttpStatus.OK);
    }


}
