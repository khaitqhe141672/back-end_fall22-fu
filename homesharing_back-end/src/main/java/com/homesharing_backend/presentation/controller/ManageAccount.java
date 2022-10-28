package com.homesharing_backend.presentation.controller;

import com.homesharing_backend.service.ManageAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/manage-account")
public class ManageAccount {

    @Autowired
    private ManageAccountService manageAccountService;

    @GetMapping("/view-account-host")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> viewAccountHost(@RequestParam("index-page") int indexPage) {
        return manageAccountService.viewAccountHost(indexPage);
    }

    @GetMapping("/view-account-customer")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> viewAccountCustomer(@RequestParam("index-page") int indexPage) {
        return manageAccountService.viewAccountCustomer(indexPage);
    }

    @GetMapping("/get-one-account-customer")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getOneAccountCustomerByAdmin(@RequestParam("customer-id") Long customerID) {
        return manageAccountService.getOneAccountCustomerByAdmin(customerID);
    }

    @GetMapping("/get-one-account-host")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getOneAccountHostByAdmin(@RequestParam("host-id") Long hostID) {
        return manageAccountService.getOneAccountHostByAdmin(hostID);
    }
}
