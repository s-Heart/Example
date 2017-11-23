package com.company.task;

/**
 * Created by shishaojie on 2017/11/23 0023.
 */
public class TResult {
    private int count;
    private float responTime;
    private String domain;

    public TResult(String domain, int count, float responTime) {
        this.domain = domain;
        this.count = count;
        this.responTime = responTime;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getResponTime() {
        return responTime;
    }

    public void setResponTime(float responTime) {
        this.responTime = responTime;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return this.domain + "\t\t\t\t" + this.count + "\t\t\t\t" + this.responTime + "\t\t\t\t" + getAvaTime();
    }

    public float getAvaTime() {
        return this.responTime / this.count;
    }
}
