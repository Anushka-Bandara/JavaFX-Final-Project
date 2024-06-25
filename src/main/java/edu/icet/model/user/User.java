package edu.icet.model.user;

import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    String userId;
    String name;
    String email;
    String Password;
    String role;
}
