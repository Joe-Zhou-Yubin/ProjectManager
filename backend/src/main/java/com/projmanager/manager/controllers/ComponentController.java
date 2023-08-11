package com.projmanager.manager.controllers;

import com.projmanager.manager.models.Component;
import com.projmanager.manager.repository.ComponentRepository;
import com.projmanager.manager.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projmanager.manager.models.Project;

@RestController
@RequestMapping("/comp")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ComponentController {

    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping("/create/{projNumber}")
    public ResponseEntity<?> createComponent(@PathVariable String projNumber,
                                             @RequestBody Component component) {
        try {
            // Fetch the project by projNumber from the database
            Optional<Project> optionalProject = projectRepository.findByProjNumber(projNumber);
            if (optionalProject.isEmpty()) {
                return ResponseEntity.badRequest().body("Project not found with projNumber: " + projNumber);
            }

            // Get the Project object from the Optional
            Project project = optionalProject.get();
            
         // Set the projNumber of the component
            component.setProjNumber(projNumber);

            // Associate the component with the fetched project
            component.getProjects().add(project);

            // Save the component
            componentRepository.save(component);

            return ResponseEntity.ok("Component created successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating component: " + e.getMessage());
        }
    }
    
    @GetMapping("/get/{projNumber}")
    public ResponseEntity<?> getComponentsByProject(@PathVariable String projNumber) {
        try {
            List<Component> components = componentRepository.findByProjNumber(projNumber);
            return ResponseEntity.ok(components);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching components: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/delete/{compNumber}")
    public ResponseEntity<?> deleteComponent(@PathVariable String compNumber) {
        try {
            componentRepository.deleteByCompNumber(compNumber);
            return ResponseEntity.ok("Component deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting component: " + e.getMessage());
        }
    }


    


}
