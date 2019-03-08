package com.game.microfeedback.dao;


import com.game.microfeedback.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionDao extends JpaRepository<Session, Integer> {
    Session findById(int id);

    List<Session> findAll();

}
