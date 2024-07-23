package zerobase.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterUser {

    private String userId;
    private String name;
    private String phone;
    private String password;
}
