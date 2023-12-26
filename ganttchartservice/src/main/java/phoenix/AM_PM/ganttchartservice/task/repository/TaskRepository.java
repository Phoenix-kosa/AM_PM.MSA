package phoenix.AM_PM.ganttchartservice.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phoenix.AM_PM.ganttchartservice.task.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {

    List<Task> findByProjectId(int projectId);
}
