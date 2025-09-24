package oocl.ltravelbackend.repository;

import oocl.ltravelbackend.model.entity.User;

public interface UserRepository {
    User findUserById(Long id);
}
