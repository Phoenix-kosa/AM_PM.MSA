
package com.example.planservice.projectplan.repository;

import com.example.planservice.projectplan.entity.ProjectPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectPlanRepository extends JpaRepository<ProjectPlan, Integer> {
    boolean existsByProjectIdAndTitle(int projectId, String title);

    int countByProjectIdAndTitleStartingWith(int projectId, String titleStartsWith);

    List<ProjectPlan> findByProjectIdAndTitleStartingWith(int projectId, String titleStartsWith);

    List<ProjectPlan> findByProjectId(int projectId);

    Optional<ProjectPlan> findByProjectIdAndTitle(int projectId, String title);

    Optional<ProjectPlan> findByIdAndProjectId(int id, int projectId);

    Optional<ProjectPlan> findByProjectIdAndId(int projectId, int id);

    Optional<ProjectPlan> findByTitleAndProjectId(String title, int projectId);
    List<ProjectPlan> findByTitle(String title);

}

