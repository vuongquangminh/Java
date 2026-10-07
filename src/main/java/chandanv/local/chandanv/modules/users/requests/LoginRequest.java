package chandanv.local.chandanv.modules.users.requests;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank; 

public class LoginRequest {
    @Email(message = "Email khong dung dinh dang")
    @NotBlank(message = "Email khong duoc de trong")
    private String email;

    @NotBlank (message = "Password khong duoc de trong")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
