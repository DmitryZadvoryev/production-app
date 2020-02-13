package ru.zadvoryev.productionapp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zadvoryev.productionapp.data.Role;
import ru.zadvoryev.productionapp.dto.UserDto;
import ru.zadvoryev.productionapp.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;


/**
 * Список пользователей, добавление, удаление, редактирование
 */
@Controller
@RequestMapping("users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String list(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user-create")
    public String showCreateForm(UserDto user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("allRoles", Role.values());
        return "users/user-create";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user-create")
    public String create(@RequestParam(value = "role") Collection<Role> roles,
                         @Valid @ModelAttribute("user") UserDto user,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "users/user-create";
        }
        user.setRoles(roles);
        userService.createOrUpdate(user);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        UserDto user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("allRoles", Role.values());
        return "users/user-update";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/user-update")
    public String updateUser(@RequestParam(value = "role") Collection<Role> roles,
                             @Valid @ModelAttribute("user") UserDto user,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "users/user-update";
        }
        user.setRoles(roles);
        userService.createOrUpdate(user);
        return "redirect:/users";
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/user-delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/users";
    }

    @ModelAttribute("admin")
    public Role getAdmin() {
        return Role.ADMIN;
    }

}
