package com.edd.demo.Game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.edd.demo.Game.Instructions.DeleteInstructions;
import com.edd.demo.Game.Instructions.GetInstructions;
import com.edd.demo.Game.Instructions.PostInstructions;

import org.json.JSONObject;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@TestMethodOrder(OrderAnnotation.class)
public class GameTest {

    private static final String ruta = "https://cdaf-45-229-42-86.ngrok.io/";
    Game game = new Game();
    PostInstructions postInstructions = new PostInstructions();
    DeleteInstructions deleteInstructions = new DeleteInstructions();
    GetInstructions getInstructions = new GetInstructions();

    @Test
    @Order(1)
    void testStartGame() {
        Map<String, String> map = new LinkedHashMap<>(16,0.75f,true);
        map.put("0", "2♣");
        map.put("1", "K♣");
        map.put("2", "A♦");
        map.put("3", "9♣");
        map.put("4", "10♥");
        map.put("5", "8♣");
        map.put("6", "8♦");
        map.put("7", "J♠");
        map.put("8", "4♦");
        map.put("9", "7♣");
        map.put("10", "6♥");
        map.put("11", "3♠");
        map.put("12", "2♥");
        map.put("13", "10♣");
        map.put("14", "7♥");
        map.put("15", "8♥");
        System.out.println(map);
        assertEquals(HttpStatus.OK, game.startGame(map, ruta).getStatusCode());
    }

