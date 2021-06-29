package io.github.cmduque.amqp.mock.camelexample.routes;

import org.apache.camel.builder.RouteBuilder;

public class CommonRoutesBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("rabbitmq:{{rabbitmq.exchange}}?hostname={{rabbitmq.host}}&portNumber={{rabbitmq.portNumber}}&queue={{rabbitmq.routingKey}}")
                .removeHeaders("*")
                .setBody(simple("Received:${body}"))
                .to("rabbitmq:{{rabbitmq.exchange}}?hostname={{rabbitmq.host}}&portNumber={{rabbitmq.portNumber}}&routingKey={{rabbitmq.replyRoutingKey}}")
        ;
    }
}
