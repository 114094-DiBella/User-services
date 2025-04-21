package tesis.userservices.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long telephone;
    private Long numberDoc;
    private LocalDateTime birthday;
    private String imageUrl;

}
