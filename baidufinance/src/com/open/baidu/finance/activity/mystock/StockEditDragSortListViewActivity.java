/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午4:59:43
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.activity.mystock;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.mobeta.android.dslv.DragSortListView;
import com.mobeta.android.dslv.DragSortListView.RemoveListener;
import com.open.baidu.finance.R;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-10-13下午4:59:43
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class StockEditDragSortListViewActivity extends ListActivity
{
    private ArrayAdapter<String> adapter;

    private DragSortListView.DropListener onDrop =
        new DragSortListView.DropListener() {
            @Override
            public void drop(int from, int to) {
                if (from != to) {
                    DragSortListView list = getListView();
                    String item = adapter.getItem(from);
                    adapter.remove(item);
                    adapter.insert(item, to);
                    list.moveCheckState(from, to);
                }
            }
        };

    private RemoveListener onRemove =
        new DragSortListView.RemoveListener() {
            @Override
            public void remove(int which) {
                DragSortListView list = getListView();
                String item = adapter.getItem(which);
                adapter.remove(item);
                list.removeCheckState(which);
            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_edit_drag_sort);
        
        String[] array = getResources().getStringArray(R.array.jazz_artist_names);
        ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(array));

        adapter = new ArrayAdapter<String>(this, R.layout.list_item_checkable, R.id.text, arrayList);
        
        setListAdapter(adapter);
        
        DragSortListView list = getListView();
        list.setDropListener(onDrop);
        list.setRemoveListener(onRemove);
   }

    @Override
    public DragSortListView getListView() {
        return (DragSortListView) super.getListView();
    }
    
}
