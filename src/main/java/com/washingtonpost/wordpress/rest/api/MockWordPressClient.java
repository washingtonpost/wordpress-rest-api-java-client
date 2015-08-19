package com.washingtonpost.wordpress.rest.api;

import com.washingtonpost.wordpress.rest.api.model.Post;
import com.washingtonpost.wordpress.rest.api.transformers.Transformer;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * <p>A Mock Client, helping for testing and localhost development</p>
 */
public class MockWordPressClient implements WordPressClient {
    private List<? extends Post> posts;

    public MockWordPressClient(String classpathFixture, Transformer<? extends Post> transformer) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(classpathFixture);
        String jsonFixture = StringUtils.inputStreamToString(inputStream);
        try {
            this.posts = transformer.transform(jsonFixture);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<? extends Post> getPosts(String queryParams) {
        return posts;
    }
}
