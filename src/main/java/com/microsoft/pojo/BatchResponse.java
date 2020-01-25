package com.microsoft.pojo;

import java.util.List;

public class BatchResponse {
    private List<Double> expectedValues;

    private List<Boolean> isAnomaly;

    private List<Boolean> isNegativeAnomaly;

    private List<Boolean> isPositiveAnomaly;

    private List<Double> lowerMargins;

    private int period;

    private List<Double> upperMargins;

    public void setExpectedValues(List<Double> expectedValues){
        this.expectedValues = expectedValues;
    }
    public List<Double> getExpectedValues(){
        return this.expectedValues;
    }
    public void setIsAnomaly(List<Boolean> isAnomaly){
        this.isAnomaly = isAnomaly;
    }
    public List<Boolean> getIsAnomaly(){
        return this.isAnomaly;
    }
    public void setIsNegativeAnomaly(List<Boolean> isNegativeAnomaly){
        this.isNegativeAnomaly = isNegativeAnomaly;
    }
    public List<Boolean> getIsNegativeAnomaly(){
        return this.isNegativeAnomaly;
    }
    public void setIsPositiveAnomaly(List<Boolean> isPositiveAnomaly){
        this.isPositiveAnomaly = isPositiveAnomaly;
    }
    public List<Boolean> getIsPositiveAnomaly(){
        return this.isPositiveAnomaly;
    }
    public void setLowerMargins(List<Double> lowerMargins){
        this.lowerMargins = lowerMargins;
    }
    public List<Double> getLowerMargins(){
        return this.lowerMargins;
    }
    public void setPeriod(int period){
        this.period = period;
    }
    public int getPeriod(){
        return this.period;
    }
    public void setUpperMargins(List<Double> upperMargins){
        this.upperMargins = upperMargins;
    }
    public List<Double> getUpperMargins(){
        return this.upperMargins;
    }
}
