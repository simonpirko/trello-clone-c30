package by.tms.trelloclonec30.repository;

import by.tms.trelloclonec30.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findAllByWorkspace_Id(Long workspaceId);
    Optional<Team> findById(Long teamId);
}
