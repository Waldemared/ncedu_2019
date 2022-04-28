package com.ncedu.nc_edu.controllers;

import com.ncedu.nc_edu.dto.assemblers.ReviewAssembler;
import com.ncedu.nc_edu.dto.resources.ReviewResource;
import com.ncedu.nc_edu.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewAssembler reviewAssembler;

    public ReviewController(@Autowired ReviewService reviewService, @Autowired ReviewAssembler reviewAssembler) {
        this.reviewService = reviewService;
        this.reviewAssembler = reviewAssembler;
    }

    @GetMapping(value = "reviews/{reviewId}")
    public ResponseEntity<RepresentationModel<ReviewResource>> getById(@PathVariable UUID reviewId) {
        return ResponseEntity.ok(reviewAssembler.toModel(reviewService.findReviewById(reviewId)));
    }

    @PutMapping(value = "/reviews/{reviewId}")
    public ResponseEntity<RepresentationModel<ReviewResource>> updateReview(@PathVariable UUID reviewId,
                                                                            @RequestBody @Valid ReviewResource reviewResource) {
        ReviewResource resource = reviewAssembler.toModel(reviewService.updateReview(reviewId, reviewResource));
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping(value = "/reviews/{reviewId}")
    public ResponseEntity<RepresentationModel<ReviewResource>> deleteReview(@PathVariable UUID reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
