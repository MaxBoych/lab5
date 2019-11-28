import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;
import java.util.Map;

public class CacheActor extends AbstractActor {

    private Map<String, Integer> cache = new HashMap<>();

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(PutMessage.class, message ->
                        cache.put(message.getURL(), message.getResponseTime())
                )
                .match(GetMessage.class, message -> {

                    if (cache.containsKey(message.getURL())) {
                        sender().tell(new ResultMessage(message.getURL(), cache.get(message.getURL()), true), ActorRef.noSender());
                    } else {
                        sender().tell(new ResultMessage(message.getURL(), Integer.MIN_VALUE, false), ActorRef.noSender());
                    }
                })
                .build();
    }
}
