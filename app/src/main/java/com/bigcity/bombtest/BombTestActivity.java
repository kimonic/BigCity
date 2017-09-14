package com.bigcity.bombtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigcity.R;
import com.bigcity.base.BaseActivity;
import com.bigcity.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * * ===============================================================
 * name:             BombTestActivity
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：             2017/9/14
 * description：
 * history：
 * *==================================================================
 */

public class BombTestActivity extends BaseActivity {
    @BindView(R.id.bt_act_bombtest)
    Button btActBombtest;
    @BindView(R.id.bt_act_bombtest2)
    Button btActBombtest2;
    @BindView(R.id.bt_act_bombtest3)
    Button btActBombtest3;
    @BindView(R.id.bt_act_bombtest4)
    Button btActBombtest4;


    @BindView(R.id.tv_act_bombtest)
    TextView tvContent;

    @Override
    public int getLayoutResId() {
        return R.layout.act_bombtest;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_act_bombtest://上传数据
                Person p2 = new Person();
                p2.setName("lucky");
                p2.setAddress("北京海淀");
                p2.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId, BmobException e) {
                        if (e == null) {
                            ToastUtils.showToast(BombTestActivity.this, "添加数据成功，返回objectId为：" + objectId);

                        } else {
                            ToastUtils.showToast(BombTestActivity.this, "创建数据失败：" + e.getMessage());
                        }
                    }
                });
                break;
            case R.id.bt_act_bombtest2://查询数据

//                //查找Person表里面id为6b6c11c537的数据
//                BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
//                bmobQuery.getObject("4baaee125b", new QueryListener<Person>() {
//                    @Override
//                    public void done(Person object, BmobException e) {
//                        if (e == null) {
//                            ToastUtils.showToast(BombTestActivity.this, "查询成功");
//                            tvContent.setText(object.getAddress() + "\n" + object.getName());
//                        } else {
//                            ToastUtils.showToast(BombTestActivity.this, "查询失败：" + e.getMessage());
//                        }
//                    }
//                });


                BmobQuery<Person> query = new BmobQuery<Person>();
                //查询playerName叫“比目”的数据
                query.addWhereEqualTo("name", "lucky");
                //返回50条数据，如果不加上这条语句，默认返回10条数据
                query.setLimit(50);
                //执行查询方法
                query.findObjects(new FindListener<Person>() {
                    @Override
                    public void done(List<Person> object, BmobException e) {
                        if (e == null) {
                            ToastUtils.showToast(BombTestActivity.this, "查询成功：共" + object.size() + "条数据。");
                            StringBuilder builder = new StringBuilder();
                            for (Person Person : object) {
                                builder.append(Person.getName())
                                        .append("------")
                                        .append(Person.getObjectId())
                                        .append("------")
                                        .append(Person.getCreatedAt())
                                        .append("\n");
                            }
                            tvContent.setText(builder.toString());
                        } else {
                            ToastUtils.showToast(BombTestActivity.this, "失败：" + e.getMessage() + "," + e.getErrorCode());
                        }
                    }
                });
                break;
            case R.id.bt_act_bombtest3://修改数据
                //更新Person表里面id为6b6c11c537的数据，address内容更新为“北京朝阳”
                final Person p4 = new Person();
                p4.setAddress("北京朝阳");
                p4.update("4baaee125b", new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtils.showToast(BombTestActivity.this, "更新成功:" + p4.getUpdatedAt());
                        } else {
                            ToastUtils.showToast(BombTestActivity.this, "更新失败：" + e.getMessage());
                        }
                    }

                });
                break;
            case R.id.bt_act_bombtest4://删除数据
                final Person p3 = new Person();
                p3.setObjectId("4baaee125b");
                p3.delete(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            ToastUtils.showToast(BombTestActivity.this, "删除成功:" + p3.getUpdatedAt());
                        } else {
                            ToastUtils.showToast(BombTestActivity.this, "删除失败：" + e.getMessage());
                        }
                    }

                });
                break;
//                     case R.id.:break;
//                     case R.id.:break;
        }
    }

    @Override
    public void initDataFromIntent() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {
        btActBombtest.setOnClickListener(this);
        btActBombtest2.setOnClickListener(this);
        btActBombtest3.setOnClickListener(this);
        btActBombtest4.setOnClickListener(this);

    }

    @Override
    public void initDataFromInternet() {

    }

    @Override
    public void LoadInternetDataToUi() {

    }


    private class Person extends BmobObject {
        private String name;
        private String address;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
