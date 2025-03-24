package com.usermanagement.infrastructure.persistence.repository;

import com.usermanagement.domain.entity.Role;
import com.usermanagement.domain.entity.User;
import com.usermanagement.domain.repository.UserRepository;
import com.usermanagement.infrastructure.persistence.entity.RoleEntity;
import com.usermanagement.infrastructure.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    @Autowired
    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = mapToEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(userEntity);
        return mapToDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaUserRepository.findById(id).map(this::mapToDomain);
    }

    @Override
    public Optional<User> findByAuth0Id(String auth0Id) {
        return jpaUserRepository.findByAuth0Id(auth0Id).map(this::mapToDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email).map(this::mapToDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByRoleName(String roleName) {
        return jpaUserRepository.findByRoleName(roleName).stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(User user) {
        jpaUserRepository.deleteById(user.getId());
    }

    @Override
    public void deleteById(Long id) {
        jpaUserRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByAuth0Id(String auth0Id) {
        return jpaUserRepository.existsByAuth0Id(auth0Id);
    }

    private UserEntity mapToEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setAuth0Id(user.getAuth0Id());
        entity.setEmail(user.getEmail());
        entity.setName(user.getName());
        entity.setPictureUrl(user.getPictureUrl());
        entity.setEnabled(user.isEnabled());
        entity.setCreatedAt(user.getCreatedAt());
        entity.setUpdatedAt(user.getUpdatedAt());

        if (user.getRoles() != null) {
            user.getRoles().forEach(role -> {
                RoleEntity roleEntity = new RoleEntity();
                roleEntity.setId(role.getId());
                roleEntity.setName(role.getName());
                roleEntity.setDescription(role.getDescription());
                entity.addRole(roleEntity);
            });
        }

        return entity;
    }

    private User mapToDomain(UserEntity entity) {
        User user = new User();
        user.setId(entity.getId());
        user.setAuth0Id(entity.getAuth0Id());
        user.setEmail(entity.getEmail());
        user.setName(entity.getName());
        user.setPictureUrl(entity.getPictureUrl());
        user.setEnabled(entity.isEnabled());
        user.setCreatedAt(entity.getCreatedAt());
        user.setUpdatedAt(entity.getUpdatedAt());

        entity.getRoles().forEach(roleEntity -> {
            Role role = new Role();
            role.setId(roleEntity.getId());
            role.setName(roleEntity.getName());
            role.setDescription(roleEntity.getDescription());
            user.addRole(role);
        });

        return user;
    }
}