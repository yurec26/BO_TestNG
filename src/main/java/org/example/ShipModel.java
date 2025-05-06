package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ShipModel(
        @JsonProperty("ship_class")
        String shipClass,
        @JsonProperty("ship_name")
        String shipName,
        @JsonProperty("dead_weight")
        long deadWeight,
        @JsonProperty("operator")
        String operator
) {
}
