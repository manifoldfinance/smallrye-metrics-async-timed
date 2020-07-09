# SmallRye Metrics Extension: AsyncTimed

This library provides annotation-based timing for methods returning promised values, namely
`java.util.concurrent.CompletionStage` and `io.smallrye.mutiny.Uni`.

## Usage

Annotate any bean method returning one of the aforementioned types with `com.traum.microprofile.metrics.annotation.AsyncTimed`.

## Release

```shell
mvn versions:set $MAVEN_CLI_OPTS -DnewVersion=$MAVEN_RELEASE_VERSION
git ls-files . | grep 'pom\.xml$' | xargs git add
git commit -m "[release] set version to $MAVEN_RELEASE_VERSION"
git tag "v$MAVEN_RELEASE_VERSION" -m "[release] v$MAVEN_RELEASE_VERSION"
mvn deploy ${MAVEN_CLI_OPTS} -Pjacoco -Dmymavenrepo.read.url=${MYMAVENREPO_READ_URL} -Dmymavenrepo.write.url=${MYMAVENREPO_WRTITE_URL}
mvn versions:commit $MAVEN_CLI_OPTS
mvn versions:set $MAVEN_CLI_OPTS -DnextSnapshot=true
git ls-files . | grep 'pom\.xml$' | xargs git add
git commit -m "[release] prepared next version"
```

