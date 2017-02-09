package org.humanResources.security.model;

/**
 *
 * @author vladimir.stankovic
 *
 */
public enum Scopes {
    REFRESH_TOKEN;

    public String authority() {
        return "ROLE_" + this.name();
    }
}