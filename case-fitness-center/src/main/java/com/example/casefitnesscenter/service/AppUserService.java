package com.example.casefitnesscenter.service;


import com.example.casefitnesscenter.common.BaseResponse;
import com.example.casefitnesscenter.entity.dto.PasswordRequest;
import com.example.casefitnesscenter.entity.model.AppUser;
import com.example.casefitnesscenter.entity.model.Member;
import jakarta.servlet.http.HttpServletRequest;


//public interface AppUserService extends UserDetailsService {
public interface AppUserService  {
    String createNewUser(Member member, String password);

    BaseResponse<Void> refreshToken(HttpServletRequest request);

    BaseResponse<Void> forgetPassword(String email);

    BaseResponse<Void> resetPassword(String email, String otpCode, PasswordRequest request);

    BaseResponse<Void> verifyOTP(String username, String otpCode);

    AppUser getAppUserById(String userId);

    void updateAppUser(AppUser appUser);
}
