package com.game.microfeedback.dao;

import com.game.microfeedback.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackDao extends JpaRepository<Feedback, Integer> {

    Feedback findById(int id);

    List<Feedback> findAll();

    @Override
    Page<Feedback> findAll(Pageable pageable);

}
