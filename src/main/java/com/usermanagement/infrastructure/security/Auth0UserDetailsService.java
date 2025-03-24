package com.usermanagement.infrastructure.security;

import com.usermanagement.application.service.RoleService;
import com.usermanagement.application.service.UserService;
import com.usermanagement.application.dto.RoleDTO;
import com.usermanagement.application.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Auth0UserDetailsService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;
    private final RoleService roleService;
    private final DefaultOAuth2UserService delegate;

    @Autowired
    public Auth0UserDetailsService(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.delegate = new DefaultOAuth2UserService();
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = delegate.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();

        String auth0Id = (String) attributes.get("sub");
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String pictureUrl = (String) attributes.get("picture");

        UserDTO userDTO;
        boolean isNewUser = false;

        try {
            userDTO = userService.getUserByAuth0Id(auth0Id);
        } catch (Exception e) {
            isNewUser = true;
            userDTO = new UserDTO();
            userDTO.setAuth0Id(auth0Id);
            userDTO.setEmail(email);
            userDTO.setName(name);
            userDTO.setPictureUrl(pictureUrl);
            userDTO.setEnabled(true);

            RoleDTO userRole;
            try {
                userRole = roleService.getRoleByName("ROLE_USER");
            } catch (Exception ex) {
                userRole = new RoleDTO();
                userRole.setName("ROLE_USER");
                userRole.setDescription("Papel padrão para usuários");
                userRole = roleService.createRole(userRole);
            }
            userDTO.getRoles().add(userRole);

            String now = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
            userDTO = userService.createUser(userDTO);
        }

        Collection<GrantedAuthority> authorities = new HashSet<>();
        if (userDTO.getRoles() != null) {
            authorities = userDTO.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toSet());
        }

        return new DefaultOAuth2User(
                authorities,
                attributes,
                "email");
    }
}