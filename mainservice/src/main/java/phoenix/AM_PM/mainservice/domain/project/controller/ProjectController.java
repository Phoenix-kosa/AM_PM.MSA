package phoenix.AM_PM.mainservice.domain.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phoenix.AM_PM.mainservice.domain.project.dto.RequestProject;
import phoenix.AM_PM.mainservice.domain.project.dto.ResponseProject;
import phoenix.AM_PM.mainservice.domain.project.service.ProjectService;
import phoenix.AM_PM.mainservice.global.config.auth.MyUserDetails;
import phoenix.AM_PM.mainservice.global.exception.BusinessLogicException;
import phoenix.AM_PM.mainservice.openfeign.ProjectPlanServiceClient;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private final ProjectService projectService;
    private final ProjectPlanServiceClient projectPlanServiceClient;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectPlanServiceClient projectPlanServiceClient) {
        this.projectService = projectService;
        this.projectPlanServiceClient = projectPlanServiceClient;
    }

    // 목록 조회
    @GetMapping
    public ResponseEntity getProjectList(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        List<ResponseProject> projectList = projectService.getProjectList(myUserDetails.getUser().getId());
        return ResponseEntity.ok().body(projectList);
    }
    // 조회
    @GetMapping("/{project-id}")
    public ResponseEntity getProject(@PathVariable("project-id") Integer projectId) {
        ResponseProject project = projectService.readProject(projectId);
        return ResponseEntity.ok().body(project);
    }
    // 생성
    @PostMapping
    public ResponseEntity createProject(@RequestBody RequestProject requestProject,
                                        @AuthenticationPrincipal MyUserDetails myUserDetails) {
        ResponseProject project = projectService.createProject(requestProject, myUserDetails.getUser());
        projectPlanServiceClient.createDefaultProjectPlans(project.getId()); // Feign 클라이언트를 사용하여 호출
        return new ResponseEntity(project, HttpStatus.CREATED);
    }
    // 수정
    @PutMapping("/{project-id}")
    public ResponseEntity modifyProject(@RequestBody RequestProject requestProject,
                                        @PathVariable("project-id") Integer projectId,
                                        @AuthenticationPrincipal MyUserDetails myUserDetails) {
        try{
            projectService.modifyProject(projectId, requestProject, myUserDetails.getUser());
        } catch (BusinessLogicException e) {
            return new ResponseEntity(HttpStatusCode.valueOf(e.getExceptionCode().getStatus()));
        }
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }
    // 삭제
    @DeleteMapping("/{project-id}")
    public ResponseEntity deleteProject(@PathVariable("project-id") Integer projectId,
                                        @AuthenticationPrincipal MyUserDetails myUserDetails) {
        try {
            projectService.deleteProject(projectId, myUserDetails.getUser());
        } catch (BusinessLogicException e) {
            return new ResponseEntity(HttpStatusCode.valueOf(e.getExceptionCode().getStatus()));
        }
        return new ResponseEntity(HttpStatus.RESET_CONTENT);
    }
}
