package pl.myblog.springblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.myblog.springblog.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

}
