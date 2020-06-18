package ro.mpp.core.Domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "app_user")
@Data
public class User extends BaseEntity<Integer> {
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;
}
