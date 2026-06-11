package com.vitoria.demo_api.jwt;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;



public class JwtUserDetails extends User {

    private com.vitoria.demo_api.domain.User user;



    public JwtUserDetails(com.vitoria.demo_api.domain.User user) {
        super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public Long getId() {
        return this.user.getId();
    }

    public String getRole(){
        return this.user.getRole().name();
    }
}
