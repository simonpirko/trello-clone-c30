package by.tms.trelloclonec30.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TeamCreateDTO {
    private String teamName;
    private Long idWorkspace;
}

