import akka.NotUsed;
import akka.actor.ActorRef;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.asynchttpclient.AsyncHttpClient;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AkkaStreams {

    public Flow<HttpRequest, HttpResponse, NotUsed> route(ActorRef cacheActor,
                                                          AsyncHttpClient asyncHttpClient,
                                                          ActorMaterializer materializer) {

        return Flow.of(HttpRequest.class)
                .map(request -> {

                })
    }
}
