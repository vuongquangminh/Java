package chandanv.local.chandanv.cronjob;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import chandanv.local.chandanv.modules.users.repositories.RefreshTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

@Service
public class RefreshTokenClean {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    private static final Logger logger = LoggerFactory.getLogger(BlacklistTokenClean.class);

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupExpiredTokens() {
        LocalDateTime currentDataTime = LocalDateTime.now();
        int deletedCount = refreshTokenRepository.deleteByExpiryDateBefore(currentDataTime);
        logger.info("Da xoa: " + deletedCount + " token");
    }
}
