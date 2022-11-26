package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.service.FollowAndFavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/follow-favourite")
public class FollowAndFavouriteController {

    @Autowired
    private FollowAndFavouriteService followAndFavouriteService;

    @PostMapping("/create-follow")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> createFollowHostByCustomer(@RequestParam("host-id") Long hostID) {
        return followAndFavouriteService.followHostByCustomer(hostID);
    }

    @PutMapping("/edit-follow")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> editFollowHostByCustomer(@RequestParam("follow-host-id") Long followHostID) {
        return followAndFavouriteService.editFollowHostByCustomer(followHostID);
    }

    @PostMapping("/create-favourite")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> createFavouritePostByCustomer(@RequestParam("post-id") Long postID) {
        return followAndFavouriteService.createFavouritePostByCustomer(postID);
    }

    @PutMapping("/edit-favourite")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<?> editFavouritePostByCustomer(@RequestParam("favourite-post-id") Long favouritePostID) {
        return followAndFavouriteService.editFavouritePostByCustomer(favouritePostID);
    }
}
