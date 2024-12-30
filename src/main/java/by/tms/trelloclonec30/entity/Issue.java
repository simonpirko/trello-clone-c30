package by.tms.trelloclonec30.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@ToString
@Table(name = "issues")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_author")
    private Account author;
    @ManyToOne
    @JoinColumn(name = "id_assignee")
    private Account assignee;
    @ManyToOne
    @JoinColumn(name = "id_project")
    private Project project;
    @Column(nullable = false)
    private Status currentStatus;

    @ManyToMany
    private Set<Roles> roles = new HashSet<>();
}