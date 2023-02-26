package net.ivanzykov.rssfeedarchiver.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.ivanzykov.rssfeedarchiver.services.FeedServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FeedController.class)
@ExtendWith(MockitoExtension.class)
class FeedControllerTest {

    private final List<String> feedUrls = List.of("/testUrl");
    private final String path = "/analyse/new";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FeedServiceImpl feed;

    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            fail();
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Test
    void analyseNew_responseWithCreated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .post(path)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(feedUrls)))
                .andExpect(status().isCreated());

        verify(feed, times(1)).consumeUrls(feedUrls);
    }

    @Test
    void analyseNew_wrongRequestBody_responseWithBadRequest() throws Exception {
        String requestBody = "NotList";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestBody)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Request body should have array of strings with URLs."));
    }
}
