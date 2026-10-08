package chandanv.local.chandanv.modules.users.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import chandanv.local.chandanv.modules.users.entities.User;
import chandanv.local.chandanv.modules.users.resources.UserResource;
import chandanv.local.chandanv.modules.users.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import chandanv.local.chandanv.resources.SuccessResource;

@RestController
@RequestMapping("api/v1")
public class UserController {
    

    @Autowired 
    private UserRepository userRepository;

    
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        String email = "chandanv1010@gmail.com";
        
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User khong ton tai"));

        UserResource userResource = new UserResource(user.getId(), user.getEmail(), user.getName());

        SuccessResource<UserResource> response = new SuccessResource<UserResource>("Success", userResource);

        return ResponseEntity.ok(response);

    }
}
