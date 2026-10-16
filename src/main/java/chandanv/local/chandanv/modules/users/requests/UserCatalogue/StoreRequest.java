package chandanv.local.chandanv.modules.users.requests.UserCatalogue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank; 
import jakarta.validation.constraints.NotNull; 
import lombok.Data;

@Data
public class StoreRequest {
    @NotBlank(message = "Ten nhom thanh vien khong duoc de trong")
    private String name;

    @NotNull(message = "Trang thai khong duoc de trong")
    @Min(value = 0, message = "Gia tri trang thai phai lon hon hoac bang 0")
    @Max(value = 2, message = "Gia tri trang thai nho lon hon hoac bang 2")
    private Integer publish;

    
}
