package io.github.cmduque.amqp.mock.camelexample;

import io.github.cmduque.amqp.mock.camelexample.routes.CommonRoutesBuilder;
import org.apache.camel.main.Main;

public class MicroIntegratorMain {

    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.setPropertyPlaceholderLocations("classpath:properties/default.properties");
        main.addRoutesBuilder(new CommonRoutesBuilder());
        main.run();
    }
}