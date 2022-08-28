package fpt.edu.backendfall22fu.presentation.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/student")
    @PreAuthorize("hasRole('ROLE_STUDENT') or hasRole('ROLE_TEACHER') or hasRole(ROLE_ADMIN)")
    public String studentAccess() {
        return "Student Content.";
    }

    @GetMapping("/teacher")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public String teacherAccess() {
        return "Teacher Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminAccess() {
        return "Admin Content.";
    }


//    @DeleteMapping("/delete-file")
//    ResponseEntity<?> deleteFileGCS(@RequestParam(value = "fileName") String fileName) {
//        return ResponseEntity.ok(gcsService.delete(fileName));
//    }
}
