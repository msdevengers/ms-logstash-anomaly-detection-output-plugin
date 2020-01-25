
package com.microsoft.dao;


import com.microsoft.pojo.BatchRequest;
import com.microsoft.pojo.BatchResponse;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class AnomalyDetectorDao {


    private String subscriptionKey;

    public AnomalyDetectorDao(String subscriptionKey){
        this.subscriptionKey = subscriptionKey;
    }

    public BatchResponse find(BatchRequest batchRequest) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:5000");
        WebTarget employeeWebTarget = webTarget.path("/anomalydetector/v1.0/timeseries/entire/detect");
        Invocation.Builder invocationBuilder = employeeWebTarget.request(MediaType.APPLICATION_JSON);
        invocationBuilder.header("Ocp-Apim-Subscription-Key",this.subscriptionKey);
        Response response = invocationBuilder.post(Entity.entity(batchRequest,MediaType.APPLICATION_JSON_TYPE));


        return response.readEntity(BatchResponse.class);

    }

}