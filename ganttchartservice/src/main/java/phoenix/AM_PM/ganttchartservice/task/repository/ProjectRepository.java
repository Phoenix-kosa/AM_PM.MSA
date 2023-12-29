package phoenix.AM_PM.ganttchartservice.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phoenix.AM_PM.ganttchartservice.task.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}