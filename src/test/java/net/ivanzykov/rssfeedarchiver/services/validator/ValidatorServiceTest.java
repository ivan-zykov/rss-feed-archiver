package net.ivanzykov.rssfeedarchiver.services.validator;

import net.ivanzykov.rssfeedarchiver.services.FeedVOFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ValidatorServiceTest {

    @Autowired
    ValidatorService validatorService;

    private static Stream<Arguments> provide_consume_invalidUrl_throwException() {
        return Stream.of(
                Arguments.of("/wrongUrl"),
                Arguments.of("ftp://site.com/abc.txt")
        );
    }

    @ParameterizedTest
    @MethodSource("provide_consume_invalidUrl_throwException")
    void consume_invalidUrl_throwException(String url) {
        var feed = new FeedVOFactory().create(List.of(url));

        Exception exception = assertThrows(InvalidFeedUrlException.class, () ->
                validatorService.consume(feed));

        assertEquals("Following feed URL is invalid: " + url, exception.getMessage());
    }

    @Test
    void consume_emptyUrl_throwException() {
        var feed = new FeedVOFactory().create(List.of(""));

        Exception exception = assertThrows(InvalidFeedUrlException.class, () ->
                validatorService.consume(feed));

        assertEquals("One of the provided URLs is empty", exception.getMessage());

    }

    @Test
    void consume_localhost_valid() {
        var feed = new FeedVOFactory().create(List.of("http://localhost:8080/file.xml"));

        assertDoesNotThrow(() -> validatorService.consume(feed));
    }
}
