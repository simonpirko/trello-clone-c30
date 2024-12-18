package by.tms.trelloclonec30.dto.issue;

import lombok.Data;

@Data
public class IssueDeleteByIssueDto {
    private String id;

    @Override
    public String toString() {
        return "IssueDeleteDto{" +
                "id='" + id + "'}";
    }
}