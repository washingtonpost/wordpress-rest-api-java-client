package com.washingtonpost.wordpress.rest.api.transformers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.washingtonpost.wordpress.rest.api.model.Post;

/**
 * <p>An Abstract Transformer</p>
 * @param <T> The transformer type
 */
public abstract class AbstractTransformer<T extends Post> implements Transformer<T> {

    protected ObjectMapper objectMapper = new ObjectMapper();

}
