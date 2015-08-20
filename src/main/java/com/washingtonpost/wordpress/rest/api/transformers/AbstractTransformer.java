package com.washingtonpost.wordpress.rest.api.transformers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.washingtonpost.wordpress.rest.api.model.Post;

/**
 * <p>An Abstract Transformer</p>
 * @param <P> The transformer type
 */
public abstract class AbstractTransformer<P extends Post> implements Transformer<P> {

    protected ObjectMapper objectMapper;

    public AbstractTransformer() {
        objectMapper = new ObjectMapper();
        // FIXME remove this once the Objects actually map the JSON spec
//        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
