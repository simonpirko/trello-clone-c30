package by.tms.trelloclonec30.repository;

import by.tms.trelloclonec30.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findAllByWorkspaceId(Long workspaceId);
}
