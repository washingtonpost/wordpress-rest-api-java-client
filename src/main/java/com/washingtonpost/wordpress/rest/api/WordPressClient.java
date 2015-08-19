package com.washingtonpost.wordpress.rest.api;

import com.washingtonpost.wordpress.rest.api.model.Post;
import java.io.IOException;
import java.util.List;

/**
 * <p>The interface describing how to connect to a WordPress REST API and get a List of Posts out of it</p>
 * @param P The type of model object the implementing Client returns
 */
public interface WordPressClient {

    /**
     * @param queryParams How to query the WordPress API
     * @return A list of {@code T} object matching the queryParams
     */
    List<? extends Post> getPosts(String queryParams) throws IOException;
}
