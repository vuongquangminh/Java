package chandanv.local.chandanv.modules.users.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chandanv.local.chandanv.modules.users.requests.LoginRequest;
import chandanv.local.chandanv.modules.users.resources.LoginResource;
import chandanv.local.chandanv.modules.users.services.interfaces.UserServiceInterface;


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
    public ResponseEntity<LoginResource> login(@RequestBody LoginRequest Loginrequest) {
        
        LoginResource auth = userService.login(Loginrequest);

        return ResponseEntity.ok(auth);
    }
    
}