    @Test
    @Order(2)
    void testGetStatusAVL1() {
        Boolean flag = false;
        ResponseEntity<?> response = getInstructions.getStatusAVL(ruta);
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                flag = getInstructions.DownloadImg(response, "AVL1.jpg",ruta);
            } catch (Exception e) {
                flag = false;
            }
        }
        assertTrue(flag);
    }

    @Test
    @Order(3)
    void testInsertDuplicated() {
        Map<String, Object> map = new HashMap<>();
        map.put("insert", "7♣");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, postInstructions.addCarta(map, ruta).getStatusCode());
    }

    @Test
    @Order(4)
    void testGetLevel() {
        ResponseEntity<?> response = getInstructions.getLevel("2", ruta);
        if (response.getStatusCode() == HttpStatus.OK) {
            String respoString = response.getBody().toString();
            Map<String, Object> map = new LinkedHashMap<>(16,0.75f,true);
            map.put("0", "8♣");
            map.put("1", "6♥");
            JSONObject respuestaCorrecta = new JSONObject(map);
            try {
                JSONObject object = new JSONObject(respoString);
                assertEquals(respuestaCorrecta.toString(), object.toString());
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }

    @Test
    @Order(5)
    void testInsertNotDuplicated() {
        Map<String, Object> map = new LinkedHashMap<>(16,0.75f,true);
        map.put("insert", "J♣");
        assertEquals(HttpStatus.OK, postInstructions.addCarta(map, ruta).getStatusCode());
    }

    @Test
    @Order(6)
    void testPreOrder() {
        ResponseEntity<?> response = getInstructions.getTransversal("preOrder", ruta);
        if (response.getStatusCode() == HttpStatus.OK) {
            String respoString = response.getBody().toString();
            Map<String, Object> map = new LinkedHashMap<>(16,0.75f,true);
            map.put("0", "K♣");
            map.put("1", "8♣");
            map.put("2", "2♣");
            map.put("3", "7♣");
            map.put("4", "10♣");
            map.put("5", "9♣");
            map.put("6", "J♣");
            map.put("7", "6♥");
            map.put("8", "8♦");
            map.put("9", "A♦");
            map.put("10", "4♦");
            map.put("11", "2♥");
            map.put("12", "10♥");
            map.put("13", "7♥");
            map.put("14", "8♥");
            map.put("15", "J♠");
            map.put("16", "3♠");
            JSONObject respuestaCorrecta = new JSONObject(map);
            try {
                JSONObject object = new JSONObject(respoString);
                assertEquals(respuestaCorrecta.toString(), object.toString());
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }

    @Test
    @Order(7)
    void testDelete() {
        Map<String, String> map = new LinkedHashMap<>(16,0.75f,true);
        map.put("delete_1", "9♣");
        map.put("delete_2", "4♦");
        assertEquals(HttpStatus.OK, deleteInstructions.deleteCartas(map, ruta).getStatusCode());

    }

    @Test
    @Order(8)
    void testDeleteProhibited() {
        Map<String, String> map = new HashMap<>();
        map.put("delete_1", "K♣");
        assertEquals(HttpStatus.CONFLICT, deleteInstructions.deleteCartas(map, ruta).getStatusCode());

    }

    @Test
    @Order(9)
    void testDeleteNonExist() {
        Map<String, String> map = new HashMap<>();
        map.put("delete_1", "K♦");
        assertEquals(HttpStatus.NOT_FOUND, deleteInstructions.deleteCartas(map, ruta).getStatusCode());

    }

    @Test
    @Order(10)
    void secondTestGetLevel() {
        ResponseEntity<?> response = getInstructions.getLevel("5", ruta);
        if (response.getStatusCode() == HttpStatus.OK) {
            String respoString = response.getBody().toString();
            Map<String, Object> map = new LinkedHashMap<>(16,0.75f,true);
            map.put("0", "8♥");
            map.put("1", "3♠");
            JSONObject respuestaCorrecta = new JSONObject(map);
            try {
                JSONObject object = new JSONObject(respoString);
                assertEquals(respuestaCorrecta.toString(), object.toString());
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }

    @Test
    @Order(11)
    void testSecondDelete() {
        Map<String, String> map = new HashMap<>();
        map.put("delete_1", "J♣");
        map.put("delete_2", "2♥");
        assertEquals(HttpStatus.OK, deleteInstructions.deleteCartas(map, ruta).getStatusCode());

    }

    @Test
    @Order(12)
    void testThirdDelete() {
        Map<String, String> map = new HashMap<>();
        map.put("delete_1", "10♣");
        map.put("delete_2", "3♠");
        assertEquals(HttpStatus.OK, deleteInstructions.deleteCartas(map, ruta));

    }

    @Test
    @Order(13)
    void testPostOrder() {
        ResponseEntity<?> response = getInstructions.getTransversal("postOrder", ruta);
        if (response.getStatusCode() == HttpStatus.OK) {
            String respoString = response.getBody().toString();
            Map<String, Object> map = new LinkedHashMap<>(16,0.75f,true);
            map.put("0", "2♣");
            map.put("1", "8♣");
            map.put("2", "7♣");
            map.put("3", "A♦");
            map.put("4", "8♦");
            map.put("5", "K♣");
            map.put("6", "8♥");
            map.put("7", "7♥");
            map.put("8", "J♠");
            map.put("9", "10♥");
            map.put("10", "6♥");
            
            JSONObject respuestaCorrecta = new JSONObject(map);
            try {
                JSONObject object = new JSONObject(respoString);
                assertEquals(respuestaCorrecta.toString(), object.toString());
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }
    @Test
    @Order(14)
    void testFourthDelete() {
        Map<String, String> map = new HashMap<>();
        map.put("delete_1", "2♥");
        map.put("delete_2", "J♣");
        assertEquals(HttpStatus.OK, deleteInstructions.deleteCartas(map, ruta));

    }

    @Test
    @Order(15)
    void testFifthDelete() {
        Map<String, String> map = new HashMap<>();
        map.put("delete_1", "8♣");
        map.put("delete_2", "7♥");
        assertEquals(HttpStatus.NOT_ACCEPTABLE, deleteInstructions.deleteCartas(map, ruta));

    }

    @Test
    @Order(16)
    void testSecondInsert() {
        Map<String, Object> map = new HashMap<>();
        map.put("insert", "5♠");
        assertEquals(HttpStatus.OK, postInstructions.addCarta(map, ruta).getStatusCode());
    }

    @Test
    @Order(17)
    void testSixthDelete() {
        Map<String, String> map = new HashMap<>();
        map.put("delete_1", "5♠");
        map.put("delete_2", "8♣");
        assertEquals(HttpStatus.ACCEPTED, deleteInstructions.deleteCartas(map, ruta));

    }

    @Test
    @Order(18)
    void testGetStatusAVL2() {
        Boolean flag = false;
        ResponseEntity<?> response = getInstructions.getStatusAVL(ruta);
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                flag = getInstructions.DownloadImg(response, "AVL2.jpg",ruta);
            } catch (Exception e) {
                flag = false;
            }
        }
        assertTrue(flag);
    }
    @Test
    @Order(19)
    void testInOrder() {
        ResponseEntity<?> response = getInstructions.getTransversal("inOrder", ruta);
        if (response.getStatusCode() == HttpStatus.OK) {
            String respoString = response.getBody().toString();
            Map<String, Object> map = new LinkedHashMap<>(16,0.75f,true);
            map.put("0", "7♣");
            map.put("1", "K♣");
            map.put("2", "A♦");
            map.put("3", "8♦");
            map.put("4", "6♥");
            map.put("5", "7♥");
            map.put("6", "8♥");
            map.put("7", "10♥");
            
            JSONObject respuestaCorrecta = new JSONObject(map);
            try {
                JSONObject object = new JSONObject(respoString);
                assertEquals(respuestaCorrecta.toString(), object.toString());
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }
}
