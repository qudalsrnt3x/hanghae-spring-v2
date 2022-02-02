package com.hanghae.hanghaespringv2.oauth.provider;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();
}
