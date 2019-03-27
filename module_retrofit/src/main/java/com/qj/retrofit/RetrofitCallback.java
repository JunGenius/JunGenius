package com.qj.retrofit;

/**
 * @author qujun
 * @des
 * @time 2019/3/11 11:31
 * Because had because, so had so, since has become since, why say why。
 **/

public interface RetrofitCallback {

    public void progress(int progress);

    public void complete(String info);

    public void error(String info);
}
