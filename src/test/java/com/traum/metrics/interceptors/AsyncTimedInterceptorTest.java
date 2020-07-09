package com.traum.metrics.interceptors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.CompletionStage;
import javax.inject.Inject;
import org.awaitility.Awaitility;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.SimpleTimer;
import org.jboss.weld.junit5.WeldInitiator;
import org.jboss.weld.junit5.WeldJunit5Extension;
import org.jboss.weld.junit5.WeldSetup;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(WeldJunit5Extension.class)
class AsyncTimedInterceptorTest {

  @WeldSetup
  WeldInitiator initiator =
      WeldInitiator.of(
          WeldInitiator.createWeld()
              .beanClasses(AsyncService.class, MetricRegistryProducer.class)
              .packages(AsyncTimedInterceptor.class)
              .interceptors(AsyncTimedInterceptor.class));

  @Inject AsyncService service;

  @Inject MetricRegistry metricRegistry;

  @Test
  void asyncTimedCompletableFuture() {
    final Duration delay = Duration.ofSeconds(2);
    final CompletionStage<Duration> result = service.returnCompletionStage(delay);

    Awaitility.await()
        .atMost(Duration.ofSeconds(3))
        .untilAsserted(
            () -> {
              final String metricName =
                  AsyncService.class.getCanonicalName() + ".returnCompletionStage";
              assertTimer(metricName, delay.toMillis(), 1);
            });
  }

  @Test
  void asyncTimedCompletableFutureWithRelativeName() {
    final Duration delay = Duration.ofSeconds(2);
    final CompletionStage<Duration> result = service.returnCompletionStageWithRelativeName(delay);

    Awaitility.await()
        .atMost(Duration.ofSeconds(3))
        .untilAsserted(
            () -> {
              final String metricName = AsyncService.class.getCanonicalName() + ".relative_name";
              assertTimer(metricName, delay.toMillis(), 1);
            });
  }

  @Test
  void asyncTimedCompletableFutureWithAbsoluteName() {
    final Duration delay = Duration.ofSeconds(2);
    final CompletionStage<Duration> result = service.returnCompletionStageWithAbsoluteName(delay);

    Awaitility.await()
        .atMost(Duration.ofSeconds(3))
        .untilAsserted(
            () -> {
              final String metricName = "absolute_name";
              assertTimer(metricName, delay.toMillis(), 1);
            });
  }

  @Test
  void asyncTimedCompletableFutureWithException() {
    assertThrows(
        RuntimeException.class,
        () -> {
          service.returnCompletionStageWithException(Duration.ofSeconds(2));
        });

    Awaitility.await()
        .atMost(Duration.ofSeconds(3))
        .untilAsserted(
            () -> {
              final String metricName = "with_exception";
              assertTimer(metricName, 0, 1);
            });
  }

  @Test
  void asyncTimedCompletableFutureWithFailure() {
    final Duration delay = Duration.ofSeconds(2);
    final CompletionStage<Duration> result = service.returnCompletionStageWithFailure(delay);

    Awaitility.await()
        .atMost(Duration.ofSeconds(3))
        .untilAsserted(
            () -> {
              final String metricName = "with_failure";
              assertTimer(metricName, 0, 1);
            });
  }

  private void assertTimer(String metricName, long delayInMillis, int count) {
    final Optional<SimpleTimer> timer = getSimpleTimer(metricName);
    assertTrue(timer.isPresent(), "Missing timer " + metricName);
    assertTimer(delayInMillis, timer.get(), count);
  }

  private void assertTimer(long delayInMillis, SimpleTimer timer, int count) {
    assertEquals(count, timer.getCount(), "Unexpected count");
    assertEquals(delayInMillis, timer.getElapsedTime().toMillis(), 10, "Unexpected measured time");
  }

  private Optional<SimpleTimer> getSimpleTimer(String name) {
    return metricRegistry
        .getSimpleTimers()
        .entrySet()
        .stream()
        .filter(entry -> entry.getKey().getName().equals(name))
        .map(Entry::getValue)
        .findFirst();
  }
}
