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
public class CountItNhieu implements Comparable<CountItNhieu> {

    private String result;
    private int count;
    private int type;

    public CountItNhieu(String result, int count, int type) {
        this.result = result;
        this.count = count;
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int compareTo(CountItNhieu o) {
        int compareResult = -1;
        if (o != null) {
            if (o.count > this.count) {
                compareResult = 1;
            } else if (o.count < this.count) {
                compareResult = -1;
            } else if (o.count == this.count) {
                compareResult = 0;
            }
        }
        return compareResult;
    }
}
