package com.washingtonpost.wordpress.rest.api;

import com.washingtonpost.wordpress.rest.api.model.Post;
import com.washingtonpost.wordpress.rest.api.transformers.Transformer;
import com.washingtonpost.wordpress.rest.api.transformers.WordPressTransformer;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.xml.bind.DatatypeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>A Factory/Builder for creating a concrete JAXRSWordPressClient</p>
 * <p>Usage is fluent, like:</p>
 * <pre>
 *     JAXRSWordPressClient myClient = (new JAXRSWordPressFactory())
 *                                          .withBaseUrl("http://wordpress.foo.com/wp-json")
 *                                          .withCredentials("joe", "secret")
 *                                          .withTransformer(new MySpecialTransformer())
 *                                          .build();
 * </pre>
 * <p>You can also create a Mock Client by specifying a classpath resource containing an array of Post JSON, like:</p>
 * <pre>
 *     MockWordPressClient myClient = (new JAXRSWordPressFactory())
 *                                          .withMockResource("/some/fixture/on/my/classpath.blah")
 *                                          .build();
 * </pre>
 */
public class JAXRSWordPressClientFactory {
    private static final Logger logger = LoggerFactory.getLogger(JAXRSWordPressClientFactory.class);
    private ClientRequestFilter clientRequestFilter;
    private Transformer<? extends Post> transformer = new WordPressTransformer();
    private URI uri;
    private String mockResource = null;

    public JAXRSWordPressClientFactory() {

    }


    public JAXRSWordPressClientFactory withCredentials(String username, String password) {
        this.clientRequestFilter = new ClientRequestFilter() {
            private final String charset = StandardCharsets.UTF_8.name();

            @Override
            public void filter(ClientRequestContext requestContext) throws IOException {

                String token = String.format("%s:%s", username, password);

                String basicAuthHeader = null;

                try {
                    basicAuthHeader = "BASIC " + DatatypeConverter.printBase64Binary(token.getBytes(charset));
                }
                catch (UnsupportedEncodingException ex) {
                    throw new IllegalStateException("Cannot encode with " + charset, ex);
                }

                MultivaluedMap<String, Object> headers = requestContext.getHeaders();
                headers.add("Authorization", basicAuthHeader);

                logger.trace("Added BasicAuth filter with username {}", username);
            }
        };
        return this;
    }

    public JAXRSWordPressClientFactory withBaseUrl(String baseUrl) {
        try {
            this.uri = new URI(baseUrl);
        }
        catch (URISyntaxException e) {
            logger.error("Unable to turn {} into a URI", baseUrl, e);
        }
        return this;
    }

    public JAXRSWordPressClientFactory withMockResource(String mockResource) {
        this.mockResource = mockResource;
        return this;
    }

    public JAXRSWordPressClientFactory withTransformer(Transformer<? extends Post> transformer) {
        this.transformer = transformer;
        return this;
    }

    public WordPressClient build() {
        if (this.mockResource != null) {
            return new MockWordPressClient(this.mockResource, transformer);
        }
        if (this.uri == null) {
            throw new IllegalStateException("A 'baseUrl' property is required when constructing a JAXRS client");
        }

        return new JAXRSWordPressClient(this.uri,
                                        this.clientRequestFilter,
                                        this.transformer);
    }

}
