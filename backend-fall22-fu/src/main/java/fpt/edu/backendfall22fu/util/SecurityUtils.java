package fpt.edu.backendfall22fu.util;

import fpt.edu.backendfall22fu.security.services.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserDetailsImpl getPrincipal(){
        return (UserDetailsImpl) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
    }
}
