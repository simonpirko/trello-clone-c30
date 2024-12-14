package by.tms.trelloclonec30.repository;

import by.tms.trelloclonec30.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    Optional<Workspace> findById(Long id);

    List<Workspace> findAllByAuthor_Id(Long id);

}
