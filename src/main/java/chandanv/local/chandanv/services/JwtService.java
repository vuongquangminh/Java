package chandanv.local.chandanv.services;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chandanv.local.chandanv.config.JwtConfig;
import chandanv.local.chandanv.modules.users.entities.RefreshToken;
import chandanv.local.chandanv.modules.users.repositories.BlacklistedTokenRepository;
import chandanv.local.chandanv.modules.users.repositories.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final JwtConfig jwtConfig;
    private final Key key;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.key = Keys.hmacShaKeyFor(Base64.getEncoder().encode(jwtConfig.getSecretKey().getBytes()));
    }

    public String generateToken(Long userId, String email) {
        logger.info("generating...");
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpirationTime());

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .setIssuer(jwtConfig.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(Long userId, String email) {
        logger.info("Generating refreshtoken....");
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getRefreshTokenExpirationTime());

        String refreshToken = Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .setIssuer(jwtConfig.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        LocalDateTime localExpiryDate = expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        
        Optional<RefreshToken> optionalRefreshToken = refreshTokenRepository.findByUserId(userId);

        if(optionalRefreshToken.isPresent()) {
            RefreshToken dBRefreshToken = optionalRefreshToken.get();
            dBRefreshToken.setRefreshToken(refreshToken);
            dBRefreshToken.setExpiryDate(localExpiryDate);
            refreshTokenRepository.save(dBRefreshToken);

        }else{

            RefreshToken insertToken = new RefreshToken();
            insertToken.setRefreshToken(refreshToken);
            insertToken.setExpiryDate(localExpiryDate);
            insertToken.setUserId(userId);
    
            refreshTokenRepository.save(insertToken);
            
        }
        
        return refreshToken;
    }

    public String getUserIdFromJwt(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getEmailFromJwt(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.get("email", String.class);

    }

    public boolean isTokenFormatValid(String token) {
        try {

            String[] tokenParts = token.split("\\.");

            return tokenParts.length == 3;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean isSignatureValid(String token) {
        try {
            // logger.info(token);
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Key getSigningKey() {
        byte[] keyBytes = jwtConfig.getSecretKey().getBytes();
        return Keys.hmacShaKeyFor(Base64.getEncoder().encode(keyBytes));
    }

    public boolean isTokenExpired(String token) {
        try {

            Claims claims = getAllClaimsFromToken(token);
            if(claims != null){
                return claims.getExpiration().after(new Date());

            }else{
                return true;
            }

        } catch(Exception e) {
            return false;
        }
    }

    public boolean isIssuerToken(String token) {
        try {
            String tokenIssuer = getClaimFromToken(token, Claims::getIssuer);
            return jwtConfig.getIssuer().equals(tokenIssuer);
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    public Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
        } catch (ExpiredJwtException e) {
            return null;
        }
    }

    public boolean isBlacklistedToken(String token) {
        return blacklistedTokenRepository.existsByToken(token);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public boolean isRefreshTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(token).orElseThrow(() -> new RuntimeException("RefreshToken khong ton tai"));

            final Date expiration = getClaimFromToken(refreshToken.getRefreshToken(), Claims::getExpiration);

            return expiration.after(new Date());

        } catch (Exception e) {
            return false;
        }
    }

}
