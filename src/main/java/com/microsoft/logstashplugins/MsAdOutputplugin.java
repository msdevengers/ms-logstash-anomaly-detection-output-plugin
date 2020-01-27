package com.microsoft.logstashplugins;

import co.elastic.logstash.api.Configuration;
import co.elastic.logstash.api.Context;
import co.elastic.logstash.api.Event;
import co.elastic.logstash.api.LogstashPlugin;
import co.elastic.logstash.api.Output;
import co.elastic.logstash.api.PluginConfigSpec;
import com.google.gson.Gson;
import com.microsoft.dao.AnomalyDetectorDao;
import com.microsoft.pojo.BatchRequest;
import com.microsoft.pojo.BatchResponse;
import com.microsoft.pojo.Point;


import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.CountDownLatch;

// class name must match plugin name
@LogstashPlugin(name = "ms_ad_output_plugin")
public class MsAdOutputplugin implements Output {

    public static final PluginConfigSpec<String> API_KEY = PluginConfigSpec.stringSetting("apikey", "");
    public static final PluginConfigSpec<String> TIMESTAMP_COLUMN = PluginConfigSpec.stringSetting("ts_column", "ts");
    public static final PluginConfigSpec<String> TIMESERIES_GRANULARITY = PluginConfigSpec.stringSetting("ts_granularity", "");
    public static final PluginConfigSpec<String> TIMESERIES_VALUE_COLUMN = PluginConfigSpec.stringSetting("ts_value_column", "TotalOperationDuration");
    public static final PluginConfigSpec<String> AD_SERVER = PluginConfigSpec.stringSetting("ad_server", "http://localhost:5000");

    private final String id;
    private final String granularity;
    private final String api_key;
    private final String ts_value_column;
    private final String timestamp_column;
    private final String ad_server;

    private PrintStream printer;
    private final CountDownLatch done = new CountDownLatch(1);
    private volatile boolean stopped = false;

    private  BatchRequest batchRequest;

    // all plugins must provide a constructor that accepts id, Configuration, and Context
    public MsAdOutputplugin(final String id, final Configuration configuration, final Context context) {
        this(id, configuration, context, System.out);
    }

    public MsAdOutputplugin(final String id, final Configuration config, final Context context, OutputStream targetStream) {
        // constructors should validate configuration options
        this.id = id;


        this.api_key = config.get(API_KEY);
        this.timestamp_column = config.get(TIMESTAMP_COLUMN);
        this.granularity = config.get(TIMESERIES_GRANULARITY);
        this.ts_value_column = config.get(TIMESERIES_VALUE_COLUMN);
        this.ad_server = config.get(AD_SERVER);
        
        this.batchRequest = new BatchRequest(new ArrayList<>(),this.granularity);

        printer = new PrintStream(targetStream);
        printer.printf( "%s %s %s ",this.api_key,this.timestamp_column,this.granularity);

    }

    @Override
    public void output(final Collection<Event> events) {
        Iterator<Event> z = events.iterator();
        ArrayList<Point> pointCollection = new ArrayList<>();

        while (z.hasNext() && !stopped) {
            Event event = z.next();
            printer.println(event.toString());
            Map<String,Object> map = event.getData();

            long duration = Long.parseLong(map.get(this.ts_value_column).toString());
            String timestamp = (map.get("ts").toString());


            Point p = new Point();
            p.setTimestamp(timestamp);
            p.setValue(duration);
            pointCollection.add(p);

        }
        if(pointCollection.size() < 1)
            return;
        batchRequest.getSeries().addAll(pointCollection);


    }

    @Override
    public void stop() {
        AnomalyDetectorDao dao = new AnomalyDetectorDao(this.api_key,this.ad_server);
        Collections.sort((ArrayList<Point>)batchRequest.getSeries());
        ((ArrayList<Point>) batchRequest.getSeries()).remove(batchRequest.getSeries().size() / 2);
        printer.println(new Gson().toJson(batchRequest));

        BatchResponse response = dao.find(batchRequest);
        printer.print(new Gson().toJson(response));
        stopped = true;
        done.countDown();
    }

    @Override
    public void awaitStop() throws InterruptedException {
        done.await();
    }

    @Override
    public Collection<PluginConfigSpec<?>> configSchema() {
        // should return a list of all configuration options for this plugin
        Collection<PluginConfigSpec<?>> configSpecs = new ArrayList<>();

        ((ArrayList<PluginConfigSpec<?>>) configSpecs).add(API_KEY);
        ((ArrayList<PluginConfigSpec<?>>) configSpecs).add(TIMESTAMP_COLUMN);
        ((ArrayList<PluginConfigSpec<?>>) configSpecs).add(TIMESERIES_VALUE_COLUMN);
        ((ArrayList<PluginConfigSpec<?>>) configSpecs).add(TIMESERIES_GRANULARITY);
        ((ArrayList<PluginConfigSpec<?>>) configSpecs).add(AD_SERVER);

        return configSpecs;
    }

    @Override
    public String getId() {
        return id;
    }
}
