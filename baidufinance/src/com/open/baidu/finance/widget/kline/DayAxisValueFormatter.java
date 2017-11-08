/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-8上午9:54:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
package com.open.baidu.finance.widget.kline;

import java.util.List;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.open.baidu.finance.bean.kline.TimeLineBean;

/**
 *****************************************************************************************************************************************************************************
 * 
 * @author :fengguangjing
 * @createTime:2017-11-8上午9:54:31
 * @version:4.2.4
 * @modifyTime:
 * @modifyAuthor:
 * @description:
 *****************************************************************************************************************************************************************************
 */
public class DayAxisValueFormatter  implements IAxisValueFormatter
{
 
    private BarLineChartBase<?> chart;
    private List<TimeLineBean> list;

    public DayAxisValueFormatter(BarLineChartBase<?> chart,List<TimeLineBean> list) {
        this.chart = chart;
        this.list = list;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return list.get((int)value).getTime()/100000+"";
    }

}
