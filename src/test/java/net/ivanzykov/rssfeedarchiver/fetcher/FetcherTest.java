package net.ivanzykov.rssfeedarchiver.fetcher;

import com.rometools.rome.io.SyndFeedInput;
import net.ivanzykov.rssfeedarchiver.feed.Feed;
import net.ivanzykov.rssfeedarchiver.feed.FetcherException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {Fetcher.class, RestTemplate.class, SyndFeedInput.class, XmlReaderRomeFactory.class})
class FetcherTest {

    @Autowired
    private Fetcher fetcher;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void consume_fetchTwoFeeds_bothFeedsAreSet() throws URISyntaxException, IOException {
        String url1 = "/testUrl1";
        String url2 = "/testUrl2";
        var feed = new Feed(List.of(url1, url2));

        assertTrue(feed.getFetchedFeeds().isEmpty());

        mockServer.expect(ExpectedCount.once(), requestTo(url1))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getXMLFixture("responseOk.xml"), MediaType.APPLICATION_XML));

        mockServer.expect(ExpectedCount.once(), requestTo(url2))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getXMLFixture("responseOk.xml"), MediaType.APPLICATION_XML));

        fetcher.consume(feed);

        mockServer.verify();

        assertAll(
                () -> assertEquals(2, feed.getFetchedFeeds().size()),
                () -> assertEquals("Test title", feed.getFetchedFeeds().get(0).getTitle()),
                () -> assertEquals("Test title", feed.getFetchedFeeds().get(1).getTitle())
        );
    }

    @Test
    void consume_XMLCouldNotBeParsed_exceptionIsHandled() throws URISyntaxException, IOException {
        String url1 = "/testUrl1";
        var feed = new Feed(List.of(url1));

        mockServer.expect(ExpectedCount.once(), requestTo(url1))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getXMLFixture("responseBad.xml"), MediaType.APPLICATION_XML));

        Exception exception = assertThrows(FetcherException.class, () ->
                fetcher.consume(feed));

        mockServer.verify();

        assertTrue(exception.getMessage().contains("Could not parse the feed with URL: /testUrl1"));
    }

    private String getXMLFixture(String filename) throws URISyntaxException, IOException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                .getResource(filename)).toURI());
        return Files.readAllLines(path).get(0);
    }
}
