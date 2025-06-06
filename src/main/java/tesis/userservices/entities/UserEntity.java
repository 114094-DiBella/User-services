package tesis.userservices.entities;
import jakarta.persistence.*;
import lombok.Data;
import tesis.userservices.models.Rol;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.UUID;
@Table(name = "users")
@Entity
@Data

public class UserEntity {

    @Id
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Long telephone;

    @Column(name = "number_doc")
    private Long numberDoc;

    @Column
    private LocalDateTime birthday;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column
    @Enumerated(EnumType.STRING)
    private Rol role;

    @Column
    private Boolean status;

}
