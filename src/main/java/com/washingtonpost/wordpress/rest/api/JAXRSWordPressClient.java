package com.washingtonpost.wordpress.rest.api;

import com.washingtonpost.wordpress.rest.api.model.Post;
import com.washingtonpost.wordpress.rest.api.transformers.Transformer;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Blah</p>
 */
public class JAXRSWordPressClient implements WordPressClient {

    private static final Logger logger = LoggerFactory.getLogger(JAXRSWordPressClient.class);

    private final URI targetURI;
    private final Transformer<? extends Post> transformer;
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
        logger.debug("Constructing new JAXRSWordPressClient with targetURI '{}' and transformer '{}' "
                + "and requestFilter '{}'", targetURI, transformer, requestFilter);
    }

    @Override
    public List<? extends Post> getPosts(String queryParams) {
        StringBuilder endpoint = new StringBuilder(this.targetURI.toString()).append("/posts");
        if (queryParams != null && !queryParams.isEmpty()) {
            endpoint.append("?").append(queryParams);
        }
        
        logger.debug("JAXRSWordPressClient making request to '{}'", endpoint);

        Response response = jaxrsClient
                .target(endpoint.toString())
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
            String errMsg = String.format("Did not get an OK status code from WordPress API at %s, rather: %s",
                    endpoint, response.getStatusInfo());
            logger.error(errMsg);
            throw new RuntimeException(errMsg);
        }
    }

    /**
     * Visible for testing.
     * @param inputStream An input stream 
     * @return The String content of that inputStream
     */
    String readInputStreamToString(InputStream inputStream) {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer, Charset.forName("UTF-8"));
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return writer.toString();
    }
}
