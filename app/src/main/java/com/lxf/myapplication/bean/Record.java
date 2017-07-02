package com.lxf.myapplication.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lxf on 2017/7/1.
 * <p>
 * 工时记录的
 */
@Entity
public class Record {
    @Id
    private Long id;
    private Long gongDiID;
    private Long workerID;
    @Convert(converter = RecordStateConverter.class, columnType = String.class)
    private RecordState state;
    private Date lastOptDate;
    private String recordDate;//yyyy-mm--dd
    @Generated(hash = 314253733)
    public Record(Long id, Long gongDiID, Long workerID, RecordState state,
            Date lastOptDate, String recordDate) {
        this.id = id;
        this.gongDiID = gongDiID;
        this.workerID = workerID;
        this.state = state;
        this.lastOptDate = lastOptDate;
        this.recordDate = recordDate;
    }
    @Generated(hash = 477726293)
    public Record() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getGongDiID() {
        return this.gongDiID;
    }
    public void setGongDiID(Long gongDiID) {
        this.gongDiID = gongDiID;
    }
    public Long getWorkerID() {
        return this.workerID;
    }
    public void setWorkerID(Long workerID) {
        this.workerID = workerID;
    }
    public RecordState getState() {
        return this.state;
    }
    public void setState(RecordState state) {
        this.state = state;
    }
    public Date getLastOptDate() {
        return this.lastOptDate;
    }
    public void setLastOptDate(Date lastOptDate) {
        this.lastOptDate = lastOptDate;
    }
    public String getRecordDate() {
        return this.recordDate;
    }
    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
    
}
