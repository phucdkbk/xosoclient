/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alandk.xosomienbac.thongke;

/**
 *
 * @author phucdk
 */
public class CountLoGan implements Comparable<CountLoGan> {

    private String result;
    private int count;
    private boolean existed;

    public CountLoGan(String result, int count, boolean existed) {
        this.count = count;
        this.existed = existed;
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isExisted() {
        return existed;
    }

    public void setExisted(boolean existed) {
        this.existed = existed;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public int compareTo(CountLoGan o) {
        if (o.count > this.count) {
            return 1;
        } else {
            return -1;
        }
    }
}
