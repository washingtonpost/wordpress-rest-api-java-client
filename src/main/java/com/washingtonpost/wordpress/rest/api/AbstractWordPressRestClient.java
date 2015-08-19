package com.washingtonpost.wordpress.rest.api;

import com.washingtonpost.wordpress.rest.api.model.Post;
import com.washingtonpost.wordpress.rest.api.transformers.Transformer;
import java.io.IOException;
import java.util.List;

/**
 * <p>foo</p>
 * @param <M> The model type
 * @param <T> The transformer type
 */
public class AbstractWordPressRestClient<M extends Post, T extends Transformer> implements WordPressRestClient {

    private Transformer transformer;
    private final String targetUri;

    public AbstractWordPressRestClient(String targetUri) {
        this.targetUri = targetUri;
    }

    @Override
    public void setTransformer(Transformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public List<M> getPosts(String queryParams) throws IOException {
        //use a client to get some JSON
        String json = "{whatever}";
        return this.transformer.transform(json);
    }

}
