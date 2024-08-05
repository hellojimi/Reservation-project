package zerobase.reservation.service;

import zerobase.reservation.domain.AccountEntity;
import zerobase.reservation.dto.Auth;

public interface AccountService {

    AccountEntity register(Auth.join join, String requestURI);

    // uuid로 이메일 인증 확인
    AccountEntity emailAuth(String uuid);

    // 계정 id로 정보 찾기
    AccountEntity getAccountInfo(String accountId);

}
