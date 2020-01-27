# Anomaly Detector Cognitive Service Logstash Output Java Plugin

[![Travis Build Status](https://travis-ci.org/logstash-plugins/logstash-output-java_output_example.svg)](https://travis-ci.org/logstash-plugins/logstash-output-java_output_example)

This is a Java plugin for [Logstash](https://github.com/elastic/logstash).

It is fully free and fully open source. The license is Apache 2.0, meaning you are free to use it however you want.

The documentation for Logstash Java plugins is available [here](https://www.elastic.co/guide/en/logstash/6.7/contributing-java-plugin.html).

#### This plugin is just optimized for Elasticseearch input plugin. It waits all input data for complete and creates a single request to AD Cognitive Service

name of the file should be logstash.config
```ruby
input {
      # Read all documents from Elasticsearch matching the given query
      elasticsearch {
        hosts => "localhost:9200"
        index =>  "mydata-2018.09.*"
        query => '{"sort" : [{ "ts" : {"order" : "asc"}}]}'
        size => 500
      }
    }
#output {
#     stdout {}
#   }

output {
  ms_ad_output_plugin {
       apikey => "xxxxx-xxxx-xxx"
       ts_granularity => "daily"
       ts_column => "ts" #will be implemented
       ms-ad-plugin => "TotalOperationDuration" #will be implemented
  }
}
```

```bash
./gradlew clear
./gradlew gem
docker build -t <repo-name>/ms-ad-logstash-output-plugin:0.0.1 .
docker push <repo-name>/ms-ad-logstash-output-plugin:0.0.1
docker run -d \
     -v <config-file-directory-path>:/opt/logstash/pipeline \
    --name ms-ad-plugin <repo-name>/ms-ad-logstash-output-plugin:0.0.1  
docker logs ms-ad-plugin

```

[![Building and Executing Ms Logstash AD Output Plugin](https://img.youtube.com/vi/tfNje8Q5IkM/0.jpg)](https://www.youtube.com/watch?v=tfNje8Q5IkM)