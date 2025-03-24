package com.usermanagement.presentation.controller;

import com.usermanagement.application.dto.RoleDTO;
import com.usermanagement.application.dto.UserDTO;
import com.usermanagement.application.service.RoleService;
import com.usermanagement.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String adminDashboard() {
        return "redirect:/admin/users";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/users/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin/create-user";
    }

    @PostMapping("/users/create")
    public String createUser(@ModelAttribute("user") UserDTO userDTO, RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(userDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário criado com sucesso!");
            return "redirect:/admin/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao criar usuário: " + e.getMessage());
            return "redirect:/admin/users/create";
        }
    }

    @GetMapping("/users/{id}/edit")
    public String showEditUserForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            UserDTO user = userService.getUser(id);
            model.addAttribute("user", user);
            model.addAttribute("allRoles", roleService.getAllRoles());
            return "admin/edit-user";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Usuário não encontrado");
            return "redirect:/admin/users";
        }
    }

    @PostMapping("/users/{id}/edit")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") UserDTO userDTO, RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(id, userDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário atualizado com sucesso!");
            return "redirect:/admin/users";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar usuário: " + e.getMessage());
            return "redirect:/admin/users/" + id + "/edit";
        }
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário removido com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao remover usuário: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/enable")
    public String enableUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.enableUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário habilitado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao habilitar usuário: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/disable")
    public String disableUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.disableUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "Usuário desabilitado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao desabilitar usuário: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{userId}/roles/{roleId}/add")
    public String addRoleToUser(@PathVariable Long userId, @PathVariable Long roleId, RedirectAttributes redirectAttributes) {
        try {
            userService.addRoleToUser(userId, roleId);
            redirectAttributes.addFlashAttribute("successMessage", "Função adicionada ao usuário com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao adicionar função ao usuário: " + e.getMessage());
        }
        return "redirect:/admin/users/" + userId + "/edit";
    }

    @PostMapping("/users/{userId}/roles/{roleId}/remove")
    public String removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId, RedirectAttributes redirectAttributes) {
        try {
            userService.removeRoleFromUser(userId, roleId);
            redirectAttributes.addFlashAttribute("successMessage", "Função removida do usuário com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao remover função do usuário: " + e.getMessage());
        }
        return "redirect:/admin/users/" + userId + "/edit";
    }
}