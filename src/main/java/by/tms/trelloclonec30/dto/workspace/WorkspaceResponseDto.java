package by.tms.trelloclonec30.dto.workspace;

import by.tms.trelloclonec30.dto.TeamDto;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class WorkspaceResponseDto {

    private Long idWorkspace;
    private String nameWorkspace;
    private String nameAuthor;
    private List<TeamDto> teams;
}
