package com.game.microfeedback.controller;

import com.game.microfeedback.dao.SessionDao;
import com.game.microfeedback.exceptions.SessionNotFindException;
import com.game.microfeedback.model.Session;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(description = "Sessions management")
@RestController
public class SessionController {

    // DAO layer
    @Autowired
    private SessionDao sessionDao;

    // Sessions
    //Get Feedbacks with param
    @ApiOperation(value = "Get all the sessions")
    @GetMapping(value = "Sessions")
    public List<Session> sessionList() {
        return sessionDao.findAll();
    }

    // Sessions/{id}
    @ApiOperation(value = "Get a session based on its Id")
    @GetMapping(value = "Sessions/{id}")
    public Session getSession(@PathVariable int id) throws SessionNotFindException {

        Session session = sessionDao.findById(id);

        if (session == null) throw new SessionNotFindException("Session " + id + " not found... ");

        return session;
    }

    // Post Session
    @ApiOperation(value = "Create a  new session ")
    @PostMapping(value = "/Sessions")
    public ResponseEntity<Void> addSession(@Valid @RequestBody Session session) {
        Session sessionSend = sessionDao.save(session);

        if (session == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sessionSend.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }



}
