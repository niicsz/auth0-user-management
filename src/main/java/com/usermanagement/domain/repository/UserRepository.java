package com.usermanagement.domain.repository;

import com.usermanagement.domain.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByAuth0Id(String auth0Id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    List<User> findByRoleName(String roleName);
    void delete(User user);
    void deleteById(Long id);
    boolean existsByEmail(String email);
    boolean existsByAuth0Id(String auth0Id);
}