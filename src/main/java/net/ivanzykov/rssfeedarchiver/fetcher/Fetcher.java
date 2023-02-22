package net.ivanzykov.rssfeedarchiver.fetcher;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import net.ivanzykov.rssfeedarchiver.feed.Consumer;
import net.ivanzykov.rssfeedarchiver.feed.Feed;
import net.ivanzykov.rssfeedarchiver.feed.FetcherException;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Fetcher implements Consumer {

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
    public Fetcher(RestTemplate restTemplate, SyndFeedInput syndFeedInput, XmlReaderRomeFactory xmlReaderFactory) {
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
    public void consume(Feed feed) {
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
