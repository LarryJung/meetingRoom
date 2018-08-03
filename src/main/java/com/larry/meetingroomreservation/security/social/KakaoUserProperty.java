package com.larry.meetingroomreservation.security.social;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class KakaoUserProperty implements SocialUserProperty{

    @JsonProperty("kaccount_email")
    private String email;

    @JsonProperty("kaccount_email_verified")
    private Boolean verified;

    @JsonProperty("id")
    private Long userUniqueId;

    @JsonProperty("properties")
    private Map<String, String> userProperties;

    @Override
    public String getSocialId() {
        return userUniqueId.toString();
    }

    @Override
    public String getUserNickname() {
        return userProperties.get("nickname");
    }

    @Override
    public String getProfileHref() {
        return userProperties.get("profile_image");
    }

    @Override
    public String getEmail() {
        return email;
    }
}
