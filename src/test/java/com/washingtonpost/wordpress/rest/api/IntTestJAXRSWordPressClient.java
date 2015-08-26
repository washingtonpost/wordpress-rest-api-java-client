package com.washingtonpost.wordpress.rest.api;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.washingtonpost.wordpress.rest.api.model.WordPressPost;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;

/**
 * <p>Uses WireMock to assert what the client is actually requesting on the wire of our "server"</p>
 */
@SuppressWarnings("unchecked")
public class IntTestJAXRSWordPressClient {

    private final int port = 8089;
    private final String url = String.format("http://localhost:%d/wp-json", port);
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(port);


    @Test
    public void testUnauthenticatedGetPosts() {
        stubFor(get(urlMatching("/wp-json/posts\\?foo=bar"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"title\":\"Some title\"}]")));

        WordPressClient<WordPressPost> client = (new WordPressClientFactory()).withBaseUrl(url).build();

        List<WordPressPost> posts = client.getPosts("foo=bar");

        assertEquals("Some title", posts.get(0).getTitle());

        verify(getRequestedFor(urlMatching("/wp-json/posts\\?foo=bar"))
                .withHeader("Accept", equalTo("application/json")));
    }

    @Test
    public void testAuthenticatedGetPosts() throws UnsupportedEncodingException {
        String token = String.format("%s:%s", "tom", "secret");
        String basicAuthHeader = "BASIC " + DatatypeConverter.printBase64Binary(token.getBytes(StandardCharsets.UTF_8.name()));

        stubFor(get(urlMatching("/wp-json/posts\\?foo=bar"))
                .withHeader("Authorization", equalTo(basicAuthHeader))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"title\":\"A different title\"}]")));

        WordPressClient<WordPressPost> client = (new WordPressClientFactory())
                .withBaseUrl(url)
                .withCredentials("tom", "secret")
                .build();

        List<WordPressPost> posts = client.getPosts("foo=bar");

        assertEquals("A different title", posts.get(0).getTitle());

        verify(getRequestedFor(urlMatching("/wp-json/posts\\?foo=bar"))
                .withHeader("Authorization", equalTo(basicAuthHeader))
                .withHeader("Accept", equalTo("application/json")));
    }

    @Test(expected=RuntimeException.class)
    public void testServerReturnsBadJSON() {
        stubFor(get(urlMatching("/wp-json/posts\\?foo=bar"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("this is not well-formed jsonb")));

        WordPressClient<WordPressPost> client = (new WordPressClientFactory()).withBaseUrl(url).build();

        client.getPosts("foo=bar");
    }

    @Test(expected=RuntimeException.class)
    public void testBadStatusCodeThrowsRuntimeException() {
        stubFor(get(urlMatching("/wp-json/posts\\?foo=bar"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("")));

        WordPressClient<WordPressPost> client = (new WordPressClientFactory()).withBaseUrl(url).build();

        client.getPosts("foo=bar");
    }

}
