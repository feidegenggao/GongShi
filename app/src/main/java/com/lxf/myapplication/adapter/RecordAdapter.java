package com.lxf.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lxf.myapplication.R;
import com.lxf.myapplication.bean.Record;
import com.lxf.myapplication.bean.RecordDao;
import com.lxf.myapplication.bean.RecordState;
import com.lxf.myapplication.bean.Worker;
import com.lxf.myapplication.bean.WorkerDao;
import com.orhanobut.logger.Logger;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by lxf on 2017/7/1.
 * <p>
 * 记录页面的Adapter
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordViewHolder> {
    private Context context;
    private List<Record> recordList;
    private final LayoutInflater layoutInflater;
    private final RecordDao recordDao;
    private final WorkerDao workerDao;

    public RecordAdapter(Context context, List<Record> recordList, RecordDao recordDao, WorkerDao workerDao) {
        this.context = context;
        this.recordList = recordList;
        layoutInflater = LayoutInflater.from(context);
        this.recordDao = recordDao;
        this.workerDao = workerDao;
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecordViewHolder(layoutInflater.inflate(
                R.layout.item_record, parent, false), recordDao);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        Record record = recordList.get(position);
        holder.setRecord(record);
        holder.setRecordAdapter(this);
        List<Worker> workerList = workerDao.queryBuilder().where((WorkerDao.Properties.Id.eq(record.getWorkerID()))).list();
        String name = null;
        if (workerList.size() == 1) {
            Worker worker = workerList.get(0);
            name = worker.getName();
        }else{
            Logger.e("出错了，这个地方为什么Worker的数量不是1？");
        }
        holder.workerName.setText("姓名：" + name);
        holder.lastOptTime.setText("最后操作时间:\n" +
                new SimpleDateFormat("yyyy年MM月dd日 HH点mm分ss秒").format(record.getLastOptDate()
                ));
        RecordState recordState = record.getState();
        int color;
        if (RecordState.NO.equals(recordState)) {
            color = context.getResources().getColor(R.color.colorQueXi);
        } else {
            color = context.getResources().getColor(R.color.colorChuXi);
        }
        holder.recordRoot.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return recordList.size();
    }

}
