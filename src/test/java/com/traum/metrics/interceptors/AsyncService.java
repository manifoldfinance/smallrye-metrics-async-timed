package com.traum.metrics.interceptors;

import com.traum.microprofile.metrics.annotation.AsyncTimed;

import javax.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.concurrent.*;

@ApplicationScoped
public class AsyncService {

  ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

  @AsyncTimed
  public CompletionStage<Duration> returnCompletionStage(Duration delay) {
    return execute(delay);
  }

  @AsyncTimed(name = "relative_name")
  public CompletionStage<Duration> returnCompletionStageWithRelativeName(Duration delay) {
    return execute(delay);
  }

  @AsyncTimed(name = "absolute_name", absolute = true)
  public CompletionStage<Duration> returnCompletionStageWithAbsoluteName(Duration delay) {
    return execute(delay);
  }

  @AsyncTimed(name = "with_exception", absolute = true)
  public CompletionStage<Duration> returnCompletionStageWithException(Duration delay) {
    throw new RuntimeException("Failed fast");
  }

  @AsyncTimed(name = "with_failure", absolute = true)
  public CompletionStage<Duration> returnCompletionStageWithFailure(Duration delay) {
    return CompletableFuture.failedStage(new RuntimeException("Failed later"));
  }

  private CompletionStage<Duration> execute(Duration delay) {
    final CompletableFuture<Duration> result = new CompletableFuture<>();
    executorService.schedule(
        () -> {
          result.complete(delay);
        },
        delay.toMillis(),
        TimeUnit.MILLISECONDS);
    return result;
  }
}
