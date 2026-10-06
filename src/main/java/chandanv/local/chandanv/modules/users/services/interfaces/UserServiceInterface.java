package chandanv.local.chandanv.modules.users.services.interfaces;
import chandanv.local.chandanv.modules.users.dtos.LoginResponse;
import chandanv.local.chandanv.modules.users.dtos.LoginRequest;


public interface UserServiceInterface {
    LoginResponse login(LoginRequest request);
}
