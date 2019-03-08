package com.game.microfeedback.service;

import com.game.microfeedback.model.Feedback;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FeedbackService {

    public List<Feedback> getListOrdered(List<Feedback> feedbacks, String field, String sort) {

        List<Feedback> feedbacksMod = new ArrayList<>(feedbacks); //Create a modifiable list from the unmodifiable list return by DAO

        if (field.compareToIgnoreCase("note") == 0)
            if (sort.compareToIgnoreCase("desc") == 0)
                feedbacksMod.sort(Comparator.comparing(Feedback::getRating).reversed());
            else feedbacksMod.sort(Comparator.comparing(Feedback::getRating));
        return feedbacksMod;
    }

}
