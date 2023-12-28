package com.example.planservice.projectplan.controller;

import com.example.planservice.projectplan.service.ProjectPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping
public class CreateController {

    @Autowired
    private ProjectPlanService projectPlanService;

    @PostMapping("/api/project")
    public ResponseEntity<?> createDefaultProjectPlans(@RequestParam("projectId") int projectId) {
        projectPlanService.createDefaultProjectPlans(projectId);
        return ResponseEntity.ok().build();
    }

}
