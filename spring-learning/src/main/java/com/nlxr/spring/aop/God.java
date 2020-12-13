package com.nlxr.spring.aop;

public class God {
    private String cluo;
    private String meixi;
    private String wowo;

    public String getCluo() {
        return cluo;
    }

    public void setCluo(String cluo) {
        this.cluo = cluo;
    }

    public String getMeixi() {
        return meixi;
    }

    public void setMeixi(String meixi) {
        this.meixi = meixi;
    }

    public String getWowo() {
        return wowo;
    }

    public void setWowo(String wowo) {
        this.wowo = wowo;
    }

    @Override
    public String toString() {
        return "God{" +
                "cluo='" + cluo + '\'' +
                ", meixi='" + meixi + '\'' +
                ", wowo='" + wowo + '\'' +
                '}';
    }

    public God(String cluo, String meixi, String wowo) {
        this.cluo = cluo;
        this.meixi = meixi;
        this.wowo = wowo;
    }
}
