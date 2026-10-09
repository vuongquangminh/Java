package chandanv.local.chandanv.modules.users.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import org.springframework.validation.annotation.Validated;

import chandanv.local.chandanv.modules.users.requests.LoginRequest;
import chandanv.local.chandanv.modules.users.resources.LoginResource;
import chandanv.local.chandanv.modules.users.services.interfaces.UserServiceInterface;
import chandanv.local.chandanv.resources.ErrorResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import chandanv.local.chandanv.modules.users.requests.BlacklistTokenRequest;
import chandanv.local.chandanv.modules.users.services.impl.BlacklistService;
import chandanv.local.chandanv.resources.MessageResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestHeader;

@Validated
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserServiceInterface userService;
    private static final Logger logger = LoggerFactory.getLogger(BlacklistService.class);

    @Autowired
    private BlacklistService blacklistService;

    public AuthController(
            UserServiceInterface userService
    ) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {

        Object result = userService.authenticate(request);

        if (result instanceof LoginResource loginResource) {
            return ResponseEntity.ok(loginResource);
        }

        if (result instanceof ErrorResource errorResource) {
            return ResponseEntity.unprocessableEntity().body(errorResource);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Network Error");
    }

    @PostMapping("blacklist_tokens")
    public ResponseEntity<?> addTokenToBlackList(@Valid @RequestBody BlacklistTokenRequest request) {
        try {
            Object result = blacklistService.create(request);
            return ResponseEntity.ok(result); 
        
        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(new MessageResource("Network Error!"));
        }

    }
    @GetMapping("loggout")
    public ResponseEntity<?> loggout(@RequestHeader("Authorization") String bearerToken){
        try {
            String token = bearerToken.substring(7);
            BlacklistTokenRequest request = new BlacklistTokenRequest();
            request.setToken(token);

            Object message = blacklistService.create(request);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new MessageResource("Network Error!"));
        }
    }
}
