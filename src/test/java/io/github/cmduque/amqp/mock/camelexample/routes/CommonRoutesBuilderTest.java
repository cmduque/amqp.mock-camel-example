package io.github.cmduque.amqp.mock.camelexample.routes;

import com.rabbitmq.client.AMQP;
import io.github.cmduque.amqp.mock.AMQPServerMock;
import io.github.cmduque.amqp.mock.dto.Message;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static io.github.cmduque.amqp.mock.dto.ServerConfig.defaultConfig;

public class CommonRoutesBuilderTest extends CamelTestSupport {

    AMQPServerMock server;

    @Before
    public void beforeMethod() {
        server = new AMQPServerMock(defaultConfig());
        server.start();
    }

    @Test
    public void commonRoutesMustReadFromQueueAndPublishEvent() throws Exception {
        String key = "book.received";
        CountDownLatch lockForMessages = server.getLockForMessages(key, 1);
        String queue = "arrived";
        String payload = "Camel in Action";
        server.publish(new Message("", queue, new AMQP.BasicProperties.Builder().build(), payload.getBytes()));

        CommonRoutesBuilder route = new CommonRoutesBuilder();
        context.getPropertiesComponent().setLocation("classpath:properties/default.properties");
        context.addRoutes(route);
        context.start();
        lockForMessages.await(10, TimeUnit.SECONDS);

        List<Message> receivedMessages = server.getAllReceivedMessages(key);
        assertEquals(1, receivedMessages.size());
        assertEquals("Received:" + payload, new String(receivedMessages.get(0).getBody()));
    }

    @After
    public void afterMethod() {
        server.stop();
    }
}