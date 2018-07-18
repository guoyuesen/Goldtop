package com.goldtop.gys.crdeit.goldtop.interfaces;

/**
 * Created by 郭月森 on 2018/6/25.
 */

public interface GrapNotif {
    /**
     * 开始
     * @param p 第一个点
     */
    public void Start(int p);

    /**
     * 移动时经过有效点
     * @param p 经过的点
     * @param pass 已经输入的密码
     */
    public void Move(int p, String pass);

    /**
     * 滑动结束
     * @param pass 滑动密码
     */
    public void Stop(String pass);
}
