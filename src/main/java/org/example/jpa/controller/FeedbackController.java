package org.example.jpa.controller;

import org.example.jpa.exception.ResourceNotFoundException;
import org.example.jpa.model.Feedback;
import org.example.jpa.repository.CustomerRepository;
import org.example.jpa.service.CustomerService;
import org.example.jpa.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public ResponseEntity<Object> allFeedback() {
        return new ResponseEntity<>(feedbackService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Object> saveFeedback(@PathVariable("id") Long customerId, @RequestBody Feedback feedback) {

        Feedback checkFeedback = customerService.findById(customerId).map(_customer -> {
            Feedback _feedback = new Feedback(_customer, feedback.getDescription());
            return feedbackService.save(_feedback);
        }).orElseThrow(() -> new ResourceNotFoundException());

        //if (checkFeedback.isEmpty())
        //return new ResponseEntity<>("Feedback not created", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(checkFeedback, HttpStatus.CREATED);
    }

    @PutMapping("/{id")
    public ResponseEntity<Object> updateFeedback(@PathVariable("id") Long feedbackId, @RequestBody Feedback feedback) {
        Feedback checkFeedback = feedbackService.findById(feedbackId).map(_feedback -> {
            _feedback.setDescription(feedback.getDescription());
            return feedbackService.save(_feedback);
        }).orElseThrow(() -> new ResourceNotFoundException());

        //if (checkFeedback.isEmpty())
        //return new ResponseEntity<>("Feedback not updated", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(checkFeedback, HttpStatus.OK);


    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> deleteFeedback(@PathVariable("id") Long feedbackId) {
        Optional<Feedback> checkFeedback = feedbackService.findById(feedbackId).map(_feedback -> {
            feedbackService.deleteById(_feedback.getId());
            return _feedback;
        });
        if (checkFeedback.isEmpty())
            return new ResponseEntity<>("Feedback not deleted", HttpStatus.BAD_REQUEST);

        String response = String.format("%s feedback deleted successfully", checkFeedback.get().getDescription());
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/count")
    public ResponseEntity<Object> countFeedback(){

        long count = feedbackService.count();

        if(count <= 0)
            return new ResponseEntity<>("No Feedback", HttpStatus.NOT_FOUND);

        Map<String, Object> totalFeedback = new HashMap<>();
        totalFeedback.put("total", count);

        return new ResponseEntity<>(totalFeedback, HttpStatus.OK);
    }

}


