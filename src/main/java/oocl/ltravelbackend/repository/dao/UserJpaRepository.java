package oocl.ltravelbackend.repository.dao;

import oocl.ltravelbackend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
}
