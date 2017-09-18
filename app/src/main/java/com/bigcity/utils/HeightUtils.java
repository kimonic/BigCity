package com.bigcity.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * * ================================================
 * name:            HeightUtils
 * guide:
 * author：          kimonik
 * version：          1.0
 * date：            2017/7/24
 * description：  listview和gridview固定高度设置辅助类
 * history：
 *
 *
 * -------------setListviewHeight(ListView listView)-----为listview设置固定高度
 * -------------setGridViewHeight(GridView gridView, int gridviewColumns)---为gridview设置固定高度
 * ===================================================
 */

public class HeightUtils {
    public static void setListviewHeight(ListView listView) {
        ListAdapter adapter = listView.getAdapter();
        if (adapter != null) {
            int totalHeight = 0;
            for (int i = 0; i < adapter.getCount(); i++) {
                View listitem = adapter.getView(i, null, listView);
                listitem.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listitem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }

    /**
     * 为gridview设置固定的高度
     */
    public static void setGridViewHeight(GridView gridView, int gridviewColumns) {
        ListAdapter adapter = gridView.getAdapter();
        if (adapter != null) {
            int totalHeight = 0;
            int count;
            if (adapter.getCount() % gridviewColumns == 0) {
                count = adapter.getCount() / gridviewColumns;
            } else {
                count = adapter.getCount() / gridviewColumns + 1;
            }
            for (int i = 0; i < count; i++) {
                View listitem = adapter.getView(i, null, gridView);
                listitem.measure(0, 0);
                totalHeight += listitem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            params.height = totalHeight + gridView.getVerticalSpacing() * (count + 1);
            gridView.setLayoutParams(params);

        }

    }
}
