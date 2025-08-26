package org.javaguru.travel.insurance.rest.iternal;

import com.jayway.jsonpath.JsonPath;
import org.javaguru.travel.insurance.rest.JsonReader;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TravelGetAgreementRestControllerTest {

    @Autowired
    private JsonReader jsonReader;

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({"valid_uuid", "uuid_unsupported", "uuid_empty"})
    void testVariousScenarios(String directory) throws Exception {
        compare(directory);
    }


    private void compare(String directory) throws Exception {
        String request = jsonReader.readString("restIternal/" + directory + "/Request_" + directory + ".json");
        String response = jsonReader.readString("restIternal/" + directory + "/Response_" + directory + ".json");
        String uuid = JsonPath.parse(request).read("$.uuid", String.class);

        MvcResult result = mockMvc.perform(get("/insurance/travel/api/internal/agreement/" + uuid)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseBodyContent = result.getResponse().getContentAsString();

        JSONAssert.assertEquals(response, responseBodyContent, false);
    }
}