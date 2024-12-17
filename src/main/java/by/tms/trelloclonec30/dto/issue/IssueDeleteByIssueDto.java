package by.tms.trelloclonec30.dto.issue;

import lombok.Data;

@Data
public class IssueDeleteByIssueDto {
    private String id;
//    private String title;
//    private String description;
//    private String idAuthor;
//    private String idAssignee;
//    private String idProject;
//    private Status currentStatus;

    @Override
    public String toString() {
        return "IssueDeleteDto{" +
                "id='" + id + //"', " +
//                "title='" + title + "', " +
//                "description='" + description + "', " +
//                "currentStatus='" + currentStatus + "', " +
//                "idAuthor='" + idAuthor + "', " +
//                "idAssignee='" + idAssignee + "', " +
//                "idProject='" + idProject +
                "'}";
    }
}