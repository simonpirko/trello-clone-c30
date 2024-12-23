package by.tms.trelloclonec30.dto.project;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProjectCreateDto {

    private Long id_workspace;
    private String name;
    private String description;
}
