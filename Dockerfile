FROM docker.elastic.co/logstash/logstash:7.5.2
COPY logstash-output-java_output_example-1.0.0.gem /tmp/
USER root
RUN logstash-plugin install --no-verify --local /tmp/logstash-output-java_output_example-1.0.0.gem


