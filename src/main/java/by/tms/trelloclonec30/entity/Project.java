package by.tms.trelloclonec30.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@ToString
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_workspace")
    private Workspace workspace;
//    @OneToMany
//    @JoinColumn(name = "id_project")
//    private List<Issue> issues;
    @ManyToMany
    private List<Team> teams;

    @ManyToMany
    private Set<Roles> roles = new HashSet<>();
}
