package com.larry.meetingroomreservation.domain.entity.support;

import java.util.Arrays;
import java.util.List;

import static com.larry.meetingroomreservation.domain.entity.support.RoleName.Scope.SCOPE_ADMIN;
import static com.larry.meetingroomreservation.domain.entity.support.RoleName.Scope.SCOPE_USER;

public enum RoleName {

    ROLE_ADMIN(Arrays.asList(SCOPE_ADMIN, SCOPE_USER)),
    ROLE_USER(Arrays.asList(SCOPE_USER));

    List<Scope> scopes;

    RoleName (List<Scope> scopes) {
        this.scopes = scopes;
    }

    public List<Scope> getScopes() {
        return scopes;
    }

    public enum Scope {
        SCOPE_ADMIN, SCOPE_USER
    }
}
