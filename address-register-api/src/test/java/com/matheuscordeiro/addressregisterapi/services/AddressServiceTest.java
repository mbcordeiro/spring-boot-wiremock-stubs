package com.matheuscordeiro.addressregisterapi.services;

import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.matheuscordeiro.addressregisterapi.bean.ZipCodeResponse;
import com.matheuscordeiro.addressregisterapi.config.CEPConfig;
import com.matheuscordeiro.addressregisterapi.services.impl.AddressServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("mock")
public class AddressServiceTest {
    private AddressServiceImpl addressService;

    @Autowired
    private CEPConfig cepConfig;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8082);

    @Before
    public void setUp() throws Exception {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        addressService = new AddressServiceImpl(restTemplate, cepConfig);
    }

    @Test
    public void shouldListAddressByZipCodeWithSuccess() {
        ZipCodeResponse mockResponse = new ZipCodeResponse();
        mockResponse.setAddress("Rua X");
        mockResponse.setCity("Sao Paulo");
        mockResponse.setComplement("xxx");
        mockResponse.setNeighborhood("Novo");
        mockResponse.setZipCode("12345678");
        mockResponse.setState("SP");

        stubFor(get(urlPathEqualTo("/ws/12345678/json")).willReturn(ResponseDefinitionBuilder.okForJson(mockResponse)));

        ZipCodeResponse zipCodeResponse = addressService.getAddressByZipCode("12345678");

        verify(1, getRequestedFor(urlPathEqualTo("/ws/12345678/json")));

        assertNotNull(zipCodeResponse);
        assertFalse(zipCodeResponse.isObjectEmpty());
    }


    @Test
    public void shouldListAddressByZipCodeWithFail() {

        stubFor(get(urlPathEqualTo("/ws/07010100/json")).willReturn(aResponse().withStatus(HttpStatus.BAD_GATEWAY.value())));

        ZipCodeResponse zipCodeResponse = addressService.getAddressByZipCode("07010100");

        verify(1, getRequestedFor(urlPathEqualTo("/ws/07010100/json")));

        assertNotNull(zipCodeResponse);
    }
}
