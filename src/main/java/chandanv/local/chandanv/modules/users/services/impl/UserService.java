package chandanv.local.chandanv.modules.users.services.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import chandanv.local.chandanv.modules.users.requests.LoginRequest;
import chandanv.local.chandanv.modules.users.resources.LoginResource;
import chandanv.local.chandanv.modules.users.resources.UserResource;
import chandanv.local.chandanv.modules.users.services.interfaces.UserServiceInterface;
import chandanv.local.chandanv.resources.ErrorResource;
import chandanv.local.chandanv.services.BaseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import chandanv.local.chandanv.modules.users.entities.User;
import chandanv.local.chandanv.modules.users.repositories.UserRepository;
import chandanv.local.chandanv.services.JwtService;
import chandanv.local.chandanv.resources.ApiResource;
import org.springframework.http.HttpStatus;




@Service
public class UserService extends BaseService implements UserServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.defaultExpiration}")
    private long defaultExpiration;

    @Override
    public Object authenticate(LoginRequest request) {
        try {
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("Email hoac mat khau khong chinh xac"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Email hoac mat khau khong chinh xac");
            }

            UserResource userResource = new UserResource(user.getId(), user.getEmail(), user.getName(), user.getPhone());
            String token = jwtService.generateToken(user.getId(), user.getEmail(), defaultExpiration);
            String refreshToken = jwtService.generateRefreshToken(user.getId(), user.getEmail());

            return new LoginResource(token, refreshToken, userResource);

        } catch (BadCredentialsException e) {
            logger.error("Loi xac thuc: {}", e.getMessage());

            return ApiResource.error("AUTH_ERROR", e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
