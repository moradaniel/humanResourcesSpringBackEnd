package org.humanResources.security.auth.jwt.verifier;

/**
 * 
 * @author vladimir.stankovic
 *
 */
public interface TokenVerifier {
    public boolean verify(String jti);
}
