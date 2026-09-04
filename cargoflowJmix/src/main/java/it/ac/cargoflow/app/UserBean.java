package it.ac.cargoflow.app;

import io.jmix.core.security.CurrentAuthentication;
import it.ac.cargoflow.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class UserBean {
    @Autowired
    private CurrentAuthentication currAuth;

    public Boolean isFullAccess(){
        return this.isRole("system-full-access");
    }

    private Boolean isRole(String roleCode){
        User currentUser = (User) this.currAuth.getUser();
        return currentUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(auth -> auth.equals("ROLE_"+roleCode));
    }
}