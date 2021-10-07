package com.traum.microprofile.metrics.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;
import org.eclipse.microprofile.metrics.MetricUnits;

/**
 * Creates a simple timer for a method returning a {@link java.util.concurrent.CompletionStage} or
 * {@link io.smallrye.mutiny.Uni}.
 */
@Inherited
@Documented
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({
  ElementType.TYPE,
  ElementType.CONSTRUCTOR,
  ElementType.METHOD,
  ElementType.ANNOTATION_TYPE
})
public @interface AsyncSimplyTimed {

  /** @return The name of the simple timer. */
  @Nonbinding
  String name() default "";

  /**
   * @return The tags of the simple timer. Each {@code String} tag must be in the form of
   *     'key=value'. If the input is empty or does not contain a '=' sign, the entry is ignored.
   * @see org.eclipse.microprofile.metrics.Metadata
   */
  @Nonbinding
  String[] tags() default {};

  /**
   * @return If {@code true}, use the given name as an absolute name. If {@code false} (default),
   *     use the given name relative to the annotated class. When annotating a class, this must be
   *     {@code false}.
   */
  @Nonbinding
  boolean absolute() default false;

  /**
   * @return The display name of the simple timer.
   * @see org.eclipse.microprofile.metrics.Metadata
   */
  @Nonbinding
  String displayName() default "";

  /**
   * @return The description of the simple timer
   * @see org.eclipse.microprofile.metrics.Metadata
   */
  @Nonbinding
  String description() default "";

  /**
   * @return The unit of the simple timer. By default, the value is {@link MetricUnits#NANOSECONDS}.
   * @see org.eclipse.microprofile.metrics.Metadata
   * @see org.eclipse.microprofile.metrics.MetricUnits
   */
  @Nonbinding
  String unit() default MetricUnits.NANOSECONDS;
}
