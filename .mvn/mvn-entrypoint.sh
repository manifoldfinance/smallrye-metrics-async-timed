#!/usr/bin/env bash

mkdir -p $MAVEN_CONFIG

repositoryDir="$CI_PROJECT_DIR/.mvn/repository"
readUrl="$MVN_REPO_READ_URL"
writeUrl="$MVN_REPO_WRITE_URL"

sonarHostUrl="$SONAR_HOST_URL"
sonarLogin="$SONAR_LOGIN"

cat > $MAVEN_CONFIG/settings.xml <<EOF
<?xml version="1.0"?>
<settings>
    <localRepository>$repositoryDir</localRepository>
    <profiles>
        <profile>
            <id>mymavenrepo</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <mymavenrepo.read.url>$readUrl</mymavenrepo.read.url>
                <mymavenrepo.write.url>$writeUrl</mymavenrepo.write.url>
            </properties>
        </profile>
        <profile>
            <id>sonar</id>
            <properties>
                <sonar.host.url>$sonarHostUrl</sonar.host.url>
                <sonar.login>$sonarLogin</sonar.login>
            </properties>
        </profile>
    </profiles>
</settings>
EOF

/usr/local/bin/mvn-entrypoint.sh
