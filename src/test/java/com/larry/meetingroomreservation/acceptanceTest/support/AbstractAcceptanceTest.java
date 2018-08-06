package com.larry.meetingroomreservation.acceptanceTest.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.larry.meetingroomreservation.domain.entity.User;
import com.larry.meetingroomreservation.domain.entity.dto.ReservationRequestDto;
import com.larry.meetingroomreservation.domain.entity.support.RoleName;
import com.larry.meetingroomreservation.domain.repository.UserRepository;
import com.larry.meetingroomreservation.security.jwt.JwtFactory;
import com.larry.meetingroomreservation.security.token.PostAuthorizationToken;
import com.larry.meetingroomreservation.security.utils.Util;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AbstractAcceptanceTest {

    private final Logger log = LoggerFactory.getLogger(AbstractAcceptanceTest.class);

    // ROLE_ADMIN
    protected static final Long Larry = 1L;

    // ROLE_USER
    protected static final Long Charry = 2L;

    protected static final Long KAKAO_정채균 = 3L;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public TestRestTemplate restTemplate;

    @Autowired
    private JwtFactory jwtFactory;

    public  <T> HttpEntity<T> makeJwtEntity(Long loginedUserId, Object object) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + makeJWT(userRepository.findById(loginedUserId).get()));
        HttpEntity<T> entity = new HttpEntity<>((T)object, header);
        return entity;
    }

    public  <T> HttpEntity<T> makeJwtEntity(Long loginedUserId) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + makeJWT(userRepository.findById(loginedUserId).get()));
        HttpEntity<T> entity = new HttpEntity<>(header);
        return entity;
    }


    public String makeJWT(User user) {
        PostAuthorizationToken postAuthorizationToken = new PostAuthorizationToken(user.getId(), user.getName(), Util.parseAuthorities(user.getRoleName()));
        String token = jwtFactory.generateToken(postAuthorizationToken);
        return token;
    }

    public <T> List<T> convertList(ResponseEntity<List> response, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String jsonArray = mapper.writeValueAsString(response.getBody());
        log.info("json : {}", jsonArray );
        CollectionType javaType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        List<T> asList = mapper.readValue(jsonArray, javaType);
        return asList;
    }

    protected String toStringDateTime(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Optional.ofNullable(date)
                .map(formatter::format)
                .orElse("");
    }

    protected <T> T getResource(String location, Class<T> responseType) {
        return restTemplate.getForObject(location, responseType);
    }

}
