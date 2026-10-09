package chandanv.local.chandanv.helpers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import chandanv.local.chandanv.resources.ErrorResource;

import java.util.Map;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException exception) {
        // Handle validation errors
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorResource errorResource = new ErrorResource("Co van de xay ra trong qua trinh kiem tra du lieu", errors);

        return new ResponseEntity<>(errorResource, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
