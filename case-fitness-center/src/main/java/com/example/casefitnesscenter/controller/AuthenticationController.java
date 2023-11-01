package com.example.casefitnesscenter.controller;

import com.example.casefitnesscenter.common.BaseResponse;
import com.example.casefitnesscenter.constant.ApiUrlConstant;
import com.example.casefitnesscenter.entity.dto.MemberDto;
import com.example.casefitnesscenter.entity.dto.MembershipDetailsRequest;
import com.example.casefitnesscenter.entity.dto.PasswordRequest;
import com.example.casefitnesscenter.service.AppUserService;
import com.example.casefitnesscenter.service.MembershipService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ApiUrlConstant.AUTHENTICATION_URL)
public class AuthenticationController {
    private final AppUserService userService;
    private final MembershipService membershipService;

    public AuthenticationController(AppUserService userService, MembershipService membershipService) {
        this.userService = userService;
        this.membershipService = membershipService;
    }

    @PostMapping("/signup")
    public ResponseEntity<BaseResponse<MemberDto>> registerMembership(@Valid @RequestBody MembershipDetailsRequest request) {
        BaseResponse<MemberDto> baseResponse = membershipService.registration(request);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/refresh")
    public ResponseEntity<BaseResponse<Void>> refreshToken(HttpServletRequest request) {
        BaseResponse<Void> baseResponse = userService.refreshToken(request);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/verify-registration")
    public ResponseEntity<BaseResponse<Void>> verifyEmail(@RequestParam String username,
                                                          @RequestParam(name = "otp_code") String otpCode) {
        BaseResponse<Void> response = userService.verifyOTP(username, otpCode);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<BaseResponse<Void>> forgetPassword(@Valid @RequestParam String email) {
        BaseResponse<Void> baseResponse = userService.forgetPassword(email);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/reset-password/{email}")
    public ResponseEntity<BaseResponse<Void>> resetPassword(
            @PathVariable String email,
            @RequestParam(name = "otp_code") String otpCode,
            @Valid @RequestBody PasswordRequest request) {
        BaseResponse<Void> baseResponse = userService.resetPassword(email, otpCode, request);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
