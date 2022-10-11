package com.hrw.vsproject.entities;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

/**
 * POJO for holding current data from the wavescap api.
 */
@Data
public class WavesResponse {
    private String id;
    private Integer precision;
    private JsonNode data;
    
    
}
