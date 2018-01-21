package com.jqh.mymovie.indircatior;

import java.util.List;

/**
 * Created by jiangqianghua on 18/1/18.
 */

public interface IPagerIndectorView extends IPagerChangeListener {

    void setPositionDataList(List<PositionData> list);
}
