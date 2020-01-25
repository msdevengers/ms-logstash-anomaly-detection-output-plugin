package org.pojo;

import java.util.List;

public class BatchRequest
{
    private String granularity;

    private List<Series> series;

    public void setGranularity(String granularity){
        this.granularity = granularity;
    }
    public String getGranularity(){
        return this.granularity;
    }
    public void setSeries(List<Series> series){
        this.series = series;
    }
    public List<Series> getSeries(){
        return this.series;
    }
}
