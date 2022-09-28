package com.homesharing_backend.util;

import com.homesharing_backend.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserDetailsImpl getPrincipal(){
        return (UserDetailsImpl) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
    }
}
