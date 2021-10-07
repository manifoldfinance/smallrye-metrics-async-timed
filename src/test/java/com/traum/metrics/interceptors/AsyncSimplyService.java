package com.traum.metrics.interceptors;

import com.traum.microprofile.metrics.annotation.AsyncSimplyTimed;
import com.traum.microprofile.metrics.annotation.AsyncTimed;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AsyncSimplyService extends AsyncService {

  @Override
  @AsyncSimplyTimed
  public CompletionStage<Duration> returnCompletionStage(Duration delay) {
    return super.returnCompletionStage(delay);
  }

  @Override
  @AsyncSimplyTimed(name = "relative_name")
  public CompletionStage<Duration> returnCompletionStageWithRelativeName(Duration delay) {
    return super.returnCompletionStageWithRelativeName(delay);
  }

  @Override
  @AsyncSimplyTimed(name = "absolute_name", absolute = true)
  public CompletionStage<Duration> returnCompletionStageWithAbsoluteName(Duration delay) {
    return super.returnCompletionStageWithAbsoluteName(delay);
  }

  @Override
  @AsyncSimplyTimed(name = "with_exception", absolute = true)
  public CompletionStage<Duration> returnCompletionStageWithException(Duration delay) {
    return super.returnCompletionStageWithException(delay);
  }

  @Override
  @AsyncSimplyTimed(name = "with_failure", absolute = true)
  public CompletionStage<Duration> returnCompletionStageWithFailure(Duration delay) {
    return super.returnCompletionStageWithFailure(delay);
  }
}
