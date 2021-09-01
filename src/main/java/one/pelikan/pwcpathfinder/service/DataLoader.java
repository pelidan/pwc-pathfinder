package one.pelikan.pwcpathfinder.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.pelikan.pwcpathfinder.dto.Country;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@SuppressWarnings("unused")
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader {

    private static final TypeReference<List<Country>> TYPE_REF = new TypeReference<>() {
    };
    public static final String RESOURCE_URL = "src/main/resources/countries.json";

    @Value("${data.url}")
    private String dataUrl;

    private final ObjectMapper objectMapper;

    public List<Country> loadCountries() {
        try {
            return loadFromUrl();
        } catch (IOException e) {
            log.warn("unable to load data file from URL: " + dataUrl);
            return loadFromResources();
        }
    }

    private List<Country> loadFromUrl() throws IOException {
        return objectMapper.readValue(new URL(dataUrl), TYPE_REF);
    }

    private List<Country> loadFromResources() {
        try {
            String json = Files.readString(Paths.get(RESOURCE_URL));
            return objectMapper.readValue(json, TYPE_REF);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to load list of countries");
        }
    }


}
