package com.hrw.vsproject.entities;


import java.sql.Date;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO for holding historic data from the wavescap api.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WavesHistoricData {

    private Date start;
    private ArrayList<ArrayList<Double>> data;
	

}


