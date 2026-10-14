package chandanv.local.chandanv.modules.users.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chandanv.local.chandanv.modules.users.entities.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    boolean existsByRefreshToken(String refreshToken);

    Optional<RefreshToken> findByRefreshToken(String refreshToken); 
    Optional<RefreshToken> findByUserId(Long userId); 

}
