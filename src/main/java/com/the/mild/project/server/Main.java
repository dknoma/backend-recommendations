package com.the.mild.project.server;

import static com.the.mild.project.ResourceConfig.SERVICE_NAME;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import com.the.mild.project.util.CorsFilter;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.the.mild.project.db.mongo.MongoDatabaseFactory;
import org.glassfish.jersey.server.ServerProperties;

/**
 * Main class.
 *
 */
public class Main {
    // Base URI the Grizzly HTTP com.the.mild.project.server will listen on
    /* TODO: Set HOSTNAME and PORT Variables. Do on cloud and for local development */
    public static final String PORT = System.getenv("PORT");
    public static final String BASE_URI = "http://0.0.0.0:" + PORT + "/" + SERVICE_NAME + "/";
    public static final Optional<MongoDatabaseFactory> MONGO_DB_FACTORY;

    static {
        final String using = System.getenv("usingDb");
        final boolean usingDb = using != null && Boolean.parseBoolean(using);

        if(usingDb) {
            MONGO_DB_FACTORY = Optional.of(new MongoDatabaseFactory());
        } else {
            MONGO_DB_FACTORY = Optional.empty();
        }
    }

    /**
     * Starts Grizzly HTTP com.the.mild.project.server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP com.the.mild.project.server.
     */
    public static HttpServer startServer() {
        // create a resource config that scans for JAX-RS resources and providers
        // in com.example package
        final ResourceConfig rc = new ResourceConfig().packages("com.the.mild.project.server.resources");

        rc.register(new CorsFilter());
//        rc.property("jersey.config.server.wadl.disableWadl", true);
//        rc.property(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
        // create and start a new instance of grizzly http com.the.mild.project.server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Main method.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println("PORT: " + System.getenv("PORT"));
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at %sapplication.wadl\nHit enter to stop it...",
                                         BASE_URI));

    }
}

