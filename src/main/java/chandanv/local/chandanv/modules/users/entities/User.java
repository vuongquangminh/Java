package chandanv.local.chandanv.modules.users.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import lombok.Data;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_catalogue_id")
    private Long userCatalogueId;

    private String name;
    private String email;
    private String password;
    private String phone;
    private String image;
    private String address;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreated() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdated() {
        updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserCatalogueId() {
        return userCatalogueId;
    }

    public void setUserCatalogueId(Long userCatalogueId) {
        this.userCatalogueId = userCatalogueId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String phone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String image() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String address() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public User() {

    }

    public User(String name , String email, String password, Long userCatalogueId, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userCatalogueId = userCatalogueId;
        this.phone = phone;

    }

}
