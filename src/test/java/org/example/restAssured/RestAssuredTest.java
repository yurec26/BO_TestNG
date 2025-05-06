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

// тестики на открытой площадке для хранения джейсонов jsonbin
public class RestAssuredTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private ShipModel shipElli;
    private String shipElliId;

    @BeforeClass
    public void setUp() {
        shipElli = new ShipModel(
                "crude tanker",
                "Elli",
                100_000,
                "Palkipon"
        );
    }

    @Test(description = "Занесение в базу данных корабля")
    public void test1() throws JsonProcessingException {
        JsonNode jsonResponse = sendRequestToJsonBins
                (shipElli,
                        "",
                        "post",
                        Map.of
                                (
                                        "Content-Type", "application/json",
                                        "X-Bin-Name", "Elli"
                                ),
                        HttpStatus.SC_OK);

        ShipModel responseShip = objectMapper
                .treeToValue(jsonResponse.get("record"), ShipModel.class);

        shipElliId = objectMapper.treeToValue(jsonResponse.get("metadata").get("id"), String.class);

        assertEquals(responseShip, shipElli,
                "после занесения в бд объекты должны совпадать ");

    }

    @Test(dependsOnMethods = "test1",
            description = "изъятие их бд по ID")
    public void test2() throws JsonProcessingException {
        JsonNode jsonResponse = sendRequestToJsonBins
                (null,
                        "/%s".formatted(shipElliId),
                        "get",
                        Map.of(),
                        HttpStatus.SC_OK);

        ShipModel responseShip = objectMapper
                .treeToValue(jsonResponse.get("record"), ShipModel.class);

        String id = objectMapper.treeToValue(jsonResponse.get("metadata").get("id"), String.class);

        assertEquals(id, shipElliId,
                "идентификаторы должны совпадать");
        assertEquals(responseShip, shipElli,
                "после изъятия из бд объекты должны совпадать ");

    }

    @Test(dependsOnMethods = "test2",
            description = "удаление записи из бд и проверка наличия")
    public void test3() {
        sendRequestToJsonBins
                (null,
                        "/%s".formatted(shipElliId),
                        "delete",
                        Map.of(),
                        HttpStatus.SC_OK);

        sendRequestToJsonBins
                (
                        null,
                        "/%s".formatted(shipElliId),
                        "get",
                        Map.of(),
                        HttpStatus.SC_NOT_FOUND);
    }
}
