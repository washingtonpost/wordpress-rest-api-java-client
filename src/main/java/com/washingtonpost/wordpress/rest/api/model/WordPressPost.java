package com.washingtonpost.wordpress.rest.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>This concrete class models the out-of-the-box fields that the WordPress REST API returns.  Extend it if your organization
 * includes additional attributes in your plugin's response</p>
 * <p>From the wp-api.org documentation, the following fields are the out-of-the-box values understood and modeled by this
 * POJO:</p>
 * <pre>
 * title - Title of the post. (string) required
 * content_raw - Full text of the post. (string) required
 * excerpt_raw - Text for excerpt of the post. (string) optional
 * name - Slug of the post. (string) optional
 * status - Post status of the post: draft, publish, pending, future, private, or any custom registered status.
 *         If providing a status of future, you must specify a date in order for the post to be published as expected.
 *         Default is draft. (string) optional
 * type - Post type of the post: post, page, link, nav_menu_item, or a any custom registered type.
 *        Default is post. (string) optional
 * date - Date and time the post was, or should be, published in local time. Date should be an RFC3339 timestamp]
 *        (http://tools.ietf.org/html/rfc3339). Example: 2014-01-01T12:20:52Z. Default is the local date and time.
 *        (string) optional
 * date_gmt - Date and time the post was, or should be, published in UTC time. Date should be an RFC3339 timestamp.
 *            Example: 201401-01T12:20:52Z. Default is the current GMT date and time. (string) optional
 * author - Author of the post. Author can be provided as a string of the author’s ID or as the User object of the author.
 *          Default is current user. (object | string) optional
 * password - Password for protecting the post. Default is empty string. (string) optional
 * post_parent - Post ID of the post parent. Default is 0. (integer) optional
 * post_format - Format of the post. Default is standard. (string) optional
 * menu_order - The order in which posts specified as the page type should appear in supported menus.
 *              Default 0. (integer) optional
 * comment_status - Comment status for the post: open or closed. Indicates whether users can submit comments to the post.
 *                  Default is the option ‘default_comment_status’, or ‘closed’. (string) optional
 * ping_status - Ping status for the post: open or closed. Indicates whether users can submit pingbacks or trackbacks to
 *              the post. Default is the option ‘default_ping_status’. (string) optional
 * sticky - Sticky status for the post: true or false. Default is false. (boolean) optional
 * post_meta - Post meta entries of the post. Post meta should be an array of one or more Meta objects for each post meta
 *             entry. See the Create Meta for a Post endpoint for the key value pairs. (array) optional
 * </pre>
 */
public class WordPressPost implements Post {

    @JsonProperty(value="title", required=true)
    private String title;
    @JsonProperty(value="ID", required=true)
    private long id;
    @JsonProperty(value="content_raw", required=true)
    private String contentRaw;
    @JsonProperty(value="excerpt_raw")
    private String excerptRaw;
    @JsonProperty(value="name")
    private String name;
    @JsonProperty("status")
    private String status;
    @JsonProperty("type")
    private String type;
    @JsonProperty("date")
    private String date;
    @JsonProperty("date_gmt")
    private String dateGMT;
    @JsonProperty("author")
    private String author;
    @JsonProperty("password")
    private String password;
    @JsonProperty("post_parent")
    private long postParent;
    @JsonProperty("post_format")
    private String postFormat;
    @JsonProperty("menu_order")
    private int menuOrder;
    @JsonProperty("comment_status")
    private String commentStatus;
    @JsonProperty("ping_status")
    private String pingStatus;
    @JsonProperty("sticky")
    private boolean sticky;
    @JsonProperty("post_meta")
    private String postMeta;

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

    public String getContentRaw() {
        return contentRaw;
    }

    public void setContentRaw(String contentRaw) {
        this.contentRaw = contentRaw;
    }

    public String getExcerptRaw() {
        return excerptRaw;
    }

    public void setExcerptRaw(String excerptRaw) {
        this.excerptRaw = excerptRaw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateGMT() {
        return dateGMT;
    }

    public void setDateGMT(String dateGMT) {
        this.dateGMT = dateGMT;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPostParent() {
        return postParent;
    }

    public void setPostParent(long postParent) {
        this.postParent = postParent;
    }

    public String getPostFormat() {
        return postFormat;
    }

    public void setPostFormat(String postFormat) {
        this.postFormat = postFormat;
    }

    public int getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(int menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getPingStatus() {
        return pingStatus;
    }

    public void setPingStatus(String pingStatus) {
        this.pingStatus = pingStatus;
    }

    public boolean isSticky() {
        return sticky;
    }

    public void setSticky(boolean sticky) {
        this.sticky = sticky;
    }

    public String getPostMeta() {
        return postMeta;
    }

    public void setPostMeta(String postMeta) {
        this.postMeta = postMeta;
    }
}
