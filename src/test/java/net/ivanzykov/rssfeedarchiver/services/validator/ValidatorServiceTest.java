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

    private static Stream<Arguments> provide_invalid_urls() {
        return Stream.of(
                Arguments.of("/wrongUrl"),
                Arguments.of("ftp://site.com/abc.txt")
        );
    }

    @ParameterizedTest
    @MethodSource("provide_invalid_urls")
    void throw_an_exception_for_an_invalid_url(String url, @Autowired ValidatorService sut) {
        var feed = FeedVOFactory.create(List.of(url));

        Exception exception = assertThrows(InvalidFeedUrlException.class, () ->
                sut.consume(feed));

        assertEquals("Following feed URL is invalid: " + url, exception.getMessage());
    }

    @Test
    void throw_an_exception_for_an_empty_url(@Autowired ValidatorService sut) {
        var feed = FeedVOFactory.create(List.of(""));

        Exception exception = assertThrows(InvalidFeedUrlException.class, () ->
                sut.consume(feed));

        assertEquals("One of the provided URLs is empty", exception.getMessage());
    }

    @Test
    void consider_a_url_with_localhost_as_valid(@Autowired ValidatorService sut) {
        var feed = FeedVOFactory.create(List.of("http://localhost:8080/file.xml"));

        assertDoesNotThrow(() -> sut.consume(feed));
    }
}
