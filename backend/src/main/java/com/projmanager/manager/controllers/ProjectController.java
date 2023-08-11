package com.projmanager.manager.controllers;

import com.projmanager.manager.models.Project;
import com.projmanager.manager.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping("/project/create")
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        try {
            Project newProject = new Project();
            newProject.setProjTitle(project.getProjTitle());
            newProject.setProjDetail(project.getProjDetail());
            projectRepository.save(newProject);

            return ResponseEntity.ok("Project created successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating project: " + e.getMessage());
        }
    }
    
    @PutMapping("/project/complete/{projNumber}")
    public ResponseEntity<?> completeProject(@PathVariable String projNumber) {
        try {
            Project project = projectRepository.findByProjNumber(projNumber).orElse(null);
            if (project == null) {
                return ResponseEntity.notFound().build();
            }
            
            project.setProjStatus(true);
            projectRepository.save(project);

            return ResponseEntity.ok("Project marked as completed!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error completing project: " + e.getMessage());
        }
    }
    
    @PutMapping("/project/incomplete/{projNumber}")
    public ResponseEntity<?> incompleteProject(@PathVariable String projNumber) {
        try {
            Project project = projectRepository.findByProjNumber(projNumber).orElse(null);
            if (project == null) {
                return ResponseEntity.notFound().build();
            }
            
            project.setProjStatus(false);
            projectRepository.save(project);

            return ResponseEntity.ok("Project marked as incomplete!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error incompleting project: " + e.getMessage());
        }
    }
    
    @GetMapping("/project/getprojects")
    public ResponseEntity<List<Project>> getAllProjects() {
        try {
            List<Project> projects = projectRepository.findAll();
            return ResponseEntity.ok(projects);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
    
    @DeleteMapping("/project/{projNumber}")
    public ResponseEntity<?> deleteProjectByProjNumber(@PathVariable String projNumber) {
        try {
            Optional<Project> projectOptional = projectRepository.findByProjNumber(projNumber);
            if (projectOptional.isPresent()) {
                Project project = projectOptional.get();
                projectRepository.delete(project);
                return ResponseEntity.ok("Project deleted successfully!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting project: " + e.getMessage());
        }
    }


}
