package com.github.ronnyaraujo.saleapi.service;

import java.util.List;
import java.util.Optional;

import com.github.ronnyaraujo.saleapi.exceptions.ObjectNotFoundException;
import com.github.ronnyaraujo.saleapi.model.UserModel;
import com.github.ronnyaraujo.saleapi.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<UserModel> getUsers() {
        LOGGER.info("Getting all users");
        List<UserModel> users = userRepository.findAll();
        return users;
    }

    public UserModel findById(Long id) throws ObjectNotFoundException {
        LOGGER.info("Getting user by ID: {}", id);

        Optional<UserModel> user = userRepository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFoundException("User not found Id: " + id));
    }

    public UserModel createUser(UserModel userModel) {
        UserModel userSaved = userRepository.save(userModel);
        LOGGER.info("Created user {} with id = {}", userModel.getName());
        return userSaved;
    }

    public UserModel updateUser(Long id, UserModel userModel) {
        LOGGER.info("Updated user {}, Id = {}", userModel.getName(), id);
        UserModel user = userRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException(String.format("User = [%s] not found", id));
        });

        user.setName(userModel.getName());
        user.setEmail(userModel.getEmail());
        user.setPhone(userModel.getPhone());
        user.setPassword(userModel.getPassword());
        userRepository.save(user);
        return user;
    }

    public void deleteUser(Long id) throws DataIntegrityViolationException{
        LOGGER.info("Deleted user Id = {}", id);
        findById(id);
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("User cannot be deleted, it is associated as an Order");
        }
    }
}
