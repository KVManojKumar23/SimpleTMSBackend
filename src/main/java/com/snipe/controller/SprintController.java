package com.snipe.controller;

import com.snipe.dto.SprintDTO;
import com.snipe.entity.Sprint.Status;
import com.snipe.service.SprintServices;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/sprints")
public class SprintController {

    private final SprintServices sprintServices;

    public SprintController(SprintServices sprintServices) {
        this.sprintServices = sprintServices;
    }

    // ✅ Create Sprint
    @PostMapping
    public ResponseEntity<SprintDTO> createSprint(@RequestBody SprintDTO sprintDto) {
        return ResponseEntity.ok(sprintServices.createSprint(sprintDto));
    }

    // ✅ Get All Sprints
    @GetMapping
    public ResponseEntity<List<SprintDTO>> getAllSprints() {
        return ResponseEntity.ok(sprintServices.getAllSprints());
    }

    // ✅ Get Sprint by ID
    @GetMapping("/{sprintId}")
    public ResponseEntity<SprintDTO> getSprintById(@PathVariable Integer sprintId) {
        return ResponseEntity.ok(sprintServices.getSprintById(sprintId));
    }

    // ✅ Update Sprint
    @PutMapping("/{sprintId}")
    public ResponseEntity<SprintDTO> updateSprint(@PathVariable Integer sprintId, @RequestBody SprintDTO sprintDto) {
        return ResponseEntity.ok(sprintServices.updateSprint(sprintId, sprintDto));
    }

    // ✅ Delete Sprint
    @DeleteMapping("/{sprintId}")
    public ResponseEntity<String> deleteSprint(@PathVariable Integer sprintId) {
        sprintServices.deleteSprint(sprintId);
        return ResponseEntity.ok("Sprint deleted successfully");
    }

    // ✅ Get Sprints by Project ID
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<SprintDTO>> getSprintsByProjectId(@PathVariable Integer projectId) {
        return ResponseEntity.ok(sprintServices.getSprintsByProjectId(projectId));
    }

    // ✅ Get Sprints between Start Dates
    @GetMapping("/between")
    public ResponseEntity<List<SprintDTO>> getSprintsBetweenDates(@RequestParam String start, @RequestParam String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return ResponseEntity.ok(sprintServices.getSprintsBetweenDates(startDate, endDate));
    }

    // ✅ Get Active Sprints
    @GetMapping("/active")
    public ResponseEntity<List<SprintDTO>> getActiveSprints() {
        return ResponseEntity.ok(sprintServices.getActiveSprints());
    }

    // ✅ Count Sprints by Project ID
    @GetMapping("/count/{projectId}")
    public ResponseEntity<Long> countSprintsByProjectId(@PathVariable Integer projectId) {
        return ResponseEntity.ok(sprintServices.countSprintsByProjectId(projectId));
    }

    // ✅ Get Latest Sprint by Project ID
    @GetMapping("/latest/{projectId}")
    public ResponseEntity<SprintDTO> getLatestSprint(@PathVariable Integer projectId) {
        return ResponseEntity.ok(sprintServices.getLatestSprintByProjectId(projectId));
    }

    // ✅ Get Sprints by Project ID and Status
    @GetMapping("/project/{projectId}/status/{status}")
    public ResponseEntity<List<SprintDTO>> getSprintsByProjectIdAndStatus(@PathVariable Integer projectId, @PathVariable Status status) {
        return ResponseEntity.ok(sprintServices.getSprintsByProjectIdAndStatus(projectId, status));
    }

    // ✅ Get Sprints by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<SprintDTO>> getSprintsByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(sprintServices.getSprintsByStatus(status));
    }
}
