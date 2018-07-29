package com.larry.meetingroomreservation.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.larry.meetingroomreservation.domain.entity.SocialProviders;
import com.larry.meetingroomreservation.security.social.SocialUserProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SocialLoginDto {

    @JsonProperty("provider")
    private SocialProviders provider;

    @JsonProperty("token")
    private String token;

}
