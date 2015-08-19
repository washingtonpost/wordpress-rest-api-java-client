package com.washingtonpost.wordpress.rest.api.transformers;

import com.washingtonpost.wordpress.rest.api.model.Post;
import java.io.IOException;
import java.util.List;

/**
 * <p>An interface describing how to transform JSON into a Model object</p>
 * @param <P> The type of post this Transformer produces.
 */
public interface Transformer<P extends Post> {

    List<P> transform(String json) throws IOException;
}
