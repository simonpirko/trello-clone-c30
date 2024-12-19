package by.tms.trelloclonec30.dto.issue;

import by.tms.trelloclonec30.dto.account.AccountShowDto;
import by.tms.trelloclonec30.entity.Project;
import by.tms.trelloclonec30.entity.Status;
import lombok.Data;

@Data
public class IssueShowDto {
    private Long id;
    private String title;
    private String description;
    private AccountShowDto author;
    private AccountShowDto assignee;
    private Project project;
    private Status currentStatus;

    @Override
    public String toString() {
        return "IssueShowDto{" +
                "id=" + id + ", " +
                "title='" + title + "', " +
                "description='" + description + "', " +
                "currentStatus='" + currentStatus + "', " +
                "author='" + author + "', " +
                "assignee='" + assignee + "', " +
                "project='" + project + "'}";
    }
}