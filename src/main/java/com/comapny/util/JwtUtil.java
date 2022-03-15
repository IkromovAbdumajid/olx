package com.comapny.util;

import com.comapny.exception.ForbiddenException;
import com.comapny.exception.UnauthorizedException;
import com.comapny.enums.ProfileRole;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    private final static String secretKey="kalitso'z";
    private final static SignatureAlgorithm signWith= SignatureAlgorithm.HS256;
    private final static long time=System.currentTimeMillis() + (60 * 60 * 1000);
    private final static String issuer="olx.uz";
    private final static Date issuedAt=new Date();

    public static String createJwt(Integer id, ProfileRole role){
        JwtBuilder jwtBuilder= Jwts.builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(issuedAt)
                .signWith(signWith,secretKey)
                .setExpiration(new Date(time))
                .setIssuer(issuer)
                .claim("role",role.name());

        String jwt=jwtBuilder.compact();
        return jwt;
    }

    public static String createJwt(Integer id){
        JwtBuilder jwtBuilder= Jwts.builder()
                .setSubject(String.valueOf(id))
                .setIssuedAt(issuedAt)
                .signWith(signWith,secretKey)
                .setExpiration(new Date(time))
                .setIssuer(issuer);

        String jwt=jwtBuilder.compact();
        return jwt;
    }

    public static com.comapny.dto.profile.ProfileJwtDTO decodeJwt(String jwt) {
        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);

        Claims claims = (Claims) jws.getBody();
        String id = claims.getSubject();
        ProfileRole role = ProfileRole.valueOf((String) claims.get("role"));

        return new com.comapny.dto.profile.ProfileJwtDTO(Integer.parseInt(id), role);
    }

    public static Integer decodeJwtAndGetId(String jwt) {
        JwtParser jwtParser = Jwts.parser();

        jwtParser.setSigningKey(secretKey);
        Jws jws = jwtParser.parseClaimsJws(jwt);

        Claims claims = (Claims) jws.getBody();
        return Integer.valueOf(claims.getSubject());
    }

    public static Integer getCurrentUser(HttpServletRequest request) throws RuntimeException {
        Integer userId = (Integer) request.getAttribute("userId");
        if (userId == null) {
            throw new RuntimeException("METHOD NOT ALLOWED");
        }
        return userId;
    }

    public static com.comapny.dto.profile.ProfileJwtDTO getProfile(HttpServletRequest request, ProfileRole requiredRole) {
        com.comapny.dto.profile.ProfileJwtDTO jwtDTO = (com.comapny.dto.profile.ProfileJwtDTO) request.getAttribute("jwtDTO");
        if (jwtDTO == null) {
            throw new UnauthorizedException("Not authorized");
        }

        if (!jwtDTO.getRole().equals(requiredRole)) {
            throw new ForbiddenException("Forbidden exp");
        }
        return jwtDTO;
    }

    public static com.comapny.dto.profile.ProfileJwtDTO getProfile(HttpServletRequest request) {
        com.comapny.dto.profile.ProfileJwtDTO jwtDTO = (com.comapny.dto.profile.ProfileJwtDTO) request.getAttribute("jwtDTO");
        if (jwtDTO == null) {
            throw new RuntimeException("METHOD NOT ALLOWED");
        }
        return jwtDTO;
    }


}
