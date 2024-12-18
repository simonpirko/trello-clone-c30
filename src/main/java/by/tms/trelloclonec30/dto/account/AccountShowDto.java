package by.tms.trelloclonec30.dto.account;
import lombok.Data;

@Data
public class AccountShowDto {
    private Long id;
    private String username;

    @Override
    public String toString() {
        return "AccountShowDto{" +
                "id='" + id + "', " +
                "username='" + username + "'}";
    }
}