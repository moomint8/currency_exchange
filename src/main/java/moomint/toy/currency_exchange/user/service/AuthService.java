package moomint.toy.currency_exchange.user.service;

import moomint.toy.currency_exchange.common.Exception.DuplicateException;
import moomint.toy.currency_exchange.user.dto.SignupDTO;

public interface AuthService {
    String signUp(SignupDTO userInfo) throws DuplicateException;
}