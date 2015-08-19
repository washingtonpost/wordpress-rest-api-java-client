package com.washingtonpost.wordpress.rest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>This concrete class models the out-of-the-box fields that the WordPress REST API returns.  Extend it if your organization
 * includes additional attributes in your plugin's response</p>
 */
public class WordPressPost implements Post {

    @JsonProperty("ID")
    private long id;
    @JsonProperty("title")
    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
