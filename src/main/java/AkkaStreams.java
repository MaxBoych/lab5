import akka.NotUsed;
import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.Source;
import org.asynchttpclient.AsyncHttpClient;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;

import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

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
                .mapAsync(1, getMessage -> {

                    Future<Object> future = Patterns.ask(cacheActor, getMessage, Config.TIMEOUT_MILLIS);
                    CompletionStage<Object> stage = FutureConverters.toJava(future);

                    stage.thenCompose(resultMessage -> {

                        if (((ResultMessage) resultMessage).isSuccess()) {
                            return CompletableFuture.completedFuture(resultMessage);
                        } else {

                            return Source.from(Collections.singletonList(getMessage))
                                    .toMat(
                                            Flow.<Pair<HttpRequest, Integer>>create()
                                    )
                        }
                    })
                }

    }
}
