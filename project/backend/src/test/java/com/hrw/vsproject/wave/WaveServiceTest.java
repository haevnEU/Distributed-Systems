package com.hrw.vsproject.wave;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrw.vsproject.entities.*;
import com.hrw.vsproject.services.SharpeRatioService;
import com.hrw.vsproject.services.WavesService;
import com.hrw.vsproject.utils.WavesUtils;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class WaveServiceTest {

    @Mock
    private WavesUtils wavesUtils;

    private WavesService wavesService;
    
    private SharpeRatioService sharpeRatioService;

    @Before
    public void setUp()  {
        wavesService = new WavesService(wavesUtils);
        sharpeRatioService = new SharpeRatioService();
    }

    @Test
    public void testMovingAverage() {
        // assign
        final long  START   = 1643702400L;
        final long  TIME24H = 86400000L; // 24h in ms
        final int   PERIOD  = 3;

        WavesHistoricData response = new WavesHistoricData();
        response.setStart(new Date(1643702400)); // 01.02.2022 8:00:00
        ArrayList<ArrayList<Double>> data = new ArrayList<>();
        // week 1
        data.add(new ArrayList<>(Arrays.asList(11.0,9908790.2843831)));
        data.add(new ArrayList<>(Arrays.asList(12.0,10189115.484735)));
        data.add(new ArrayList<>(Arrays.asList(13.0,10405027.985165)));
        data.add(new ArrayList<>(Arrays.asList(14.0,10584549.978609)));
        data.add(new ArrayList<>(Arrays.asList(15.0,10683485.817987)));
        data.add(new ArrayList<>(Arrays.asList(16.0,10664537.140598)));
        data.add(new ArrayList<>(Arrays.asList(17.0,15772911.905205)));
        // week 2
        data.add(new ArrayList<>(Arrays.asList(21.0,16033060.276911)));
        data.add(new ArrayList<>(Arrays.asList(22.0,16051725.146456)));
        data.add(new ArrayList<>(Arrays.asList(23.0,16174172.187481)));
        data.add(new ArrayList<>(Arrays.asList(24.0,16021165.130637)));
        data.add(new ArrayList<>(Arrays.asList(25.0,16086152.665355)));
        data.add(new ArrayList<>(Arrays.asList(26.0,16115106.678239)));
        data.add(new ArrayList<>(Arrays.asList(27.0,16262036.393648)));
        response.setData(data);

        Mockito.when(wavesUtils.getHistoricData()).thenReturn(response);
        Mockito.when(wavesUtils.getPrices(response)).thenReturn(Arrays.asList(11.0, 12.0, 13.0, 14.0, 15.0, 16.0, 17.0, 21.0, 22.0, 23.0, 24.0, 25.0, 26.0, 27.0));

        // act
        PriceRange priceRange = wavesService.getAllPricesAndMovingAverage(PERIOD);

        // assert
        Price p1 = priceRange.getData().get(START);
        Price p2 = priceRange.getData().get(START + TIME24H * 1);
        Price p3 = priceRange.getData().get(START + TIME24H * 2);
        Price p4 = priceRange.getData().get(START + TIME24H * 3);
        Price p5 = priceRange.getData().get(START + TIME24H * 4);

        Assertions.assertThat(p1.getPriceEod()).isEqualTo(11.0);
        Assertions.assertThat(p2.getPriceEod()).isEqualTo(12.0);
        Assertions.assertThat(p3.getPriceEod()).isEqualTo(13.0);
        Assertions.assertThat(p4.getPriceEod()).isEqualTo(14.0);
        Assertions.assertThat(p5.getPriceEod()).isEqualTo(15.0);

        Offset<Double> offset = Offset.offset(0.01);
        Assertions.assertThat(p1.getMovingAvg()).isNull();
        Assertions.assertThat(p2.getMovingAvg()).isNull();
        Assertions.assertThat(p3.getMovingAvg()).isEqualTo(12.0, offset);
        Assertions.assertThat(p4.getMovingAvg()).isEqualTo(13.0, offset);
        Assertions.assertThat(p5.getMovingAvg()).isEqualTo(14.0, offset);

    }


    @Test
    public void testGetCurrentPrice() throws JsonProcessingException {
        // assign
        Double expected = 11.0;

        WavesResponse response = new WavesResponse();

        String data = """
                {"firstPrice_tryn": 143.74994717929,
                "lastPrice_tryn": 152.35899452959,
                "firstPrice_brln": 55.764603271709,
                "lastPrice_brln": 59.104292220867,
                "firstPrice_gbpn": 7.8632046592739,
                "lastPrice_gbpn": 8.3075291848936,
                "firstPrice_ngnn": 0,
                "lastPrice_ngnn": 0,
                "firstPrice_uahn": 296.99792567697,
                "lastPrice_uahn": 314.78484842209,
                "firstPrice_jpyn": 1218.1903591974,
                "lastPrice_jpyn": 1287.6450428132,
                "firstPrice_cnyn": 67.257433854339,
                "lastPrice_cnyn": 71.102122238597,
                "firstPrice_rubn": 800.02826642668,
                "lastPrice_rubn": 847.94119691739,
                "firstPrice_eurn": 9.2873763834213,
                "lastPrice_eurn": 9.8172465945289,
                "firstPrice_usd-n": 10.532637846886,
                "lastPrice_usd-n": 11.0,
                "firstPrice_waves": 1,
                "lastPrice_waves": 1,
                "firstPrice_btc": 0.0002385645585805,
                "lastPrice_btc": 0.00024921851520126,
                "firstPrice_eth": 0.0033990897458317,
                "lastPrice_eth": 0.0034536978679947}
                """;

        ObjectMapper mapper = new ObjectMapper();
        response.setData(mapper.readTree(data));
        response.setId("WAVES");
        response.setPrecision(8);

        Mockito.when(wavesUtils.getWavesResponse()).thenReturn(response);
        // act
        Double actual = wavesService.getCurrentPrice();

        // assert
        Assertions.assertThat(actual).isEqualTo(expected);
    }
    
    @Test
    public void testGetSharpeRatio() {
    	
    	WavesHistoricData response = new WavesHistoricData();
    	
    	
    	double expectedResult = 8.04885233884948;

        Mockito.when(wavesUtils.getHistoricData()).thenReturn(response);
    	Mockito.when(wavesUtils.getPrices(response)).thenReturn(Arrays.asList(3.0,6.0,7.5,10.0));

    	double result =  wavesService.getSharpeRatio();

        Offset<Double> offset = Offset.offset(0.01);
        Assertions.assertThat(result).isEqualTo(expectedResult, offset);
    	
    }

    @Test
    public void testStandDeviation() {
        double expectedResult = 29.06729102;

        List<Double> doubles = Arrays.asList(3.0,6.0,7.5,10.0);

        double result =  sharpeRatioService.calculateSD(doubles);

        Offset<Double> offset = Offset.offset(0.01);
        Assertions.assertThat(result).isEqualTo(expectedResult, offset);

    }

    @Test
    public void testAssetReturn() {
        double expectedResult = 233.3333333;

        List<Double> doubles = Arrays.asList(3.0,6.0,7.5,10.0);

        double result =  sharpeRatioService.calculateAR(doubles);

        Offset<Double> offset = Offset.offset(0.01);
        Assertions.assertThat(result).isEqualTo(expectedResult, offset);

    }

}
