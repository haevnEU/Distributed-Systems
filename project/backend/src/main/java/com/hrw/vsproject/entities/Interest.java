package com.hrw.vsproject.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * Interest class holding the interest values for the respective coins
 */
@Data
public class Interest {
    @JsonProperty("rubn-apy")
    private JsonNode rubnApy;
    @JsonProperty("eth-apy")
    private JsonNode ethApy;
    @JsonProperty("eurn-apy")
    private JsonNode eurnApy;
    @JsonProperty("brln-apy")
    private JsonNode brlnApy;
    @JsonProperty("tryn-apy")
    private JsonNode trynApy;
    @JsonProperty("usdt-apy")
    private JsonNode usdtApy;
    @JsonProperty("usdc-apy")
    private JsonNode usdcApy;
    @JsonProperty("usdn-apy")
    private JsonNode usdnApy;
    @JsonProperty("uahn-apy")
    private JsonNode uahnApy;
    @JsonProperty("jpyn-apy")
    private JsonNode jpynApy;
    @JsonProperty("gbpn-apy")
    private JsonNode gbpnApy;
    @JsonProperty("cnyn-apy")
    private JsonNode cnynApy;
    @JsonProperty("btc-apy")
    private JsonNode btcApy;
    @JsonProperty("nsbt-apy")
    private JsonNode nsbtApy;
}
