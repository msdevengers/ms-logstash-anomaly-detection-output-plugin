
package com.microsoft.dao;

import com.google.gson.Gson;
import com.microsoft.pojo.BatchRequest;
import com.microsoft.pojo.BatchResponse;
import com.microsoft.pojo.StreamRequest;
import com.microsoft.pojo.StreamResponse;

import java.io.PrintStream;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AnomalyDetectorDao {

    private static final String BATCH_CONTEXT = "/anomalydetector/v1.0/timeseries/entire/detect";
    private static final String STREAM_CONTEXT = "/anomalydetector/v1.0/timeseries/last/detect";

    private String subscriptionKey;
    private String adServer;
    private PrintStream printer;

    public AnomalyDetectorDao(String subscriptionKey, String adServer, PrintStream printer) {
        this.subscriptionKey = subscriptionKey;
        this.adServer = adServer;
        this.printer = printer;
    }

    public BatchResponse find(BatchRequest request) {
        Client client = ClientBuilder.newClient();
        try {
            Invocation.Builder invocationBuilder = getInvocationBuilder(client,BATCH_CONTEXT);
            Response response = invocationBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

            String rawResponse = response.readEntity(String.class);
            if (response.getStatus() == 200) {
                return new Gson().fromJson(rawResponse, BatchResponse.class);
            }
            printer.print(rawResponse);

            throw new ProcessingException(rawResponse);
        } finally {
            client.close();
        }
    }

    public StreamResponse find(StreamRequest request) {
        Client client = ClientBuilder.newClient();
        try {
            Invocation.Builder invocationBuilder = getInvocationBuilder(client,STREAM_CONTEXT);
            Response response = invocationBuilder.post(Entity.entity(request, MediaType.APPLICATION_JSON_TYPE));

            String rawResponse = response.readEntity(String.class);
            if (response.getStatus() == 200) {
                return new Gson().fromJson(rawResponse, StreamResponse.class);
            }
            printer.print(rawResponse);
            throw new ProcessingException(rawResponse);
        } finally {
            client.close();
        }
    }

    private Invocation.Builder getInvocationBuilder(Client client,String context) {
        WebTarget webTarget = client.target(this.adServer);
        WebTarget anomalyWebTarget = webTarget.path(context);
        Invocation.Builder invocationBuilder = anomalyWebTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Ocp-Apim-Subscription-Key", this.subscriptionKey);
        return invocationBuilder;

    }

}