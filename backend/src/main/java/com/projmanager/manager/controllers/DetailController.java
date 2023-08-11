package com.projmanager.manager.controllers;

import com.projmanager.manager.models.Component;
import com.projmanager.manager.models.Detail;
import com.projmanager.manager.repository.ComponentRepository;
import com.projmanager.manager.repository.DetailRepository;
import com.projmanager.manager.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/det")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DetailController {

    @Autowired
    private DetailRepository detailRepository;

    @Autowired
    private ComponentRepository componentRepository;

    @PostMapping("/create/{compNumber}")
    public ResponseEntity<?> createDetail(@PathVariable String compNumber,
                                          @RequestBody Map<String, String> detailData) {
        try {
            // Fetch the component by compNumber from the database
            Optional<Component> optionalComponent = componentRepository.findByCompNumber(compNumber);
            if (optionalComponent.isEmpty()) {
                return ResponseEntity.badRequest().body("Component not found with compNumber: " + compNumber);
            }

            // Get the Component object from the Optional
            Component component = optionalComponent.get();

            // Create a new Detail entity
            Detail detail = new Detail();
            
            // Set the component number for the detail
            detail.setCompNumber(compNumber);

            // Set the properties from the incoming JSON
            detail.setDetTitle(detailData.get("detTitle"));
            detail.setDetDetail(detailData.get("detDetail"));
            detail.setPriority(detailData.get("priority"));
            detail.setDeadline(LocalDate.parse(detailData.get("deadline")));
            detail.setUsername(detailData.get("username"));

            // Associate the detail with the fetched component
            detail.getComponents().add(component);
            
            // Save the detail
            detailRepository.save(detail);

            return ResponseEntity.ok("Detail created successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating detail: " + e.getMessage());
        }
    }


    
    @GetMapping("/getbycomp/{compNumber}")
    public ResponseEntity<?> getDetailsByComponent(@PathVariable String compNumber) {
        try {
            // Find the details by its compNumber
            List<Detail> details = detailRepository.findByCompNumber(compNumber);
            if (!details.isEmpty()) {
                return ResponseEntity.ok(details);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching details: " + e.getMessage());
        }
    }

    
    @GetMapping("/getbydet/{detNumber}")
    public ResponseEntity<?> getDetailByDetNumber(@PathVariable String detNumber) {
        try {
            // Find the detail by its detNumber
            Optional<Detail> optionalDetail = detailRepository.findByDetNumber(detNumber);
            if (optionalDetail.isPresent()) {
                Detail detail = optionalDetail.get();
                return ResponseEntity.ok(detail);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching detail: " + e.getMessage());
        }
    }
    
    @PutMapping("/complete/{detNumber}")
    public ResponseEntity<?> completeDetail(@PathVariable String detNumber) {
        try {
            Optional<Detail> optionalDetail = detailRepository.findByDetNumber(detNumber);
            if (optionalDetail.isPresent()) {
                Detail detail = optionalDetail.get();
                detail.setDetStatus(true);
                detailRepository.save(detail);
                return ResponseEntity.ok("Detail marked as completed!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error completing detail: " + e.getMessage());
        }
    }
    
    @PutMapping("/incomplete/{detNumber}")
    public ResponseEntity<?> incompleteDetail(@PathVariable String detNumber) {
        try {
            Optional<Detail> optionalDetail = detailRepository.findByDetNumber(detNumber);
            if (optionalDetail.isPresent()) {
                Detail detail = optionalDetail.get();
                detail.setDetStatus(false);
                detailRepository.save(detail);
                return ResponseEntity.ok("Detail marked as incomplete!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error incompleting detail: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/delete/{detNumber}")
    public ResponseEntity<?> deleteDetailByDetNumber(@PathVariable String detNumber) {
        try {
            Optional<Detail> optionalDetail = detailRepository.findByDetNumber(detNumber);
            if (optionalDetail.isPresent()) {
                Detail detail = optionalDetail.get();
                detailRepository.delete(detail);
                return ResponseEntity.ok("Detail deleted successfully!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting detail: " + e.getMessage());
        }
    }
    
    @PutMapping("/update/{detNumber}")
    public ResponseEntity<?> updateDetailByDetNumber(@PathVariable String detNumber, @RequestBody Detail updatedDetail) {
        try {
            Optional<Detail> optionalDetail = detailRepository.findByDetNumber(detNumber);
            if (optionalDetail.isPresent()) {
                Detail detail = optionalDetail.get();
                
                // Update the fields of the detail with the values from the updatedDetail
                detail.setDetTitle(updatedDetail.getDetTitle());
                detail.setDetDetail(updatedDetail.getDetDetail());
                detail.setPriority(updatedDetail.getPriority());
                detail.setDeadline(updatedDetail.getDeadline());
                detail.setDetStatus(updatedDetail.isDetStatus());
                detail.setUsername(updatedDetail.getUsername());
                
                detailRepository.save(detail);
                return ResponseEntity.ok("Detail updated successfully!");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating detail: " + e.getMessage());
        }
    }



}
