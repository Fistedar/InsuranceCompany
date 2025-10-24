package org.javaguru.travel.insurance.rest.v2;

import org.javaguru.travel.insurance.config.TestSecurityConfig;
import org.javaguru.travel.insurance.rest.JsonReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.org.webcompere.modelassert.json.JsonAssertions.assertJson;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class TravelCalculatePremiumControllerV2Test {

    @Autowired
    private JsonReader jsonReader;
    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @DisplayName("Test: agreement scenarios")
    @CsvSource({
            "all_fields_null", "all_fields_valid", "country_is_empty", "country_is_null",
            "country_unsupported", "dateFrom_is_null", "dateFrom_later_dateTo",
            "dateTo_is_null", "persons_is_empty", "persons_is_null"
    })
    void testAgreementScenarios(String directory) throws Exception {
        compare(directory, "restV2/agreement/");
    }

    @ParameterizedTest
    @DisplayName("Test: person scenarios")
    @CsvSource({
            "firstName_is_empty", "firstName_is_null", "lastName_is_empty",
            "lastName_is_null", "personBirthDate_in_future", "personBirthDate_is_null",
            "personCode_is_empty", "personCode_is_null", "personCode_invalid_format",
            "lastName_invalid_format", "firstName_invalid_format"
    })
    void testPersonScenarios(String directory) throws Exception {
        compare(directory, "restV2/person/");
    }

    @ParameterizedTest
    @DisplayName("Test: risk travel medical scenarios")
    @CsvSource({
            "medicalRiskLimitLevel_is_empty", "medicalRiskLimitLevel_is_null",
            "medicalRiskLimitLevel_unsupported"
    })
    void testRiskTravelMedicalScenarios(String directory) throws Exception {
        compare(directory, "restV2/risk_travel_medical/");
    }

    @ParameterizedTest
    @DisplayName("Test: selected risks scenarios")
    @CsvSource({
            "selectedRisks_is_empty", "selectedRisks_is_null", "selectedRisks_unsupported_1_risk",
            "selectedRisks_unsupported_2_risks"
    })
    void testSelectedRisksScenarios(String directory) throws Exception {
        compare(directory, "restV2/risks/");
    }


    private void compare(String directory, String path) throws Exception {
        String requestJson = jsonReader.readString(path + directory + "/Request_" + directory + ".json");
        String expectedJson = jsonReader.readString(path + directory + "/Response_" + directory + ".json");

        MvcResult result = mockMvc.perform(post("/insurance/travel/api/v2/")
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBodyContent = result.getResponse().getContentAsString();

        // Для случаев, когда ожидается ошибка
        if (expectedJson.contains("\"errors\"")) {
            assertJson(responseBodyContent).where()
                    .arrayInAnyOrder()
                    .keysInAnyOrder()
                    .at("/errors").isEqualTo(expectedJson);
        }
        // Для успешных ответов (с uuid)
        else {
            assertJson(responseBodyContent).where()
                    .arrayInAnyOrder()
                    .keysInAnyOrder()
                    .at("/uuid").isNotEmpty()
                    .isEqualTo(expectedJson);
        }
    }

}
