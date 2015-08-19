package com.washingtonpost.wordpress.rest.api;

import com.washingtonpost.wordpress.rest.api.model.Post;
import com.washingtonpost.wordpress.rest.api.transformers.Transformer;
import java.io.IOException;
import java.util.List;

/**
 * <p>The interface describing how to connect to a WordPress REST API and get a List of Posts out of it</p>
 * @param M The type of model object the implementing Client returns
 * @param T The transformer to use to convert json to a POJO of type M
 */
interface WordPressRestClient<M extends Post, T extends Transformer> {

    /**
     * @param transformer the transformer to use to convert JSON to a POJO of type M
     */
    void setTransformer(T transformer);

    /**
     * @param queryParams How to query the WordPress API
     * @return A list of {@code T} object matching the queryParams
     */
    List<M> getPosts(String queryParams) throws IOException;
}
