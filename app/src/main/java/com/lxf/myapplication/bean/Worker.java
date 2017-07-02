package com.lxf.myapplication.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lxf on 2017/7/1.
 *
 * 工人的bean
 */

@Entity
public class Worker {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String name;
    @Generated(hash = 1959981079)
    public Worker(Long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }
    @Generated(hash = 107771754)
    public Worker() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID:" + getId() + "\tName:" + getName();
    }
}
