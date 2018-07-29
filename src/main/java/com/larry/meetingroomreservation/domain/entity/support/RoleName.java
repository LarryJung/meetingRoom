package com.larry.meetingroomreservation.domain.entity.support;

import java.util.Arrays;
import java.util.List;

import static com.larry.meetingroomreservation.domain.entity.support.RoleName.Scope.ADMIN;
import static com.larry.meetingroomreservation.domain.entity.support.RoleName.Scope.USER;

public enum RoleName {

    ROLE_ADMIN(Arrays.asList(ADMIN, USER)),
    ROLE_USER(Arrays.asList(USER));

    List<Scope> scopes;

    RoleName (List<Scope> scopes) {
        this.scopes = scopes;
    }

    public List<Scope> getScopes() {
        return scopes;
    }

    public enum Scope {
        ADMIN, USER
    }
}
