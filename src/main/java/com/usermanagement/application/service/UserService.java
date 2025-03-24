package com.usermanagement.application.service;

import com.usermanagement.application.dto.UserDTO;
import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long id, UserDTO userDTO);
    UserDTO getUser(Long id);
    UserDTO getUserByAuth0Id(String auth0Id);
    UserDTO getUserByEmail(String email);
    List<UserDTO> getAllUsers();
    List<UserDTO> getUsersByRole(String roleName);
    void deleteUser(Long id);
    void addRoleToUser(Long userId, Long roleId);
    void removeRoleFromUser(Long userId, Long roleId);
    boolean existsByEmail(String email);
    boolean existsByAuth0Id(String auth0Id);
    UserDTO enableUser(Long id);
    UserDTO disableUser(Long id);
}