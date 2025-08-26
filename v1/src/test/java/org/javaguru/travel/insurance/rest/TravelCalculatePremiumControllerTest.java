package org.javaguru.travel.insurance.rest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TravelCalculatePremiumControllerTest {

    @Autowired
    private JsonReader jsonReader;
    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({
            "all_fields_null", "all_fields_valid", "country_is_empty", "country_is_null",
            "country_unsupported", "dateFrom_is_null", "dateFrom_later_dateTo",
            "dateTo_is_null", "firstName_is_empty", "firstName_is_null",
            "lastName_is_empty", "lastName_is_null", "medicalRiskLimitLevel_is_empty",
            "medicalRiskLimitLevel_is_null", "medicalRiskLimitLevel_unsupported",
            "personBirthDate_in_future", "personBirthDate_is_null", "selectedRisks_is_empty",
            "selectedRisks_is_null", "selectedRisks_unsupported"
    })
    void testVariousScenarios(String directory) throws Exception {
        compare(directory);
    }


    private void compare(String directory) throws Exception {
        String requestJson = jsonReader.readString("rest/" + directory + "/Request_" + directory + ".json");
        String expectedJson = jsonReader.readString("rest/" + directory + "/Response_" + directory + ".json");

        MvcResult result = mockMvc.perform(post("/insurance/travel/api/")
                        .content(requestJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn();

        JSONAssert.assertEquals(expectedJson, result.getResponse().getContentAsString(), false);
    }

}
