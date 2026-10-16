package chandanv.local.chandanv.modules.users.services.impl;

import chandanv.local.chandanv.modules.users.services.interfaces.UserCatalogueServiceInterface;
import chandanv.local.chandanv.services.BaseService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import chandanv.local.chandanv.modules.users.requests.UserCatalogue.StoreRequest;
import chandanv.local.chandanv.modules.users.entities.UserCatalogue;
import chandanv.local.chandanv.modules.users.repositories.UserCatalogueRepository;
import org.springframework.transaction.annotation.Transactional;
import chandanv.local.chandanv.modules.users.requests.UserCatalogue.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;


@Service
@Transactional
public class UserCatalogueService  extends BaseService implements UserCatalogueServiceInterface{


    @Autowired
    private UserCatalogueRepository userCatalogueRepository;


    @Override
    public UserCatalogue create(StoreRequest request){

        try{

            UserCatalogue payload = UserCatalogue.builder()
                .name(request.getName())
                .publish(request.getPublish())
                .build();
    
            return userCatalogueRepository.save(payload);
        } catch (Exception e) {
            throw new RuntimeException("Trnascation failed: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public UserCatalogue update(Long id, UpdateRequest request){

        UserCatalogue userCatalogue = userCatalogueRepository.findById(id)
            .orElseThrow(()-> new EntityNotFoundException("Nhom thanh vien khong ton tai"));

        UserCatalogue payload = userCatalogue.toBuilder()
            .name(request.getName())
            .publish(request.getPublish())
            .build();
        return userCatalogueRepository.save(payload);
    }
}
