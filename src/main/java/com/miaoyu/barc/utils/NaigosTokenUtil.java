package com.miaoyu.barc.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miaoyu.barc.pojo.NaigosApiPojo;
import com.miaoyu.barc.user.mapper.BarcNaigosTokenMapper;
import com.miaoyu.barc.user.model.BarcNaigosTokenModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class NaigosTokenUtil {
    @Autowired
    private BarcNaigosTokenMapper barcNaigosTokenMapper;

    public J getNaigosUuid(String naigosToken) throws IOException, InterruptedException {
        String api = "https://sapi.naigos.cn/users/current";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(api))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        NaigosApiPojo naigosApiPojo = mapper.readValue(response.body(), NaigosApiPojo.class);
        J j = new J();
        j.setCode(naigosApiPojo.getCode());
        j.setMsg(naigosApiPojo.getMessage());
        if (j.getCode() == 0) {
            BarcNaigosTokenModel data = (BarcNaigosTokenModel) naigosApiPojo.getData();

        }
        return j;
    }
}
