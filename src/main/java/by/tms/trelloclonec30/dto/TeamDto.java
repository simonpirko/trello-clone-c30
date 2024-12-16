package by.tms.trelloclonec30.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class TeamDto {

    private String teamName;
    private List<String> members;
}
