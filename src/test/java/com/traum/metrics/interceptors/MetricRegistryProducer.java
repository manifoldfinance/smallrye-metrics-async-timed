package com.traum.metrics.interceptors;

import io.smallrye.metrics.MetricsRegistryImpl;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.mockito.Mockito;

@ApplicationScoped
public class MetricRegistryProducer {

  private final MetricRegistry metricRegistry = Mockito.spy(new MetricsRegistryImpl());

  @Produces
  MetricRegistry metricRegistry() {
    return metricRegistry;
  }
}
