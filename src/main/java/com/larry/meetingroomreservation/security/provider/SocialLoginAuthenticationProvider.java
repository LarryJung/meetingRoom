package com.larry.meetingroomreservation.security.provider;

import com.larry.meetingroomreservation.domain.entity.SocialProviders;
import com.larry.meetingroomreservation.domain.entity.User;
import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import com.larry.meetingroomreservation.domain.repository.UserRepository;
import com.larry.meetingroomreservation.security.dto.SocialLoginDto;
import com.larry.meetingroomreservation.security.social.SocialFetchService;
import com.larry.meetingroomreservation.security.social.SocialUserProperty;
import com.larry.meetingroomreservation.security.token.SocialPreAuthorizationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.UUID;

@Component
public class SocialLoginAuthenticationProvider implements AuthenticationProvider {

    private final Logger log = LoggerFactory.getLogger(SocialLoginAuthenticationProvider.class);

    @Autowired
    private UserRepository userRepository;

    @Resource(name = "socialFetchService")
    private SocialFetchService socialFetchService;

    @Override
    public Authentication authenticate(Authentication socialPreAuthentication) throws AuthenticationException {
        User user = getAccount(((SocialPreAuthorizationToken) socialPreAuthentication).getDto());
        return ((SocialPreAuthorizationToken) socialPreAuthentication).toPostToken(user);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SocialPreAuthorizationToken.class.isAssignableFrom(aClass);
    }

    private User getAccount(SocialLoginDto dto) {
        SocialUserProperty property = socialFetchService.getSocialUserInfo(dto);
        log.info("properties : {}", property.getUserNickname());
        String userId = property.getUserId();
        SocialProviders provider = dto.getProvider();
        log.info("userid / prrovider : {}, {}", userId, provider.getUserinfoEndpoint());
        return userRepository.findBySocialIdAndSocialProvider(userId, provider)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .userId("SOCIAL_USER")
                                .password(String.valueOf(UUID.randomUUID().getMostSignificantBits()))
                                .name(property.getUserNickname())
                                .email(property.getEmail())
                                .roleName(RoleName.ROLE_USER)
                                .socialId(userId)
                                .profileHref(property.getProfileHref())
                                .socialProvider(provider)
                                .build()));
    }

}
