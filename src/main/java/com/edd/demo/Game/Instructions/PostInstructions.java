package com.edd.demo.Game.Instructions;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class PostInstructions {
    
    @GetMapping("/Game/add")
    public ResponseEntity<?> addCarta(Map<String, Object> map, String ruta) {
        String url = ruta + "Game/add";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
            return result;
        } catch (Exception e) {
            HttpClientErrorException errorException = (HttpClientErrorException) e;
            return new ResponseEntity<>(errorException.getStatusCode());
        }

    }
}
