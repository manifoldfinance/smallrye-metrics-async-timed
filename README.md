# SmallRye Metrics Extension: AsyncSimplyTimed

This library provides annotation-based timing for methods returning promised values, namely
`java.util.concurrent.CompletionStage` and `io.smallrye.mutiny.Uni`.

## Installation

Please add our bintray repository:

```xml
<repositories>
    <repository>
        <id>bintray</id>
        <name>JFrog Bintray Apache Maven Packages</name>
        <url>https://dl.bintray.com/traum-ferienwohnungen/maven</url>
    </repository>
</repositories>
```

This library assumes [`io.smallrye:smallrye-metrics`](https://smallrye.io/docs/smallrye-metrics/2.4.0/index.html) is available at runtime.
Therefore, the minimum set of dependencies looks as follows:

```xml
<dependencies>
    <!-- When from quarkus use io.quarkus:quarkus-smallrye-metrics instead -->
    <dependency>
        <groupId>io.smallrye</groupId>
        <artifactId>smallrye-metrics</artifactId>
        <version>2.4.x</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>com.traum</groupId>
        <artifactId>smallrye-metrics-async-timed</artifactId>
        <version>RELEASE</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Usage

Annotate any bean method returning one of the aforementioned types with `com.traum.microprofile.metrics.annotation.AsyncSimplyTimed`,
instead of MicroProfile's [`SimplyTimed`](https://download.eclipse.org/microprofile/microprofile-metrics-2.3/microprofile-metrics-spec-2.3.html#_simplytimed).
