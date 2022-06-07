package org.cyrilselyanin.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.cyrilselyanin.dto.RegCashRequestDto;
import org.cyrilselyanin.dto.RegCashResponseDto;

import java.io.IOException;

public class SbisRetailService {
    private final OkHttpClient okHttpClient = new OkHttpClient();
    private final MediaType JSON_MEDIA = MediaType.get(
            "application/json; charset=utf-8"
    );

    public RegCashResponseDto regCash(
            String regCashUrl,
            String token,
            String sid,
            RegCashRequestDto regCashRequestDto
    ) throws NullPointerException, IOException {
        String json = new ObjectMapper().writeValueAsString(regCashRequestDto);
        RequestBody requestBody = RequestBody.create(json, JSON_MEDIA);
        Request request = new Request.Builder()
                .url(regCashUrl)
                .post(requestBody)
                .addHeader("X-SBISAccessToken", token)
                .addHeader("X-SBISSessionId", sid)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            String respJson = response.body().string();
            return new ObjectMapper()
                    .readerFor(RegCashResponseDto.class)
                    .readValue(respJson);
        }
    }
}