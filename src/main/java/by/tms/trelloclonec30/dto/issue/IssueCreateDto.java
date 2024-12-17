package by.tms.trelloclonec30.dto.issue;

import by.tms.trelloclonec30.entity.Status;
import lombok.Data;

@Data
public class IssueCreateDto {
    private String title;
    private String description;
    private String idAuthor;
    private String idAssignee;
    private String idProject;
    private Status currentStatus;

    @Override
    public String toString() {
        return "IssueCreateDto{" +
                "title='" + title + "', " +
                "description='" + description + "', " +
                "currentStatus='" + currentStatus + "', " +
                "idAuthor='" + idAuthor + "', " +
                "idAssignee='" + idAssignee + "', " +
                "idProject='" + idProject +  "'}";
    }
}