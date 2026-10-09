package chandanv.local.chandanv.modules.users.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import chandanv.local.chandanv.modules.users.entities.User;
import chandanv.local.chandanv.modules.users.resources.UserResource;
import chandanv.local.chandanv.modules.users.repositories.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import chandanv.local.chandanv.resources.SuccessResource;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User khong ton tai"));

        UserResource userResource = UserResource.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone()) 
                .build();

        SuccessResource<UserResource> response = new SuccessResource<>("SUCCESS", userResource);

        logger.info("Success!");
        return ResponseEntity.ok(response);

    }
}
