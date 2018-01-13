package com.jqh.mymovie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hejunlin.superindicatorlibray.CircleIndicator;
import com.hejunlin.superindicatorlibray.LoopViewPager;
import com.jqh.mymovie.base.BaseFragment;
import com.jqh.mymovie.model.Channel;

/**
 * Created by jiangqianghua on 17/9/23.
 */

public class HomeFragment extends BaseFragment {

    private GridView mGridView ;
    @Override
    protected void initView() {
        LoopViewPager viewPager = bindViewId(R.id.loopviewpager);
        CircleIndicator indicator = bindViewId(R.id.indicator);
        viewPager.setAdapter(new HomePicAdapter(getActivity()));
        viewPager.setLooperPic(true);//自动轮播
        indicator.setViewPager(viewPager);// indicator 需要知道viewpager
        mGridView = bindViewId(R.id.gv_channel);
        mGridView.setAdapter(new ChannelAdapter());
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 6:
                        // 直播
                        break;
                    case 7:
                        // 收藏
                        break;
                    case 8:
                        //记录
                        break;
                    default:
                        // 跳转对应频道
                        DetailListActivity.LaunchDetailListActivity(getActivity(),position+1);
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {

    }

    class ChannelAdapter extends BaseAdapter
    {
        @Override
        public int getCount() {
            return Channel.CHANNEL_COUNT;
        }

        @Override
        public Object getItem(int position) {
            return new Channel(position+1,getActivity());
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Channel channel = (Channel) getItem(position);
            ViewHolder holder ;
            if(convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.home_grid_item, null);
                holder = new ViewHolder();
                holder.textView = (TextView) convertView.findViewById(R.id.tv_home_item_text);
                holder.imageView = (ImageView) convertView.findViewById(R.id.iv_home_item_img);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.textView.setText(channel.getChannelName());
            holder.imageView.setImageResource(channel.getImageResId());
            return convertView;
        }
    }

    class ViewHolder{
        TextView textView ;
        ImageView imageView ;
    }
}
