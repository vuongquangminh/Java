package chandanv.local.chandanv.modules.users.services.interfaces;

import chandanv.local.chandanv.modules.users.entities.UserCatalogue;
import chandanv.local.chandanv.modules.users.requests.UserCatalogue.StoreRequest;
import chandanv.local.chandanv.modules.users.requests.UserCatalogue.UpdateRequest;
import org.springframework.data.domain.Page;
import java.util.Map;


public interface UserCatalogueServiceInterface  {
    UserCatalogue create(StoreRequest request);
    UserCatalogue update(Long id, UpdateRequest request);
    Page<UserCatalogue> paginate(Map<String, String[]> parameters);
}
