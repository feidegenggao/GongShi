package com.lxf.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxf.myapplication.R;
import com.lxf.myapplication.bean.Record;
import com.lxf.myapplication.bean.RecordDao;
import com.lxf.myapplication.bean.RecordState;
import com.orhanobut.logger.Logger;

import org.w3c.dom.Text;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lxf on 2017/7/1.
 */

class RecordViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recordRoot)
    RelativeLayout recordRoot;
    @BindView(R.id.workerName)
    TextView workerName;
    @BindView(R.id.lastOptTime)
    TextView lastOptTime;
    @BindView(R.id.recordBt)
    ImageButton recordBt;


    private Record record;
    private RecordAdapter recordAdapter;

    RecordViewHolder(View itemView, final RecordDao recordDao) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        recordBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RecordState.NO.equals(record.getState())) {
                    record.setState(RecordState.YES);
                } else {
                    record.setState(RecordState.NO);
                }
                record.setLastOptDate(new Date());
                recordDao.update(record);
                recordAdapter.notifyItemChanged(getAdapterPosition());
            }
        });
    }

    void setRecord(Record record) {
        this.record = record;
    }

    void setRecordAdapter(RecordAdapter recordAdapter) {
        this.recordAdapter = recordAdapter;
    }
}