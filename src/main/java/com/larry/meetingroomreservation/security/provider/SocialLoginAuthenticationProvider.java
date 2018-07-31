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
        String socialId = property.getSocialId();
        SocialProviders provider = dto.getProvider();
        log.info("userid / provider : {}, {}", socialId, provider.getUserinfoEndpoint());
        return userRepository.findBySocialIdAndSocialProvider(socialId, provider)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .userId(property.getUserNickname())
                                .password(String.valueOf(UUID.randomUUID().getMostSignificantBits()))
                                .name("SOCIAL_USER")
                                .email(property.getEmail())
                                .roleName(RoleName.ROLE_USER)
                                .socialId(socialId)
                                .profileHref(property.getProfileHref())
                                .socialProvider(provider)
                                .build()));
    }

}
