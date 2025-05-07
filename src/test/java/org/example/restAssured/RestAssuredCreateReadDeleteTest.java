package org.example.restAssured;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.example.ShipModel;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static org.example.RestAssuredHelper.sendRequestToJsonBins;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

// тестики на открытой площадке для хранения джейсонов jsonbin
public class RestAssuredCreateReadDeleteTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ShipModel shipElli;
    private String shipElliId;

    @BeforeClass
    public void setUp() {
        shipElli = new ShipModel(
                "crude tanker",
                "Elli",
                100_000L,
                "Palkipon"
        );
    }

    @Test(description = "Занесение в базу данных корабля")
    public void test1() throws JsonProcessingException {
        JsonNode jsonResponse = sendRequestToJsonBins
                (shipElli, "/b","", "post", Map.of(
                                "Content-Type", "application/json",
                                "X-Bin-Name", "Elli"),
                        HttpStatus.SC_OK);

        ShipModel responseShip = objectMapper
                .treeToValue(jsonResponse.get("record"), ShipModel.class);

        shipElliId = objectMapper.treeToValue(jsonResponse.get("metadata").get("id"), String.class);

        assertNotNull(responseShip, "объект должен существовать в бд");
        assertEquals(responseShip, shipElli,
                "после занесения и возврата из бд объекты должны совпадать ");
        System.out.println("RA 1 " + Thread.currentThread().getName());
    }

    @Test(dependsOnMethods = "test1",
            description = "изъятие их бд по ID")
    public void test2() throws JsonProcessingException {
        JsonNode jsonResponse = sendRequestToJsonBins
                (null, "/b", "/%s".formatted(shipElliId), "get", Map.of(), HttpStatus.SC_OK);

        ShipModel responseShip = objectMapper
                .treeToValue(jsonResponse.get("record"), ShipModel.class);

        String id = objectMapper.treeToValue(jsonResponse.get("metadata").get("id"), String.class);

        assertEquals(id, shipElliId,
                "идентификаторы должны совпадать");
        assertEquals(responseShip, shipElli,
                "после изъятия из бд объекты должны совпадать ");
        System.out.println("RA 2 " + Thread.currentThread().getName());
    }

    @Test(dependsOnMethods = "test2",
            description = "удаление записи из бд и проверка корректности удаления")
    public void test3() {
        sendRequestToJsonBins
                (null, "/b", "/%s".formatted(shipElliId), "delete", Map.of(), HttpStatus.SC_OK);

        sendRequestToJsonBins
                (null, "/b", "/%s".formatted(shipElliId), "get", Map.of(), HttpStatus.SC_NOT_FOUND);
        System.out.println("RA 3 " + Thread.currentThread().getName());
    }
}
