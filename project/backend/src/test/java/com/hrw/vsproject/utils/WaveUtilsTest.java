package com.hrw.vsproject.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrw.vsproject.entities.Interest;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class WaveUtilsTest {
    @Mock
    private RestTemplate rt;

    @InjectMocks
    private WavesUtils wavesUtils;

    @Test
    public void testInterest() throws JsonProcessingException {
        // assign
        ObjectMapper mapper = new ObjectMapper();
        String url = "https://dev.pywaves.org/neutrino/json";

        Interest expected = new Interest();
        expected.setUsdnApy(mapper.readTree(
                """
                {"30d": 4.98,
                "60d": 5.75,
                "7d": 5.09,
                "last": 4.67,
                "3d": 5.18}
                """
        ));

        Mockito.when(rt.getForObject(url, Interest.class)).thenReturn(expected);

        // act
        Interest actual = wavesUtils.getInterest();


        // assert
        Assertions.assertThat(expected.getUsdnApy()).isEqualTo(actual.getUsdnApy());

    }
}
