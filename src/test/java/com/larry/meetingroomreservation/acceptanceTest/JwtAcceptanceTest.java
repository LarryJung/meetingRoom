package com.larry.meetingroomreservation.acceptanceTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.larry.meetingroomreservation.domain.entity.SocialProviders;
import com.larry.meetingroomreservation.security.dto.LoginJwtResponseDto;
import com.larry.meetingroomreservation.security.dto.SocialLoginDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JwtAcceptanceTest {

    private final Logger log = LoggerFactory.getLogger(JwtAcceptanceTest.class);

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${kakao.accesstoken}")
    private String kakaoAccessToken;

    @Autowired
    private ObjectMapper objectMapper;

    // 이 테스트는 클라이언트로부터 accessToken을 받아서 내려준 이후의 JWT 발급 과정을 테스트한다.
    // accessToken은 application.properties에 있고 갱신되거나 만료되면 테스트코드는 일시적으로 실패할 수 있다.
    @Test
    public void kakaoLogin() {
        Map<String, Object> params = new HashMap<>();
        params.put("provider", "KAKAO");
        params.put("token", kakaoAccessToken);

        String body = null;
        try {
            body = objectMapper.writeValueAsString(params);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(body != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            HttpEntity entity = new HttpEntity(body, headers);
            LoginJwtResponseDto dto = restTemplate.postForObject("/socialLogin", entity, LoginJwtResponseDto.class);
            log.info("got token : {}", dto.getToken());
            assertNotNull(dto.getToken());
        }
    }

}
