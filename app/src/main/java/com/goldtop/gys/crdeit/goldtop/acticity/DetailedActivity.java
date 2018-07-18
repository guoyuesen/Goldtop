package com.goldtop.gys.crdeit.goldtop.acticity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.goldtop.gys.crdeit.goldtop.Adapters.DetailedAdapter;
import com.goldtop.gys.crdeit.goldtop.Base.BaseActivity;
import com.goldtop.gys.crdeit.goldtop.R;

import org.json.JSONArray;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郭月森 on 2018/7/17.
 */

public class DetailedActivity extends BaseActivity {
    @Bind(R.id.detailed_list)
    ListView detailedList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);
        hiedBar(this);
        JSONArray array = new JSONArray();
        array.put(0);
        array.put(0);
        array.put(0);
        array.put(0);
        array.put(0);
        array.put(0);
        detailedList.setAdapter(new DetailedAdapter(this,array));
    }
}
