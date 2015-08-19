package com.washingtonpost.wordpress.rest.api.transformers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.washingtonpost.wordpress.rest.api.model.Post;

/**
 * <p>An Abstract Transformer</p>
 * @param <P> The transformer type
 */
public abstract class AbstractTransformer<P extends Post> implements Transformer<P> {

    protected ObjectMapper objectMapper = new ObjectMapper();

}
