package com.game.microfeedback.controller;

import com.game.microfeedback.dao.FeedbackDao;
import com.game.microfeedback.exceptions.feedback.FeedbackFiltersException;
import com.game.microfeedback.exceptions.feedback.FeedbackNotFindException;
import com.game.microfeedback.model.Feedback;
import com.game.microfeedback.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Api(description = "Feedbacks management")
@RestController
public class FeedbackController {

    // Couche données
    @Autowired
    private FeedbackDao feedbackDao;
    @Autowired
    private SessionController sessionController;
    @Autowired
    private UserController userController;
    @Autowired
    private FeedbackService feedbackService;

    // Feedback
    @ApiOperation(value = "Get all the feedbacks")
    @RequestMapping(method = RequestMethod.GET, value = "feedbacks")
    public List<Feedback> feedbackList() {
        return feedbackDao.findAll();
    }

    // Feedback/{id}
    @ApiOperation(value = "Get one feedback based on its Id")
    @GetMapping(value = "feedbacks/{id}")
    public Feedback getFeedback(@PathVariable int id) throws FeedbackNotFindException {

        Feedback feedback = feedbackDao.findById(id);

        if (feedback == null) throw new FeedbackNotFindException("Feedback " + id + " not found... ");

        return feedback;
    }

    // Post Feedback
    @ApiOperation(value = "Create a new feedback for the Session based on its Id")
    @PostMapping(value = "sessions/{id}/feedbacks")
    public ResponseEntity<Void> addFeedback(@Valid @RequestBody Feedback feedback, @PathVariable int id, @RequestHeader(value = "Ubi-UserId") int userId) {
        feedback.setSession(sessionController.getSession(id));
        feedback.setUser(userController.getUser(userId));
        Feedback feedbackSend = feedbackDao.save(feedback);

        if (feedback == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("Feedbacks/{id}")
                .buildAndExpand(feedbackSend.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //Get Feedbacks with param
    @ApiOperation(value = "Get feedback(s) based on param like numbers of feedback wanted, the field we want to use to order and the sort wanted")
    @RequestMapping(method = RequestMethod.GET, value = "feedbacks", params = {"search", "orderby", "sort"})
    public List<Feedback> getFeedbackOrdered(@RequestParam("search") int n, @RequestParam("orderby") String field, @RequestParam("sort") String sort) {
        if (field == null || ( field.compareTo("note") != 0  && field.compareTo("id") != 0))
            throw new FeedbackFiltersException("The field " + field + " does not exists in Feedback ... ");
        if (sort == null || ( sort.compareTo("acs") != 0 && sort.compareTo("desc") != 0))
            throw new FeedbackFiltersException(sort + " is an invalid sort direction, please enter acs or desc");

        return  feedbackService.getListOrdored(this.getFeedbackOrdered(n), field, sort);
    }

    //Get Feedbacks with param
    @ApiOperation(value = "Get feedback(s) based on numbers of feedback wanted")
    @RequestMapping(method = RequestMethod.GET, value = "feedbacks", params = {"search"})
    public List<Feedback> getFeedbackOrdered(@RequestParam("search") int n) {
        Pageable pageable = PageRequest.of(0, n, Sort.by("id").descending());

        return feedbackDao.findAll(pageable).getContent();
    }


}
