package chandanv.local.chandanv.modules.users.repositories;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import chandanv.local.chandanv.modules.users.entities.UserCatalogue;


@Repository 
public interface UserCatalogueRepository extends JpaRepository<UserCatalogue, Long> {
        Optional<UserCatalogue> findById(Long id);

}
