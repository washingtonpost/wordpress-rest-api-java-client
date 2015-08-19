package com.washingtonpost.wordpress.rest.api;

import com.washingtonpost.wordpress.rest.api.model.Post;
import com.washingtonpost.wordpress.rest.api.transformers.Transformer;
import java.io.IOException;
import java.util.List;

/**
 * <p>Blah</p>
 */
public class JAXRSWordPressClient implements WordPressClient {

    private final String targetUri;
    private final Transformer transformer;


    public JAXRSWordPressClient(String targetUri, Transformer<? extends Post> transformer) {
        this.targetUri = targetUri;
        this.transformer = transformer;
    }

    @Override
    public List<? extends Post> getPosts(String queryParams) throws IOException {
        //use a client to get some JSON
        String json = "{whatever}";
        return this.transformer.transform(json);
    }
}
