package com.larry.meetingroomreservation.security.social;

import com.larry.meetingroomreservation.domain.entity.SocialProviders;
import com.larry.meetingroomreservation.security.dto.SocialLoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SocialFetchService {

    private final Logger log = LoggerFactory.getLogger(SocialFetchService.class);

    private static final String HEADER_PREFIX = "Bearer ";

    public SocialUserProperty getSocialUserInfo(SocialLoginDto dto) {
        SocialProviders provider = dto.getProvider();
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entity = new HttpEntity<>("parameter", generateHeader(dto.getToken()));
        log.info("entity : {}", entity);
        return restTemplate.exchange(provider.getUserinfoEndpoint(), HttpMethod.GET, entity, provider.getPropertyMetaclass()).getBody();
    }

    private HttpHeaders generateHeader(String token) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", generateHeaderContent(token));
        return header;
    }

    private String generateHeaderContent(String token) {
        StringBuilder sb = new StringBuilder();

        sb.append(HEADER_PREFIX);
        sb.append(token);

        return sb.toString();
    }

}
