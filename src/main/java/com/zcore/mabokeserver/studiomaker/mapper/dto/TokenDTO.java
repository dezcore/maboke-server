package com.zcore.mabokeserver.studiomaker.mapper.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenDTO {
    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("token_type")
    private String token_type;

    @JsonProperty("expires_in")
    private double expires_in;

    @JsonProperty("refresh_token")
    private String refresh_token;

    @JsonProperty("scope")
    private String scope;
}
