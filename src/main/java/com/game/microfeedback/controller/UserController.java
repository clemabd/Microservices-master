package com.game.microfeedback.controller;

import com.game.microfeedback.dao.UserDao;
import com.game.microfeedback.exceptions.UserNotFindException;
import com.game.microfeedback.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(description = "Users Management")
@RestController
public class UserController {

    // DAO layer
    @Autowired
    private UserDao userDao;

    // Users
    @ApiOperation(value = "Get all users")
    @GetMapping(value = "Users")
    public List<User> userList() {
        return userDao.findAll();
    }

    // Users/{id}
    @ApiOperation(value = "Get a user based on its ID")
    @GetMapping(value = "Users/{id}")
    public User getUser(@PathVariable int id) throws UserNotFindException {

        User user = userDao.findById(id);

        if (user == null) throw new UserNotFindException("User " + id + " not found... ");

        return user;
    }

    // Post User
    @ApiOperation(value = "Create a new user")
    @PostMapping(value = "/Users")
    public ResponseEntity<Void> addUser(@Valid @RequestBody User user) {
        User userSend = userDao.save(user);

        if (user == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userSend.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }



}
