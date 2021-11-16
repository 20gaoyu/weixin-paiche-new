FROM harbor.ctyuncdn.cn/cdn-log/cdnlog-docker-base:v2.0.0
MAINTAINER "CDN"
VOLUME /tmp
USER   root
RUN    mkdir /soft
COPY cdn-log-config-rest*.jar /soft/cdn-log-config-rest/cdn-log-config-rest.jar
COPY start.sh /soft/cdn-log-config-rest/start.sh

ENTRYPOINT ["sh","/soft/cdn-log-config-rest/start.sh"]
