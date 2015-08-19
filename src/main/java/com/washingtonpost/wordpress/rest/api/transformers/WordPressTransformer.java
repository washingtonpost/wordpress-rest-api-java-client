package com.washingtonpost.wordpress.rest.api.transformers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.washingtonpost.wordpress.rest.api.model.WordPressPost;
import java.io.IOException;
import java.util.List;

/**
 * <p>Transformer for the standard WordPress response</p>
 */
public class WordPressTransformer extends AbstractTransformer<WordPressPost> {

    @Override
    public List<WordPressPost> transform(String json) throws IOException {
        return super.objectMapper.readValue(json,  new TypeReference<List<WordPressPost>>() {});
    }
}
