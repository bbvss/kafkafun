package org.acme.kafka;

import io.smallrye.mutiny.Multi;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.Random;

/**
 * A bean producing random prices every 5 seconds.
 * The prices are written to a Kafka topic (prices). The Kafka configuration is specified in the application configuration.
 */
@ApplicationScoped
public class PriceGenerator {

    private Random random = new Random();

    @Outgoing("generated-price")
    public Multi<Integer> generate() {
        return Multi.createFrom()
                .ticks()
                .every(Duration.ofSeconds(5))
                .onOverflow()
                .drop()
                .map(
                        tick -> {
                            final int i = random.nextInt(100);
                            System.out.println("i = " + i);
                            return i;
                        });
    }

}