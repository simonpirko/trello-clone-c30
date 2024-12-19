package by.tms.trelloclonec30.dto.project;

import by.tms.trelloclonec30.dto.issue.IssueByProjectDto;
import lombok.Data;

import java.util.List;

@Data
public class ProjectIssuesDto {
    private Long id;
    private String name;
    private String description;
    private List<IssueByProjectDto> issues;

    @Override
    public String toString() {
        return "ProjectIssuesDto{" +
                "id=" + id + ", " +
                "name='" + name + "', " +
                "description='" + description + "', " +
                "issues='" + issues + "'}";
    }
}
