package chandanv.local.chandanv.modules.users.services.interfaces;
import chandanv.local.chandanv.modules.users.requests.LoginRequest;
import chandanv.local.chandanv.modules.users.resources.LoginResource;


public interface UserServiceInterface {
    LoginResource login(LoginRequest request);
}
