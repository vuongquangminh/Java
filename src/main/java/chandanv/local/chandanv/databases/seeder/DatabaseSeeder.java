package chandanv.local.chandanv.databases.seeder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;



import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component 
public class DatabaseSeeder implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override 
    public void run(String... args) throws Exception {

        if(isTableEmpty()) {
            String passwordEncoded = passwordEncoder.encode("password");
            entityManager.createNativeQuery("INSERT INTO users (name, email, password, user_catalogue_id, phone) VALUES (?,?,?,?,?)")
            .setParameter(1, "Nam Hoang Van")
            .setParameter(2, "chandanv1010@example.com")
            .setParameter(3, passwordEncoded)
            .setParameter(4, 1)
            .setParameter(5, "0869952231")
            .executeUpdate();
            System.out.println("password: " + passwordEncoded);
        }

    }

    private boolean isTableEmpty() {
        Long count = (Long) entityManager.createQuery("SELECT COUNT(id) FROM User").getSingleResult();
        return count == 0;
    }
}
