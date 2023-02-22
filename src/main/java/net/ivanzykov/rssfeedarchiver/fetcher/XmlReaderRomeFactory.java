package net.ivanzykov.rssfeedarchiver.fetcher;

import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class XmlReaderRomeFactory {

    /**
     * Creates a new instance of {@link XmlReader} with provided input stream
     *
     * @param inputStream inputStream of XML file
     * @return xmlReader object with content of the input XML file
     * @throws IOException if there was a problem reading the input
     */
    public XmlReader createXmlReader(InputStream inputStream) throws IOException {
        return new XmlReader(inputStream);
    }
}
