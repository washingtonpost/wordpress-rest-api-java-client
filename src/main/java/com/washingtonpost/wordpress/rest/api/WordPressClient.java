package com.washingtonpost.wordpress.rest.api;

import com.washingtonpost.wordpress.rest.api.model.Post;
import java.util.List;

/**
 * <p>The interface describing how to connect to a WordPress REST API and get a List of Posts out of it</p>
 * @param <P> The type of the Post
 */
public interface WordPressClient<P extends Post> {

    /**
     * @param queryParams How to query the WordPress API
     * @return A list of {@code P} object matching the queryParams.
     */
    List<P> getPosts(String queryParams);
}
