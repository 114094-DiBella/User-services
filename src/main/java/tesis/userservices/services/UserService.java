package tesis.userservices.services;

import org.springframework.stereotype.Service;
import tesis.userservices.dtos.LoginResponse;
import tesis.userservices.dtos.UserDto;
import tesis.userservices.dtos.UserRequest;
import tesis.userservices.entities.UserEntity;
import tesis.userservices.models.User;


import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<User> getAllUsers();

    Optional<UserEntity> findByEmail(String email);

    LoginResponse login(UserDto userDto);

    User createUser(UserRequest user);

    User changePassword(UserDto userDto);

    User getUserByNumberDoc(Long numberDoc);

    void updateUser(Long document , UserRequest userDto);

}
