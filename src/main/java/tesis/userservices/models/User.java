package tesis.userservices.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long telephone;
    private Long numberDoc;
    private LocalDateTime birthday;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Rol role;
    private Boolean status;

    public <T> User(String email, String password, List<T> roleUser) {
    }
}
