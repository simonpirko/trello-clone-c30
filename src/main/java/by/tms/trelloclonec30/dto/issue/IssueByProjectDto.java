package by.tms.trelloclonec30.dto.issue;

import by.tms.trelloclonec30.dto.account.AccountShowDto;
import by.tms.trelloclonec30.entity.Status;
import lombok.Data;

@Data
public class IssueByProjectDto {
    private Long id;
    private String title;
    private String description;
    private AccountShowDto author;
    private AccountShowDto assignee;
    private Status currentStatus;

    @Override
    public String toString() {
        return "IssueByProjectDto{" +
                "id=" + id + ", " +
                "title='" + title + "', " +
                "description='" + description + "', " +
                "currentStatus='" + currentStatus + "', " +
                "author='" + author + "', " +
                "assignee='" + assignee + "', " + "'}";
    }
}