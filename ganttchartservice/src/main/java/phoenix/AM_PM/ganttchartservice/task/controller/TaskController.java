package phoenix.AM_PM.ganttchartservice.task.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phoenix.AM_PM.ganttchartservice.task.entity.Project;
import phoenix.AM_PM.ganttchartservice.task.repository.ProjectRepository;
import phoenix.AM_PM.ganttchartservice.task.dto.AddTaskDTO;
import phoenix.AM_PM.ganttchartservice.task.dto.EditTaskDTO;
import phoenix.AM_PM.ganttchartservice.task.entity.Task;
import phoenix.AM_PM.ganttchartservice.task.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TaskController {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    @GetMapping("/task/{projectId}")
    public ResponseEntity<List<Task>> getTasks(@PathVariable(name = "projectId") int projectId) {
        List<Task> tasks = taskRepository.findByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/task")
    public ResponseEntity<String> addTask( @RequestBody AddTaskDTO dto) {
        try {
            Random random = new Random();
            int randomNumber = random.nextInt(5);
            String[] bgList = {"#02343F", "#331B3F", "#0A174E", "#07553B", "#50586C"};
            String[] fontColorList = {"#F0EDCC", "#ACC7B4", "#F5D042", "#CED46A", "#DCE2F0"};
            Optional<Project> projectOptional = projectRepository.findById(dto.getProjectId());
                if (projectOptional.isPresent()) {
                    Project projectInfo = projectOptional.get();
                    Task taskEntity = Task.builder()
                            .projectId(dto.getProjectId())
                            .content(dto.getContent())
                            .beginDate(projectInfo.getStartDate().atStartOfDay())
                            .endDate(projectInfo.getEndDate().atStartOfDay())
                            .backgroundColor(bgList[randomNumber])
                            .frontColor(fontColorList[randomNumber])
                            .build();
                    taskRepository.save(taskEntity);
                    return ResponseEntity.ok("successful");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("project not found");
                }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add notice: " + e.getMessage());
        }
    }

    @DeleteMapping("/task/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable(name = "taskId") String taskId) {
        try {
            System.out.println(taskId);
            taskRepository.deleteById(taskId);
            return ResponseEntity.ok("successful");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notice not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Delete failed");
        }
    }

    @PutMapping("/task/{taskId}")
    public ResponseEntity<String> editTask(@PathVariable(name = "taskId") String taskId, @RequestBody EditTaskDTO dto) {
        try {
            Optional<Task> taskOptional = taskRepository.findById(taskId);
            if (taskOptional.isPresent()) {
                Task TaskInfo = taskOptional.get();
                TaskInfo.setBeginDate(dto.getBeginDate());
                TaskInfo.setEndDate(dto.getEndDate());
                TaskInfo.setContent(dto.getContent());
                taskRepository.save(TaskInfo);
                return ResponseEntity.ok("successful");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("task not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
