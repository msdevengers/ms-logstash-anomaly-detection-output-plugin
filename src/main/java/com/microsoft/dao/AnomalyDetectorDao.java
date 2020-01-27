
package com.microsoft.dao;


import com.microsoft.pojo.BatchRequest;
import com.microsoft.pojo.BatchResponse;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class AnomalyDetectorDao {


    private String subscriptionKey;
    private String adServer;

    public AnomalyDetectorDao(String subscriptionKey,String adServer){
        this.subscriptionKey = subscriptionKey;
        this.adServer = adServer;
    }

    public BatchResponse find(BatchRequest batchRequest) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(this.adServer);
        WebTarget employeeWebTarget = webTarget.path("/anomalydetector/v1.0/timeseries/entire/detect");
        Invocation.Builder invocationBuilder = employeeWebTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Ocp-Apim-Subscription-Key",this.subscriptionKey);
        Response response = invocationBuilder.post(Entity.entity(batchRequest,MediaType.APPLICATION_JSON_TYPE));


        return response.readEntity(BatchResponse.class);

    }

}