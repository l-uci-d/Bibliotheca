
FROM debian:buster-slim AS build

ARG MAVEN_VERSION=3.9.8
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

# Install libraries used for builds
RUN apt-get update \
    && apt-get upgrade -y \
    && mkdir -p /usr/share/man/man1 \
    && apt-get install -y --no-install-recommends \
        software-properties-common \
        wget \
        gnupg \
        curl \
        ca-certificates \
        bzip2 \
        zip \
        unzip \
        git \
    && rm -rf /var/cache/apt/archives/* \
    && rm -rf /var/lib/apt/lists/*

# Install Maven
RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
#  && echo "${SHA}  /tmp/apache-maven.tar.gz" | sha512sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && rm -f /usr/bin/mvn \
  && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

# Add JavaFX
RUN curl -fsSL -o /tmp/openjfx-21.0.4_linux-x64_bin-sdk.zip https://download2.gluonhq.com/openjfx/21.0.4/openjfx-21.0.4_linux-x64_bin-sdk.zip \
  && cd /tmp/ \
  && unzip /tmp/openjfx-21.0.4_linux-x64_bin-sdk.zip

FROM maven:3.8.4-openjdk-17-slim

RUN apt-get update && \
    apt-get install -y curl \
    wget \
    openjdk-17-jdk


ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64
ENV DISPLAY=:0

WORKDIR /app

COPY . .

RUN apt-get update && apt-get install libgtk-3-0 libglu1-mesa xvfb -y && apt-get update


ENV DISPLAY=:99

ADD run.sh /run.sh

RUN chmod a+x /run.sh

CMD /run.sh