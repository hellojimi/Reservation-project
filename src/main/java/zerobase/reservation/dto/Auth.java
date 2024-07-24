package zerobase.reservation.dto;

import lombok.*;
import zerobase.reservation.domain.UserEntity;

public class Auth {

    @Data
    public static class login {
        private String id;
        private String password;
    }

    @Data
    public static class join {
        private String userId;
        private String name;
        private String phone;
        private String password;

//        public UserEntity fromEntity(UserEntity userEntity) {
//            return UserEntity.builder()
//                    .id(userEntity.getId())
//                    .name(userEntity.getName())
//                    .password(userEntity.getPassword())
//                    .phone(userEntity.getPhone())
//                    .build();
//        }
    }
}
