package com.hrw.vsproject.utils;

import com.hrw.vsproject.entities.Interest;
import com.hrw.vsproject.entities.WavesHistoricData;
import com.hrw.vsproject.entities.WavesResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

/**
 * A utility class for fetching data from wavescap api.
 */
@Service
public class WavesUtils {
    private final RestTemplate rt = new RestTemplate();



    /**
     * Fetches the current values and information about waves fom the wavescap.com api.
     *
     * @return {@link WavesResponse} with the data from wavescap api.
     * @throws ResponseStatusException in case api call goes wrong
     */
    public WavesResponse getWavesResponse() throws ResponseStatusException {
        String url = "https://wavescap.com/api/asset/WAVES.json";

        try {
            return rt.getForObject(
                    url,
                    WavesResponse.class
            );
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "API call or mapping went wrong.");
        }
    }

    /**
     * Get price and volume data from wavescap api for one year and maps the data in {@link WavesHistoricData}.
     *
     * @return {@link WavesHistoricData}.
     * @throws ResponseStatusException in case api call goes wrong
     */
    public WavesHistoricData getHistoricData() throws ResponseStatusException {
        String url = "https://wavescap.com/api/chart/asset/WAVES-usd-n-1y.json";

        try {
            return rt.getForObject(
                    url,
                    WavesHistoricData.class
            );
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "API call or mapping went wrong.");
        }

    }

    /**
     * Returns a sorted list of price data for every day of the last year.
     *
     * @param data {@link WavesHistoricData} with historic price and volume data of the last year.
     * @return sorted {@link List} with prices. Each price represents one day of the last year.
     */
    public List<Double> getPrices(WavesHistoricData data) {
        List<Double> result = new ArrayList<>();

        for(ArrayList<Double> list : data.getData()) {
            result.add(list.get(0));
        }

        return result;
    }

    /**
     * Get the respective interests for the different coins
     *
     * @return {@link Interest}.
     * @throws ResponseStatusException in case api call goes wrong
     */
    public Interest getInterest() throws ResponseStatusException {
        String url = "https://dev.pywaves.org/neutrino/json";

        try {
            return rt.getForObject(
                    url,
                    Interest.class
            );
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "API call or mapping went wrong.");
        }
    }

}
