# wordpress-rest-api-java-client
Java Client for connecting to and parsing JSON coming our of a WordPress REST API plugin

This Client uses JAX-RS to connect to a remote WordPress REST API and retrieve the lists of Posts from the /posts endpoint.  The client passes through whatever query parameters sent to the `getPosts( )` method directly to the WordPress REST API.  This client comes with a default implementation of a Transformer class that knows how to transform WordPress JSON into a "WordPressPost.java" POJO.

If your WordPress JSON feed is customized to include fields not contained in the off-the-shelf WordPress JSON API installation, you can construct an instance of a WordPressClient with your own Transformer/POJO pair that's suitable for your organization.

## Including this JAR

Add this dependency to your Maven POM:
```
<dependency>
    <groupId>com.washingtonpost.wordpress</groupId>
    <artifactId>wordpress-rest-api-client</artifactId>
    <version>x.y.z</version>  // Check RELEASE_NOTES.md for the best version for your needs
</dependency>
```

## Constructing a default client with BasicAuth autentication:
```
import com.washingtonpost.wordpress.rest.api.JAXRSWordPressClient;
import com.washingtonpost.wordpress.rest.api.JAXRSWordPressFactory;

public class FooClazz {

    public void myMethod() {
        JAXRSWordPressClient myClient = (new JAXRSWordPressFactory())
                                               .withBaseUrl("http://wordpress.foo.com/wp-json")
                                               .withCredentials("joe", "secret")
                                               .build();
        ...
    }
```

## Testing with this client
This client ships with a MockWordPressClient that loads a classpath resource and returns the JSON contents of that resource as fixture data, like so:
```
import com.washingtonpost.wordpress.rest.api.model.WordPressPost;

public class TestJAXRSWordPressClient {

    @Test
    public void testHappyPath() throws IOException {
        WordPressClient client = new WordPressClientFactory()
                                        .withMockResource("off-the-shelf-response.json")
                                        .build();
        ...
    }
```

## Extending Functionality
To use this client to parse a different batch of JSON than comes with the standard WordPress REST API, implement 2 classes:

* implement com.washingtonpost.wordpress.rest.api.model.Post  // just a marker interface
* extend the com.washingtonpost.wordpress.rest.api.transformers.AbstractTransformer (or just implement your own Transformer)

Register your transformer with the Client when you use the ClientFactory to create your Client instance, like:
```
JAXRSWordPressClient myClient = (new JAXRSWordPressFactory())
                                               .withTransformer(new MySpecialTransformer())
                                               .withBaseUrl("http://wordpress.foo.com/wp-json")
                                               .build();
```

# What this Client doesn't (yet) support

* OAuth authentication
* Any REST method other than a GET to the WordPress API's /posts endpoint
