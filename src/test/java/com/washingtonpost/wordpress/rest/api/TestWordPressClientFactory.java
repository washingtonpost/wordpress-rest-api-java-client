package com.washingtonpost.wordpress.rest.api;

import com.google.common.collect.ImmutableList;
import com.washingtonpost.wordpress.rest.api.model.Post;
import com.washingtonpost.wordpress.rest.api.transformers.Transformer;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * <p>Tests the WordPressClientFactory</p>
 */
@SuppressWarnings("unchecked")
public class TestWordPressClientFactory {

    @Test(expected=IllegalArgumentException.class)
    public void testFactoryChokesOnBadBaseUrl() {
        new WordPressClientFactory().withBaseUrl("ggg:\\bad.format").build();
    }

    @Test
    public void testFactoryWithFakeBuilder() {
        WordPressClient<FakePost> client = new WordPressClientFactory()
                .withMockResource("off-the-shelf-response.json")
                .withTransformer(new FakeTransformer())
                .build();

        List<FakePost> posts = client.getPosts("?whatever=true&foo=bar");
        assertEquals(1, posts.size());
        assertEquals("foo", posts.get(0).value);
    }

    private class FakeTransformer implements Transformer<FakePost> {

        @Override
        public List<FakePost> transform(String json) throws IOException {
            return ImmutableList.of(new FakePost());
        }
    }

    private class FakePost implements Post {
        String value = "foo";
    }
}
