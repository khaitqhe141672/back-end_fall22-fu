package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.service.LikesDislikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/like-dislike")
public class LikesDislikeController {

    @Autowired
    private LikesDislikesService likesDislikesService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> createLikeOrDislikeRateByCustomer(@RequestParam("rate-id") Long rateID,
                                                               @RequestParam("type") int type) {
        return likesDislikesService.createLikeOrDislikeRateByCustomer(rateID, type);
    }

    @PutMapping("/edit")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> editLikeOrDislikeRateByCustomer(@RequestParam("like-dislike-id") Long likeDislikeID,
                                                             @RequestParam("type") int type) {
        return likesDislikesService.editLikeOrDislikeRateByCustomer(likeDislikeID, type);
    }
}
