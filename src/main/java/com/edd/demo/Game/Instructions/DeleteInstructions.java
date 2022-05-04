package com.edd.demo.Game.Instructions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class DeleteInstructions {
    

    @GetMapping("/Game/status-avltree")
    public ResponseEntity<?> deleteCartas(Map<String, Object> map, String ruta) {
        String url = ruta + "Game/delete";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.delete(url, entity,String.class);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            HttpClientErrorException errorException = (HttpClientErrorException) e;
            return new ResponseEntity<>(errorException.getStatusCode());
        }
    }
}
