package chandanv.local.chandanv.modules.users.resources;

public class UserResource {
    private final Long id;
    private final String email;

    public UserResource(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
