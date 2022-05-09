package com.edd.demo.Game.Instructions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public class DeleteInstructions {
    

    @GetMapping("/Game/status-avltree")
    public ResponseEntity<?> deleteCartas(Map<String, String> map, String ruta) {
        String url = ruta + "Game/delete";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            System.out.println(entity);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, entity,String.class,"");
            return response;
        } catch (Exception e) {
            HttpClientErrorException errorException = (HttpClientErrorException) e;
            return new ResponseEntity<>(errorException.getStatusCode());
        }
    }
}
