package com.washingtonpost.wordpress.rest.api;

import com.washingtonpost.wordpress.rest.api.model.WordPressPost;
import com.washingtonpost.wordpress.rest.api.transformers.WordPressTransformer;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * <p>Tests the JAXRSWordPressClient</p>
 */
public class TestJAXRSWordPressClient {

    @Test
    public void testHappyPath() throws IOException {
        WordPressClient client = new JAXRSWordPressClient("http://blah.com",
                                                          new WordPressTransformer());
        List<WordPressPost> posts = client.getPosts("?whatever=true&foo=bar");

        assertNotNull("List of posts should not be null", posts);
        assertEquals(1, posts.size());
        assertEquals(123, posts.get(0).getId());
    }
}
