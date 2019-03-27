package com.qj.common.datebase.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author qujun
 * @des
 * @time 2019/1/30 14:59
 * Because had because, so had so, since has become since, why say why。
 **/

@Entity
public class Test {

    @Id
    private String ID = "";

    private String NAME = "";

    private String ADDRESS = "";

    public String getADDRESS() {
        return this.ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getNAME() {
        return this.NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    @Generated(hash = 695405647)
    public Test(String ID, String NAME, String ADDRESS) {
        this.ID = ID;
        this.NAME = NAME;
        this.ADDRESS = ADDRESS;
    }

    @Generated(hash = 372557997)
    public Test() {
    }
}
