package by.tms.trelloclonec30.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
/*    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> authorities = new HashSet<>();*/

}
