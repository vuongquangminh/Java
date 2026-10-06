package chandanv.local.chandanv.modules.users.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import chandanv.local.chandanv.modules.users.services.impl.UserService;
import chandanv.local.chandanv.modules.users.services.interfaces.UserServiceInterface;

import org.springframework.web.bind.annotation.RequestBody;

import chandanv.local.chandanv.modules.users.dtos.LoginRequest;
import chandanv.local.chandanv.modules.users.dtos.LoginResponse;


@RestController 
@RequestMapping ("v1/auth")
public class AuthController {

    private final UserServiceInterface userService;

    public AuthController(
        UserServiceInterface userService
    ) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest Loginrequest) {
        
        LoginResponse auth = userService.login(Loginrequest);

        return ResponseEntity.ok(auth);
    }
    
}
