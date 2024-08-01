package zerobase.reservation.dto;

import lombok.Data;

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
    }
}
