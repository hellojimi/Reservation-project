package zerobase.reservation.dto;

import lombok.*;
import zerobase.reservation.domain.UserEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUser {

    private String userId;
    private String name;
    private String phone;
    private String password;

    public RegisterUser fromEntity(UserEntity userEntity) {
        return RegisterUser.builder()
                .userId(userEntity.getId())
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .phone(userEntity.getPhone())
                .build();
    }
}
