package com.lxf.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.lxf.myapplication.R;
import com.lxf.myapplication.adapter.RecordAdapter;
import com.lxf.myapplication.bean.Record;
import com.lxf.myapplication.bean.RecordDao;
import com.lxf.myapplication.bean.RecordState;
import com.lxf.myapplication.bean.Worker;
import com.lxf.myapplication.bean.WorkerDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.aigestudio.datepicker.bizs.themes.DPCNTheme;
import cn.aigestudio.datepicker.bizs.themes.DPTManager;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.datePicker)
    DatePicker datePicker;
    @BindView(R.id.workRV)
    RecyclerView workRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        initDatePicker();
        initWorkerRV();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(R.string.home_activity_title);
//        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, RecordActivity.class));
            }
        });
    }

    private void initWorkerRV() {
        TODAY = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(new Date());
        initDB();
        initTodayRecord();
        updateView();
    }

    private void initDatePicker() {
        Calendar instance = Calendar.getInstance();
        datePicker.setDate(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH) + 1);
        datePicker.setMode(DPMode.SINGLE);
        datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Toast.makeText(HomeActivity.this, "DATE:" + date, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private WorkerDao workerDao;
    private RecordDao recordDao;
    private Long gongDiID = new Long(1);
    private String TODAY;
    private List<Record> todayRecordList;
    QueryBuilder<Record> recordQueryBuilder;
    private void initDB() {
        workerDao = getDaoSession().getWorkerDao();
        recordDao = getDaoSession().getRecordDao();
        recordQueryBuilder = recordDao.queryBuilder().
                where(RecordDao.Properties.RecordDate.eq(TODAY));
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
        workRV.setLayoutManager(new LinearLayoutManager(this));
        todayRecordList = recordQueryBuilder.list();
        workRV.setAdapter(new RecordAdapter(this, todayRecordList, recordDao, workerDao));
    }

}
