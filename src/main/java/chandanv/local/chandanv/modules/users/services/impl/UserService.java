package chandanv.local.chandanv.modules.users.services.impl;
import org.springframework.stereotype.Service;

import chandanv.local.chandanv.modules.users.requests.LoginRequest;
import chandanv.local.chandanv.modules.users.resources.LoginResource;
import chandanv.local.chandanv.modules.users.resources.UserResource;
import chandanv.local.chandanv.modules.users.services.interfaces.UserServiceInterface;
import chandanv.local.chandanv.services.BaseService;


@Service
public class UserService extends BaseService implements UserServiceInterface {

    @Override 
    public LoginResource login(LoginRequest request) {
        try {
            // String email = request.getEmail();
            // String password = request.getPassword();
            
            String token = "random_token";
            UserResource userDTO = new UserResource(1L, "database_email@gmail.com");
            return new LoginResource(token, userDTO);
            
        } catch (Exception e) {
            throw new RuntimeException("Co van de xay ra: " + e.getMessage(), e);
        }
    }
}
