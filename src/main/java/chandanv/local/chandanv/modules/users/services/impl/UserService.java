package chandanv.local.chandanv.modules.users.services.impl;
import chandanv.local.chandanv.modules.users.controllers.AuthController;
import chandanv.local.chandanv.modules.users.services.interfaces.UserServiceInterface;
import chandanv.local.chandanv.services.BaseService;

import org.springframework.stereotype.Service;

import chandanv.local.chandanv.modules.users.dtos.LoginResponse;
import chandanv.local.chandanv.modules.users.dtos.LoginRequest;
import chandanv.local.chandanv.modules.users.dtos.UserDTO;


@Service
public class UserService extends BaseService implements UserServiceInterface {

    @Override 
    public LoginResponse login(LoginRequest request) {
        try {
            // String email = request.getEmail();
            // String password = request.getPassword();
            
            String token = "random_token";
            UserDTO userDTO = new UserDTO(1L, "database_email@gmail.com");
            return new LoginResponse(token, userDTO);
            
        } catch (Exception e) {
            throw new RuntimeException("Co van de xay ra: " + e.getMessage(), e);
        }
    }
}
