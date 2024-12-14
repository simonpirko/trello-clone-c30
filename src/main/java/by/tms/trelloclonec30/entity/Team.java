package by.tms.trelloclonec30.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    private List<Account> accounts;

    @ManyToOne
    @JoinColumn(name = "id_workspace")
    private Workspace workspace;
}
