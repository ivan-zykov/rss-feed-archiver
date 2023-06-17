package net.ivanzykov.rssfeedarchiver.services.fetcher;

import net.ivanzykov.rssfeedarchiver.services.FeedVOFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
class FetcherServiceTest {

    @Test
    void both_fetched_feeds_are_set_in_feed(@Autowired final FetcherService sut,
                                            @Autowired final RestTemplate restTemplate)
            throws URISyntaxException, IOException {

        final var url1 = "/testUrl1";
        final var url2 = "/testUrl2";
        final var feedVO = FeedVOFactory.create(List.of(url1, url2));
        final MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(ExpectedCount.once(), requestTo(url1))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getXMLFixture("responseOk.xml"), MediaType.APPLICATION_XML));
        mockServer.expect(ExpectedCount.once(), requestTo(url2))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getXMLFixture("responseOk.xml"), MediaType.APPLICATION_XML));

        sut.consume(feedVO);

        mockServer.verify();
        assertAll(
                () -> assertEquals(2, feedVO.getFetchedFeeds().size()),
                () -> assertEquals("Test title", feedVO.getFetchedFeeds().get(0).getTitle()),
                () -> assertEquals("Test title", feedVO.getFetchedFeeds().get(1).getTitle())
        );
    }

    @Test
    void handle_exception_while_parsing_a_feed(@Autowired final FetcherService sut,
                                               @Autowired final RestTemplate restTemplate)
            throws URISyntaxException, IOException {

        final var url1 = "/testUrl1";
        final var feedVO = FeedVOFactory.create(List.of(url1));
        final MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
        mockServer.expect(ExpectedCount.once(), requestTo(url1))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getXMLFixture("responseBad.xml"), MediaType.APPLICATION_XML));

        Exception exception = assertThrows(FetcherException.class, () ->
                sut.consume(feedVO));

        assertTrue(exception.getMessage().contains("Could not parse the feed with URL: /testUrl1"));
        mockServer.verify();
    }

    private String getXMLFixture(final String filename) throws URISyntaxException, IOException {
        final Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                .getResource(filename)).toURI());
        return Files.readAllLines(path).get(0);
    }
}
