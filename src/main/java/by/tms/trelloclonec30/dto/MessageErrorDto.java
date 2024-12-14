package by.tms.trelloclonec30.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class MessageErrorDto {

    private int statusCode;
    private String message;

}
