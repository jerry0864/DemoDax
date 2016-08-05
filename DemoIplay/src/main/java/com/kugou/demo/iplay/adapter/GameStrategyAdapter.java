package com.kugou.demo.iplay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kugou.demo.iplay.R;
import com.kugou.demo.iplay.entity.GameStrategyInfo;

import java.util.ArrayList;

/**
 * 攻略列表适配器
 * @author jerryliu
 * @since 2016/7/25 15:43
 */
public class GameStrategyAdapter extends RecyclerView.Adapter<GameStrategyAdapter.GameStrategyViewHolder>{
    private ArrayList<GameStrategyInfo> data;
    private Context context;
    public GameStrategyAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<GameStrategyInfo> data){
        this.data = data;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public GameStrategyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_strategy_list_item,null);
        return new GameStrategyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GameStrategyViewHolder holder, int position) {
        if(data==null){
            return;
        }
        GameStrategyInfo info = data.get(position);
        if(info!=null){
            holder.strategyTitle.setText(info.getStrategyTitle());
            holder.strategyDesc.setText(info.getStrategyDesc());
            holder.strategyReadNum.setText(info.getReadNum()+"阅读");
            holder.strategyAgreeNum.setText(info.getAgreeNum()+"赞");
            holder.strategyCommentNum.setText(info.getCommentNum()+"评论");
            //TODO 其他字段待补充
        }
    }

    @Override
    public int getItemCount() {
        if(data!=null){
            return data.size();
        }
        return 0;
    }

    public class GameStrategyViewHolder extends RecyclerView.ViewHolder{

        private TextView strategyTitle,strategyDesc,strategyReadNum,strategyAgreeNum,strategyCommentNum;
        private ImageView strategyTypeFlag,strategyIcon,videoPlayIcon;

        public GameStrategyViewHolder(View itemView) {
            super(itemView);
            strategyTitle = (TextView) itemView.findViewById(R.id.strategy_title);
            strategyDesc = (TextView) itemView.findViewById(R.id.strategy_desc);
            strategyReadNum = (TextView) itemView.findViewById(R.id.strategy_read_num);
            strategyAgreeNum = (TextView) itemView.findViewById(R.id.strategy_agree_num);
            strategyCommentNum = (TextView) itemView.findViewById(R.id.strategy_comment_num);

            strategyTypeFlag = (ImageView) itemView.findViewById(R.id.strategy_type_flag);
            strategyIcon = (ImageView) itemView.findViewById(R.id.strategy_icon);
            videoPlayIcon = (ImageView) itemView.findViewById(R.id.strategy_video_play_img);
        }
    }
}
