package ru.zadvoryev.productionapp.dto;

import ru.zadvoryev.productionapp.data.Role;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

public class UserDto implements Serializable {

    private Long id;

    @Size(min = 3, max = 15, message = "Логин должен содержать от 3 до 15 символов!")
    private String username;

    @Size(min = 3, max = 15, message = "Пароль должен содержать от 3 до 15 символов!")
    private String password;

    @Size(min = 1, max = 25, message = "Имя должно содержать от 1 до 25 символов!")
    private String name;

    @Size(min = 1, max = 25, message = "Фамилия должна содержать от 1 до 25 символов!")
    private String surname;

    @Size(min = 1, message = "Выбирите роль пользователя!")
    @NotNull
    private Collection<Role> roles;

    public UserDto(Long id,
                   String username,
                   String password,
                   String name,
                   String surname,
                   Collection<Role> roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDto)) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(getId(), userDto.getId()) &&
                Objects.equals(getUsername(), userDto.getUsername()) &&
                Objects.equals(getPassword(), userDto.getPassword()) &&
                Objects.equals(getName(), userDto.getName()) &&
                Objects.equals(getSurname(), userDto.getSurname()) &&
                Objects.equals(getRoles(), userDto.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getName(), getSurname(), getRoles());
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}


