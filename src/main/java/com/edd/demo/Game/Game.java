package com.edd.demo.Game;

import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Game {


    @GetMapping("/Game/start")
    public ResponseEntity<?> startGame(Map<String, Object> map, String ruta ) {
        String url = ruta + "Game/start";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(url, entity, String.class);
            return result;
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

}
