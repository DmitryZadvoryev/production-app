package ru.zadvoryev.productionapp.service;

import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.zadvoryev.productionapp.converter.UserConverter;
import ru.zadvoryev.productionapp.data.User;
import ru.zadvoryev.productionapp.dto.UserDto;
import ru.zadvoryev.productionapp.repository.UserRepository;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    final UserRepository userRepository;

    final UserConverter converter;

    public UserService(UserRepository userRepository, UserConverter converter) {
        this.userRepository = userRepository;
        this.converter = converter;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username);
    }

    public List<UserDto> getAllUsers() {
            List<User> users = userRepository.findAll();
            return converter.createFromEntities(users);
    }


    public UserDto findById(long id) {
            User user = userRepository.getOne(id);
            return converter.convertFromEntity(user);
    }


    public void createOrUpdate(UserDto userDto) {
        User user = converter.convertFromDto(userDto);
        userRepository.save(user);
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }
}
