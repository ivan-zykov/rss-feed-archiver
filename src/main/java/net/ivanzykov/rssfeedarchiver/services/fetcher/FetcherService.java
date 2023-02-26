package net.ivanzykov.rssfeedarchiver.services.fetcher;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import net.ivanzykov.rssfeedarchiver.controller.FeedService;
import net.ivanzykov.rssfeedarchiver.services.Consumer;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Order(2)
public class FetcherService implements Consumer {

    private final RestTemplate restTemplate;
    private final SyndFeedInput syndFeedInput;
    private final XmlReaderRomeFactory xmlReaderFactory;

    /**
     * Constructor of this class.
     *
     * @param restTemplate      restTemplate object requesting the feed over HTTP
     * @param syndFeedInput     syndFeedInput object parsing the XML from the response
     * @param xmlReaderFactory  xmlReaderFactory object creating objects of XML parser
     */
    public FetcherService(RestTemplate restTemplate, SyndFeedInput syndFeedInput, XmlReaderRomeFactory xmlReaderFactory) {
        this.restTemplate = restTemplate;
        this.syndFeedInput = syndFeedInput;
        this.xmlReaderFactory = xmlReaderFactory;
    }

    /**
     * Fetches RSS feed from the URL set in the feed object and sets the result in the feed object.
     *
     * @param feed feed object with the URL to fetch and where to save the result
     */
    @Override
    public void consume(FeedService feed) {
        for (String url : feed.getFeedUrls()) {
            SyndFeed syndFeed = restTemplate.execute(url, HttpMethod.GET, null, response -> {
                try (XmlReader xmlReader = xmlReaderFactory.createXmlReader(response.getBody())) {
                    return syndFeedInput.build(xmlReader);
                } catch (FeedException e) {
                    throw new FetcherException("Could not parse the feed with URL: " + url + e.getLocalizedMessage());
                }
            });
            feed.addFetchedFeed(syndFeed);
        }
    }
}
