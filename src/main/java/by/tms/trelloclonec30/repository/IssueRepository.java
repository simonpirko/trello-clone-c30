package by.tms.trelloclonec30.repository;

import by.tms.trelloclonec30.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    Optional<Issue> findById(Long id);

    List<Issue> findByProjectId(Long projectId);
}