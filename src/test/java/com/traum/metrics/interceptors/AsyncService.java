package com.traum.metrics.interceptors;

import com.traum.microprofile.metrics.annotation.AsyncSimplyTimed;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AsyncService {

  ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

  @AsyncSimplyTimed
  public CompletionStage<Duration> returnCompletionStage(Duration delay) {
    return execute(delay);
  }

  @AsyncSimplyTimed(name = "relative_name")
  public CompletionStage<Duration> returnCompletionStageWithRelativeName(Duration delay) {
    return execute(delay);
  }

  @AsyncSimplyTimed(name = "absolute_name", absolute = true)
  public CompletionStage<Duration> returnCompletionStageWithAbsoluteName(Duration delay) {
    return execute(delay);
  }

  @AsyncSimplyTimed(name = "with_exception", absolute = true)
  public CompletionStage<Duration> returnCompletionStageWithException(Duration delay) {
    throw new RuntimeException("Failed fast");
  }

  @AsyncSimplyTimed(name = "with_failure", absolute = true)
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
