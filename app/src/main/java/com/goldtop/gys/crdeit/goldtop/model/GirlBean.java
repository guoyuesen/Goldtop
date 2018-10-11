package com.goldtop.gys.crdeit.goldtop.model;

/**
 * Created by 郭月森 on 2018/9/26.
 */

public class GirlBean {
    private String url;
    private float scale;

    public GirlBean(String url, float scale) {
        this.url = url;
        this.scale = scale;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
