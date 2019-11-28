import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;

import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class Main {
    public static void main(String[] args) throws IOException {

        ActorSystem system = ActorSystem.create();
        ActorRef routeActor = system.actorOf(Props.create(RouteActor.class));

        Http http = Http.get(system);
        AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();
        ActorMaterializer materializer = ActorMaterializer.create(system);

        Flow<HttpRequest, HttpResponse, NotUsed> flow = new JSRouter().jsRoute(routeActor)
                .flow(system, materializer);

        CompletionStage<ServerBinding> completionStage = http.bindAndHandle(
                flow,
                ConnectHttp.toHost(Config.HOST, Config.PORT),
                materializer
        );

        System.out.println("Server start at http://" + Config.HOST + ":" + Config.PORT);
        System.in.read();


        completionStage.thenCompose(ServerBinding::unbind)
                .thenAccept(sv -> system.terminate());
    }
}
