package chandanv.local.chandanv.modules.users.services.impl;

import java.time.ZoneId;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chandanv.local.chandanv.modules.users.entities.BlacklistedToken;
import chandanv.local.chandanv.modules.users.repositories.BlacklistedTokenRepository;
import chandanv.local.chandanv.modules.users.requests.BlacklistTokenRequest;
import chandanv.local.chandanv.services.JwtService;
import io.jsonwebtoken.Claims;
import chandanv.local.chandanv.resources.MessageResource;

@Service
public class BlacklistService {

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    @Autowired
    private JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(BlacklistService.class);

    public Object create(BlacklistTokenRequest request) {
        try {

            if (blacklistedTokenRepository.existsByToken(request.getToken())) {
                return new MessageResource("token da ton tai trong blacklist");
            }
            Claims claims = jwtService.getAllClaimsFromToken(request.getToken());
            Long userId = Long.valueOf(claims.getSubject());

            Date expiryDate = claims.getExpiration();

            BlacklistedToken blacklistedToken = new BlacklistedToken();
            blacklistedToken.setToken(request.getToken());
            blacklistedToken.setUserId(userId);
            blacklistedToken.setExpriryDate(expiryDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            blacklistedTokenRepository.save(blacklistedToken);

            logger.info("them Token vao danh sach blacklist thanh cong ");

            return new MessageResource("them token vao danh sach blacklist thanh cong");

        } catch (Exception e) {

            return new MessageResource("Network Error! " + e.getMessage());

        }

    }

}
