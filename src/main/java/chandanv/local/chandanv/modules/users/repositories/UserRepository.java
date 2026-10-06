package chandanv.local.chandanv.modules.users.repositories;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import chandanv.local.chandanv.modules.users.entities.User;


@Repository 
public interface UserRepository extends JpaRepository<User, Long> {

}
