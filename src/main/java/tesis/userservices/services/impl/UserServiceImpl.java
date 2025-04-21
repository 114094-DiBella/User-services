package tesis.userservices.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tesis.userservices.config.JwtUtil;
import tesis.userservices.dtos.LoginResponse;
import tesis.userservices.dtos.UserDto;
import tesis.userservices.dtos.UserRequest;
import tesis.userservices.entities.UserEntity;
import tesis.userservices.models.User;
import tesis.userservices.repositories.UserJpaRepository;
import tesis.userservices.services.UserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        try {
            List<UserEntity> users = userJpaRepository.findAll();
            if (users.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
            }
            return users.stream()
                    .map(userEntity -> modelMapper.map(userEntity, User.class))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving users", e);
        }
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        try {
            Optional<UserEntity> user = userJpaRepository.findByEmail(email);
            if (user.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }
            return user;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error searching user by email", e);
        }
    }

    @Override
    public LoginResponse login(UserDto userDto) {
        try {
            Optional<UserEntity> userOptional = userJpaRepository.findByEmail(userDto.getEmail());

            if (userOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            UserEntity userEntity = userOptional.get();

            // Use passwordEncoder to compare passwords securely
            //if (!passwordEncoder.matches(userDto.getPassword(), userEntity.getPassword())) {
            //    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
            //}

            String token = jwtUtil.generateToken(userEntity.getEmail());

            return new LoginResponse(token, "Login exitoso");

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during login", e);
        }
    }

    @Override
    public User createUser(UserRequest user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is required");
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last name is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }
        if (user.getTelephone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Telephone is required");
        }
        if (user.getNumberDoc() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number of the document is required");
        }
        if (user.getBirthday() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Birthday is required");
        }

        Optional<UserEntity> userOptional = userJpaRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        userEntity.setId(UUID.randomUUID());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setUpdatedAt(LocalDateTime.now());
        userJpaRepository.save(userEntity);
        return modelMapper.map(userEntity, User.class);

    }

    @Override
    public User changePassword(UserDto userDto) {
        Optional<UserEntity> userOptional = userJpaRepository.findByEmail(userDto.getEmail());
        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();
            userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userEntity.setUpdatedAt(LocalDateTime.now());
            userJpaRepository.save(userEntity);
            return modelMapper.map(userEntity, User.class);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    @Override
    public User getUserByNumberDoc(Long numberDoc) {
        Optional<UserEntity> userOptional = userJpaRepository.findByNumberDoc(numberDoc);
        return userOptional.map(userEntity -> modelMapper.map(userEntity, User.class)).orElse(null);
    }

    @Override
    public void updateUser(String id , UserRequest userDto) {
        Optional<UserEntity> userOptional = userJpaRepository.findById(UUID.fromString(id));
        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();
            modelMapper.map(userDto, userEntity);
            userEntity.setUpdatedAt(LocalDateTime.now());
            userJpaRepository.save(userEntity);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    @Override
    public User getUserById(String id) {
        Optional<UserEntity> userOptional = userJpaRepository.findById(UUID.fromString(id));
        return userOptional.map(userEntity -> modelMapper.map(userEntity, User.class)).orElse(null);
    }

    @Override
    public void deleteUser(String id) {
        Optional<UserEntity> userOptional = userJpaRepository.findById(UUID.fromString(id));
        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();
            userEntity.setUpdatedAt(LocalDateTime.now());
            userJpaRepository.delete(userEntity);
            return;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
}