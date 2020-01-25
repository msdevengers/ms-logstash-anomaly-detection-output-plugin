package com.microsoft.pojo;

import org.apache.logging.log4j.util.Strings;

public class Point implements Comparable<Point> {
    private String timestamp;

    private long value;

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public long getValue() {
        return this.value;
    }

    @Override
    public int compareTo(Point o) {
        if (o == null || Strings.isEmpty(o.getTimestamp()))
            return 1;
        else if (Strings.isEmpty(this.getTimestamp()))
        return -1;

        return this.getTimestamp().compareTo(o.timestamp);
    }
}
