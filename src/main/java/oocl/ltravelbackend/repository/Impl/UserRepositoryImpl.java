package oocl.ltravelbackend.repository.Impl;

import oocl.ltravelbackend.model.entity.User;
import oocl.ltravelbackend.repository.UserRepository;
import oocl.ltravelbackend.repository.dao.UserJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Override
    public User findUserById(Long id) {
        return userJpaRepository.findById(id).orElse(null);
    }
}
