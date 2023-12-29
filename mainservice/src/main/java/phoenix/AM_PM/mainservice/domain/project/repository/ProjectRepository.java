package phoenix.AM_PM.mainservice.domain.project.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import phoenix.AM_PM.mainservice.domain.project.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}