package com.example.casefitnesscenter.service.impl;


import com.example.casefitnesscenter.common.BaseResponse;
import com.example.casefitnesscenter.common.exception.AppRuntimeException;
import com.example.casefitnesscenter.common.exception.ResourceNotFoundException;
import com.example.casefitnesscenter.entity.StatusSubscribe;
import com.example.casefitnesscenter.entity.dto.*;
import com.example.casefitnesscenter.entity.mapper.MemberMapper;
import com.example.casefitnesscenter.entity.model.*;
import com.example.casefitnesscenter.entity.repository.MemberRepository;
import com.example.casefitnesscenter.security.SecurityUtils;
import com.example.casefitnesscenter.service.AppUserService;
import com.example.casefitnesscenter.service.MembershipService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.example.casefitnesscenter.constant.AppFitnessConstant.messageFormat;
import static com.example.casefitnesscenter.constant.UserConstant.*;
import static com.example.casefitnesscenter.utils.DateConverter.MONTH_YEARS_FORMAT;
import static com.example.casefitnesscenter.utils.DateConverter.parseToDate;


@RequiredArgsConstructor
@Service
public class MembershipDetails implements MembershipService {
    private final AppUserService appUserService;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public BaseResponse<MemberDto> registration(MembershipDetailsRequest request) {
        assertNotDuplicateEmail(request.email());
        Member member = memberMapper.toEntity(request);
        Member persisted = memberRepository.save(member);
        String otpCode = appUserService.createNewUser(persisted, request.password());
        MemberDto mapper = memberMapper.toDto(persisted);

        String subject = messageFormat("Hello {}! Verification Your Email First", request.name().toUpperCase());
        emailService.sendEmailFromHtmlTemplate(request.email(),
                subject,
                "email-verification",
                verifyRegistrationContext(otpCode, "http://localhost"));



        return new BaseResponse<>(
                HttpStatus.CREATED.value(),
                String.format(SUCCESSFULLY_CREATE,mapper.name()),
                mapper
        );
    }

    @Override
    public Subscription findSubscriptionProgram(String memberId, String programId) {
        return memberRepository.findSubscriptionMember(memberId, programId)
                .stream()
                .filter(subscription -> subscription.getStatusSubscribe() == StatusSubscribe.SUBSCRIBE)
                .findFirst()
                .orElseThrow();
    }
//    @Override
//    public BaseResponse<List<SubscriptionResponse>> findSubscriptionsMember(String memberId) {
//        return null;
//        return new BaseResponse<>(
//                HttpStatus.CREATED.value(),
//                String.format(SUCCESSFULLY_CREATE, mapper.name()),
//                mapper
//        );
//    }

//    @Override
//    public BaseResponse<SubscriptionResponse> reSubscribeMember(String id) {
//        return null;
//
//    }

    @Override
    public BaseResponse<MemberDto> updateMember(String id, UpdateMemberDetailRequest request) {
        Member member = getMemberById(id);

        if (request.name() != null) {
            member.setName(request.name());
        }

        if (request.creditCard() != null) {
            CreditCard creditCard = member.getCreditCard();
            if (request.creditCard().cardNumber() != null) {
                creditCard.setCardNumber(request.creditCard().cardNumber());
            }

            if (request.creditCard().placeholder() != null) {
                creditCard.setPlaceholder(request.creditCard().placeholder());
            }
            if (request.creditCard().cvv() != null) {
                creditCard.setCvv(Integer.valueOf(request.creditCard().cvv()));
            }
            if (request.creditCard().expiredDate() != null) {
                Date date = parseToDate(request.creditCard().expiredDate(), MONTH_YEARS_FORMAT);
                creditCard.setExpiredDate(date);
            }
            member.setCreditCard(creditCard);
        }

        Member persisted = memberRepository.save(member);
        MemberDto mapper = memberMapper.toDto(persisted);

        return new BaseResponse<>(
                HttpStatus.CREATED.value(),
                String.format(SUCCESSFULLY_CREATE, mapper.name()),
                mapper
        );
    }

    @Override
    public BaseResponse<String> checkStatusMemberRegistration(String email) {
        AppUser appUser = this.userLoggedIn(email);
        StatusMembership statusMembership = memberRepository.findStatusMembership(appUser.getMember().getEmail());

        return new BaseResponse<>(statusMembership.getName());
       // return null;
    }

    @Override
    public BaseResponse<Void> changePassword(String id, PasswordRequest request) {
        Member member = this.getMemberById(id);
        AppUser appUser = this.userLoggedIn(member.getEmail());

        boolean matches = passwordEncoder.matches(request.oldPassword(), appUser.getPassword());
        if (!matches) {
            throw new AppRuntimeException("Password did not match");
        }

        boolean passwordNewMatchWithOldPassword = passwordEncoder.matches(request.newPassword(), appUser.getPassword());
        if (passwordNewMatchWithOldPassword) {
            throw new AppRuntimeException("Please enter another password!");
        }

        appUser.setPassword(passwordEncoder.encode(request.newPassword()));
        appUserService.updateAppUser(appUser);

        return new BaseResponse<>(HttpStatus.OK.value(), "Success to change your password");
       // return null;
    }

    @Override
    public BaseResponse<List<MemberDto>> findAllMember() {
        List<MemberDto> list = new ArrayList<>();
        for (Member creditCard : memberRepository.findAll()) {
            MemberDto dto = memberMapper.toDto(creditCard);
            list.add(dto);
        }
        return new BaseResponse<>(list);
    }

    @Override
    public Member getMemberById(String id) {
        String message = messageFormat(USER_NOT_FOUND_MSG, id);

        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(message));
    }

    private void assertNotDuplicateEmail(String email) {
        boolean present = memberRepository.existsByEmail(email);
        if (present) {
            throw new DataIntegrityViolationException(EMAIL_ALREADY_TAKEN);
        }
    }

    private Context verifyRegistrationContext(String otpCode, String verifyLink) {
        Map<String, Object> variableHtml = Map.of(
                "otp-code", otpCode,
                "verify-link", verifyLink);
        Context context = new Context();
        context.setVariables(variableHtml);
        return context;
    }

    private AppUser userLoggedIn(String username) {
        AppUser authenticatedUserDetails = SecurityUtils.getAuthenticatedUserDetails();
        if (!authenticatedUserDetails.getMember().getEmail().equals(username)) {
            throw new AppRuntimeException("Not your session. Please login again!");
        }

        return authenticatedUserDetails;
    }


}
