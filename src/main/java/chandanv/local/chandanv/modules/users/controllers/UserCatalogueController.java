package chandanv.local.chandanv.modules.users.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

import chandanv.local.chandanv.modules.users.entities.UserCatalogue;
import chandanv.local.chandanv.modules.users.requests.UserCatalogue.StoreRequest;
import chandanv.local.chandanv.modules.users.requests.UserCatalogue.UpdateRequest;
import chandanv.local.chandanv.modules.users.resources.UserCatalogueResource;
import chandanv.local.chandanv.modules.users.services.interfaces.UserCatalogueServiceInterface;
import org.springframework.validation.annotation.Validated;
import chandanv.local.chandanv.resources.ApiResource;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;




@Validated
@RestController
@RequestMapping("api/v1")
public class UserCatalogueController {

    private static final Logger logger = LoggerFactory.getLogger(UserCatalogueController.class);
    private final UserCatalogueServiceInterface userCatalogueService;


    public UserCatalogueController(UserCatalogueServiceInterface userCatalogueService){
        this.userCatalogueService= userCatalogueService;
    }

    @PostMapping("/user_catalogues")
    public ResponseEntity<?> store(@Valid @RequestBody StoreRequest request) {
        logger.info("Methoed Running....!");

        UserCatalogue userCatalogue = userCatalogueService.create(request);
        UserCatalogueResource userCatalogueResource = UserCatalogueResource.builder()
                .id(userCatalogue.getId())
                .name(userCatalogue.getName())
                .publish(userCatalogue.getPublish()) 
                .build();

        ApiResource<UserCatalogueResource> response = ApiResource.ok(userCatalogueResource, "Them moi ban ghi thanh cong");
        return ResponseEntity.ok(response);
    }
    @PutMapping("/user_catalogues/{id}")
    public ResponseEntity<?> update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateRequest request
    ) {

        try{
            UserCatalogue userCatalogue = userCatalogueService.update(id, request);
            UserCatalogueResource userCatalogueResource = UserCatalogueResource.builder()
                .id(userCatalogue.getId())
                .name(userCatalogue.getName())
                .publish(userCatalogue.getPublish()) 
                .build();
            ApiResource<UserCatalogueResource> response = ApiResource.ok(userCatalogueResource, "Cap nhat ban ghi thanh cong");

            return ResponseEntity.ok(response);

        }catch(EntityNotFoundException e){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResource.error("NOT_FOUND", e.getMessage(),  HttpStatus.BAD_REQUEST)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResource.error("INTERNAL_SERVER_ERROR", "Co loi xay ra trong qua tirnh cap nhat", HttpStatus.NOT_FOUND)
            );
        }
    }


}
