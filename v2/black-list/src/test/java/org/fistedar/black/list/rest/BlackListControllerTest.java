package org.fistedar.black.list.rest;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class BlackListControllerTest {

    @Autowired
    private JsonReader jsonReader;
    @Autowired
    private MockMvc mvc;

    @ParameterizedTest
    @CsvSource({"all_fields_valid","all_fields_null","firstName_is_empty",
            "lastName_is_empty", "personCode_is_empty"})
    void testPersonScenarios(String directory) throws Exception {
        compare(directory, "rest/person/");
    }

    private void compare(String directory, String path) throws Exception {
        String requestJSON = jsonReader.readString(path + directory + "/Request_" + directory + ".json");
        String expectedJSON = jsonReader.readString(path + directory + "/Response_" + directory + ".json");

        MvcResult result = mvc.perform(post("/blacklist/person/check/")
                        .content(requestJSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseJSON = result.getResponse().getContentAsString();

        assertJson(responseJSON).where()
                .arrayInAnyOrder()
                .keysInAnyOrder()
                .isEqualTo(expectedJSON);
    }
}