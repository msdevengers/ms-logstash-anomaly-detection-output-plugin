
package org.dao;


import org.pojo.BatchRequest;

import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;



public class AnomalyDetectorDao {

    public String find(BatchRequest batchRequest) {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("https://jsonplaceholder.typicode.com/");
        WebTarget employeeWebTarget = webTarget.path("todos/1");
        Invocation.Builder invocationBuilder = employeeWebTarget.request(MediaType.APPLICATION_JSON);
        String response =  invocationBuilder.get(String.class);
        System.out.println("response = " + response);
        return response;

    }

}