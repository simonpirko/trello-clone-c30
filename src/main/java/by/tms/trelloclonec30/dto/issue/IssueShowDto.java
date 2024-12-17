package by.tms.trelloclonec30.dto.issue;

import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.entity.Project;
import by.tms.trelloclonec30.entity.Status;
import lombok.Data;

@Data
public class IssueShowDto {
    private String id;
    private String title;
    private String description;
    private Account author;
    private Account assignee;
    private Project project;
    private Status currentStatus;

    @Override
    public String toString() {
        return "IssueDeleteDto{" +
                "id='" + id + "', " +
                "title='" + title + "', " +
                "description='" + description + "', " +
                "currentStatus='" + currentStatus + "', " +
                "author='" + author + "', " +
                "assignee='" + assignee + "', " +
                "project='" + project + "'}";
    }
}