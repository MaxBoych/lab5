import akka.NotUsed;
import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import org.asynchttpclient.AsyncHttpClient;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;

import java.util.concurrent.Future;

public class AkkaStreams {

    public Flow<HttpRequest, HttpResponse, NotUsed> route(ActorRef cacheActor,
                                                          AsyncHttpClient asyncHttpClient,
                                                          ActorMaterializer materializer) {

        return Flow.of(HttpRequest.class)
                .map(request -> {

                    String URL = request.getUri().query().getOrElse(Config.URL, Config.EMPTY_URL);
                    Integer count = Integer.parseInt(request.getUri().query().getOrElse(Config.COUNT, Config.EMPTY_COUNT));

                    return new GetMessage(URL, count);
                })
                .mapAsync(1, message -> {

                    Future<Object> future = Patterns.ask(cacheActor, message, Config.TIMEOUT_MILLIS);
                        }

    }
}
