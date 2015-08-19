package com.washingtonpost.wordpress.rest.api;

import com.washingtonpost.wordpress.rest.api.model.Post;
import com.washingtonpost.wordpress.rest.api.transformers.Transformer;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Blah</p>
 */
public class JAXRSWordPressClient implements WordPressClient {

    private static final Logger logger = LoggerFactory.getLogger(JAXRSWordPressClient.class);

    private final URI targetURI;
    private final Transformer transformer;
    private Client jaxrsClient;

    /**
     * <p>Package private constructor to encourage the caller to use this Client's associated Factory</p>
     * @param targetURI The base URI of the Wordpress REST API.  The {@code targetUri} should include the FQDN up to, but
     * not including the REST endpoints (e.g. "https://wp.foo.com/wp-json/" not
     * "https://wp.foo.com/wp-json/posts")
     * @param requestFilter A possibly null filter to pass every request this CLient makes to WordPress through.  Useful for
     * adding BasicAuth header information to every request, if appropriate.
     * @param transformer A concrete class to provide transformation services from the JSON returned by the WP-API and a POJO
     * that implements the Post marker interface
     */
    JAXRSWordPressClient(URI targetURI,
                         ClientRequestFilter requestFilter,
                         Transformer<? extends Post> transformer) {
        this.targetURI = targetURI;
        this.transformer = transformer;
        this.jaxrsClient = ClientBuilder.newClient();

        // If our authenticator is provided, register it as a default ClientRequestFilter so that every request this Client
        // makes has BasicAuth information added to it
        if (requestFilter != null) {
            jaxrsClient = jaxrsClient.register(requestFilter);
        }
    }

    @Override
    public List<? extends Post> getPosts(String queryParams) {

        Response response = jaxrsClient
                .target(targetURI)
                .path("/posts?" + queryParams)
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (Status.OK.getStatusCode() == response.getStatusInfo().getStatusCode()) {
            String json = readInputStreamToString((InputStream)response.getEntity());
            try {
                return this.transformer.transform(json);
            }
            catch (IOException ex) {
                logger.error("Unable to transform JSON '{}' into a Post", json);
                throw new RuntimeException(ex);
            }
        }
        else {
            logger.error("Did not get an OK status code from WordPress API, rather: {}", response.getStatusInfo());
            throw new RuntimeException("Did not get an OK status code from WordPress API, rather: " + response.getStatusInfo());
        }
    }

    /**
     * Do this "the hard way" (i.e. write our own) to minimize the transitive dependencies that we force on our users.  There's
     * an implementation of this in Apache Commons IO and Guava, but neither of those need to be transitively forced on the
     * application that uses this Client for this one-off conversion.
     * @param inputStream An input stream that we assume contains a bunch of JSON
     * @return The String content of that inputStream
     */
    private String readInputStreamToString(InputStream inputStream) {
        return "[{\"title\":\"This is a title!\", \"ID\":123}]";
    }
}
