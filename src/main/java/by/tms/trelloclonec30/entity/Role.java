package by.tms.trelloclonec30.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

public enum Role {
    AUTHOR_WORKSPACE, AUTHOR_ISSUE, PROJECT_LEADER, ASSIGNED_TO_TASK, PROJECT_MEMBER;

}
