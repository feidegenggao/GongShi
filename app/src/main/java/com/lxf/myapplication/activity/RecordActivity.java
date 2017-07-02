package com.lxf.myapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lxf.myapplication.R;
import com.lxf.myapplication.adapter.RecordAdapter;
import com.lxf.myapplication.bean.Record;
import com.lxf.myapplication.bean.RecordDao;
import com.lxf.myapplication.bean.RecordState;
import com.lxf.myapplication.bean.Worker;
import com.lxf.myapplication.bean.WorkerDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecordActivity extends BaseActivity {
    private WorkerDao workerDao;
    private RecordDao recordDao;
    private Long gongDiID = new Long(1);
    private String TODAY;
    private List<Record> todayRecordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        TODAY = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());
        ButterKnife.bind(this);
        initDB();
    }

    QueryBuilder<Record> recordQueryBuilder;

    private void initDB() {
        workerDao = getDaoSession().getWorkerDao();
        recordDao = getDaoSession().getRecordDao();
        recordQueryBuilder = recordDao.queryBuilder().
                where(RecordDao.Properties.RecordDate.eq(TODAY));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTodayRecord();
        updateView();
    }

    /**
     * 根据工人记录，初始化当天的工时记录全部为{@link com.lxf.myapplication.bean.RecordState#NO}
     */
    private void initTodayRecord() {
        todayRecordList = recordQueryBuilder.list();
        List<Worker> list = workerDao.queryBuilder().list();
        boolean ifInitRecordForThisWorker;
        for (Worker worker : list) {
            ifInitRecordForThisWorker = false;
            for (Record record : todayRecordList) {
                if (record.getWorkerID().equals(worker.getId())) {
                    ifInitRecordForThisWorker = true;
                }
            }

            if (!ifInitRecordForThisWorker) {
                Record record = new Record();
                record.setGongDiID(gongDiID);
                record.setLastOptDate(new Date());
                record.setRecordDate(TODAY);
                record.setState(RecordState.NO);
                record.setWorkerID(worker.getId());

                recordDao.insert(record);
            }
        }
    }

    private void updateView() {
        recordRV.setLayoutManager(new LinearLayoutManager(this));
        todayRecordList = recordQueryBuilder.list();
        recordRV.setAdapter(new RecordAdapter(this, todayRecordList, recordDao, workerDao));
    }

    @BindView(R.id.recordRV)
    RecyclerView recordRV;

    @OnClick(R.id.addWorker)
    public void addWorker() {
        Worker worker = new Worker();
        worker.setName("ZhangSan");
        workerDao.insert(worker);
    }
}
