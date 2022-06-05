package org.cyrilselyanin.dto;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class TokenRequestDto {
    private String appClientId;
    private String appSecret;
    private String secretKey;

    public static TokenRequestDto create(
            String appClientId,
            String appSecret,
            String secretKey
    ) {
        TokenRequestDto dto = new TokenRequestDto();
        dto.setAppClientId(appClientId);
        dto.setAppSecret(appSecret);
        dto.setSecretKey(secretKey);
        return dto;
    }

    @JsonGetter("app_client_id")
    public String getAppClientId() {
        return this.appClientId;
    }

    @JsonGetter("app_secret")
    public String getAppSecret() {
        return this.appSecret;
    }

    @JsonGetter("secret_key")
    public String getSecretKey() {
        return this.secretKey;
    }
}
