package com.romands.Entity;

import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public enum UserRoles {
    ADMIN("admin"),
    USER("user");

    String role;
    UserRoles(String role){
        this.role = role;
    }

}
