package com.washingtonpost.wordpress.rest.api;

import com.washingtonpost.wordpress.rest.api.model.WordPressPost;
import com.washingtonpost.wordpress.rest.api.transformers.WordPressTransformer;

/**
 * <p>Blah</p>
 */
public class WordPressClient extends AbstractWordPressRestClient<WordPressPost, WordPressTransformer> {

    public WordPressClient(String targetUri) {
        super(targetUri);
    }
}
