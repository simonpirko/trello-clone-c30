package by.tms.trelloclonec30.repository;


import by.tms.trelloclonec30.entity.Account;
import by.tms.trelloclonec30.entity.Role;
import by.tms.trelloclonec30.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RolesRepository extends JpaRepository<Roles, Long> {
      Set<Roles> findAllByAccountAndRole(Account account, Role role);
}
