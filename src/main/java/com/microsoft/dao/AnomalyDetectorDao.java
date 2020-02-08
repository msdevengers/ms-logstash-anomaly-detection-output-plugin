
package com.microsoft.dao;

import com.google.gson.Gson;
import com.microsoft.pojo.BatchRequest;
import com.microsoft.pojo.BatchResponse;
import java.io.PrintStream;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AnomalyDetectorDao {

    private String subscriptionKey;
    private String adServer;
    private PrintStream printer;

    public AnomalyDetectorDao(String subscriptionKey, String adServer, PrintStream printer) {
        this.subscriptionKey = subscriptionKey;
        this.adServer = adServer;
        this.printer = printer;
    }

    public BatchResponse find(BatchRequest batchRequest) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(this.adServer);
        WebTarget anomalyWebTarget = webTarget.path("/anomalydetector/v1.0/timeseries/entire/detect");
        Invocation.Builder invocationBuilder = anomalyWebTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Ocp-Apim-Subscription-Key", this.subscriptionKey);
        Response response = invocationBuilder.post(Entity.entity(batchRequest, MediaType.APPLICATION_JSON_TYPE));

        String rawResponse = response.readEntity(String.class);
        if (response.getStatus() == 200) {
            return new Gson().fromJson(rawResponse, BatchResponse.class);
        }
        printer.print(rawResponse);
        throw new ProcessingException(rawResponse);
    }

}