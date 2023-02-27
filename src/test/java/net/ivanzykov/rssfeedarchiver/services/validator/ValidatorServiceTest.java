package net.ivanzykov.rssfeedarchiver.services.validator;

import net.ivanzykov.rssfeedarchiver.services.FeedVOFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ValidatorServiceTest {

    @Autowired
    ValidatorService validatorService;

    private static Stream<Arguments> provide_consume_invalidUrl_throwException() {
        return Stream.of(
                Arguments.of(""),
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
}
