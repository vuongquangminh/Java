package chandanv.local.chandanv.modules.users.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import chandanv.local.chandanv.modules.users.entities.BlacklistedToken;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {

    boolean existsByToken(String token);
}
