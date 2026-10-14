package chandanv.local.chandanv.modules.users.requests;

import lombok.*;
import jakarta.validation.constraints.NotBlank; 

@Data
public class RefreshTokenRequest {

    @NotBlank(message = "RefreshToken khong duoc de trong")
    private String refreshToken;

}
