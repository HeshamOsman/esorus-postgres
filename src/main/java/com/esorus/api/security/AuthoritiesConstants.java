package com.esorus.api.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";
    
    public static final String PROFESSIONAL_BUYER = "ROLE_PROFESSIONAL_BUYER";
    
    public static final String SUPPLIER = "ROLE_SUPPLIER";

    private AuthoritiesConstants() {
    }
}
