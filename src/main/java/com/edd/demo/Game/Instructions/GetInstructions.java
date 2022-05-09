package com.edd.demo.Game.Instructions;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.io.FileOutputStream;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

public class GetInstructions {

    @GetMapping("/Game/status-avltree")
    public ResponseEntity<?> getStatusAVL(String ruta) {
        String url = ruta + "Game/status-avltree";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result;
    }

    @GetMapping("/Game/get-level")
    public ResponseEntity<?> getLevel(String level, String ruta) {
        String url = ruta + "Game/get-level?level=" + level;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result;
    }

    @GetMapping("/Game/avltree")
    public ResponseEntity<?> getTransversal(String order, String ruta) {
        String url = ruta + "Game/avltree?transversal=" + order;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        return result;
    }

    public boolean DownloadImg(ResponseEntity<?> response, String name,String ruta) {
        try {
            String respuesta = response.getBody().toString();
            //String respuesta = "{ \"path\": \"http://localhost:8080/img/img.jpeg\"}";
            JSONObject object = new JSONObject(respuesta);
            String path = object.getString("path");
            //String path = "http://localhost:8080/img/img.jpeg";
            URLConnection conn = new URL(ruta + path).openConnection();
            File file = new File(name);
            conn.connect();
            InputStream in = conn.getInputStream();
            OutputStream out = new FileOutputStream(file);
            int b = 0;
            while (b != -1) {
                b = in.read();
                if (b != -1)
                    out.write(b);
            }
            out.close();
            in.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
