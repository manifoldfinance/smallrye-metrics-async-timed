#!/usr/bin/env bash

MAVEN_RELEASE_VERSION=$1

./mvnw versions:set $MAVEN_CLI_OPTS -DnewVersion=$MAVEN_RELEASE_VERSION
git ls-files . | grep 'pom\.xml$' | xargs git add
git commit -m "[release] set version to $MAVEN_RELEASE_VERSION"
git tag "v$MAVEN_RELEASE_VERSION" -m "[release] v$MAVEN_RELEASE_VERSION"
./mvnw versions:commit $MAVEN_CLI_OPTS
./mvnw versions:set $MAVEN_CLI_OPTS -DnextSnapshot=true
git ls-files . | grep 'pom\.xml$' | xargs git add
git commit -m "[release] prepared next version"
