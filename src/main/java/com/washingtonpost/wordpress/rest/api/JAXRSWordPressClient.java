package com.washingtonpost.wordpress.rest.api;

import com.washingtonpost.wordpress.rest.api.model.Post;
import com.washingtonpost.wordpress.rest.api.transformers.Transformer;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Blah</p>
 */
public class JAXRSWordPressClient implements WordPressClient {

    private static final Logger logger = LoggerFactory.getLogger(JAXRSWordPressClient.class);

    private final Client jaxrsClient;
    private final String targetUri;
    private final Transformer transformer;


    public JAXRSWordPressClient(String targetUri, Transformer<? extends Post> transformer) {
        this.targetUri = targetUri;
        this.transformer = transformer;
        this.jaxrsClient = null;
    }

    @Override
    public List<? extends Post> getPosts(String queryParams) throws IOException {
        //use a client to get some JSON
        String json = "[{\"title\":\"This is a title!\", \"ID\":123}]";
        return this.transformer.transform(json);
    }
}
