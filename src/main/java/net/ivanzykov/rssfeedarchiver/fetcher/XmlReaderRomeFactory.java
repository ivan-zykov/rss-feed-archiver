package net.ivanzykov.rssfeedarchiver.fetcher;

import com.rometools.rome.io.XmlReader;

import java.io.IOException;
import java.io.InputStream;

public class XmlReaderRomeFactory {

    private XmlReaderRomeFactory() {
    }

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
