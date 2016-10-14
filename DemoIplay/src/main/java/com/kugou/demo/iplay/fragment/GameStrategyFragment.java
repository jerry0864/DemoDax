
package com.kugou.demo.iplay.fragment;

import com.kugou.demo.iplay.R;
import com.kugou.demo.iplay.adapter.GameStrategyAdapter;
import com.kugou.demo.iplay.entity.GameStrategyInfo;
import com.kugou.demo.iplay.widget.irecyclerview.IRecyclerView;
import com.kugou.demo.iplay.widget.irecyclerview.OnLoadMoreListener;
import com.kugou.demo.iplay.widget.irecyclerview.OnRefreshListener;
import com.kugou.demo.iplay.widget.irecyclerview.footer.LoadMoreFooterView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * 游戏攻略列表
 * 
 * @author jerryliu
 * @since 2016/7/23 16:43
 */
public class GameStrategyFragment extends Fragment
        implements OnRefreshListener, OnLoadMoreListener {
    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    private IRecyclerView mRecyclerView;

    public GameStrategyFragment() {
        // Required empty public constructor
    }

    public static GameStrategyFragment newInstance(String param1) {
        GameStrategyFragment fragment = new GameStrategyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    private LoadMoreFooterView loadMoreFooterView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_news, container, false);
        mRecyclerView = (IRecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadMoreFooterView = (LoadMoreFooterView) mRecyclerView.getLoadMoreFooterView();

        mRecyclerView.setOnRefreshListener(this);
        mRecyclerView.setOnLoadMoreListener(this);

        // mRecyclerView.post(new Runnable() {
        // @Override
        // public void run() {
        // mRecyclerView.setRefreshing(true);
        // }
        // });

        ArrayList<GameStrategyInfo> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            GameStrategyInfo info = new GameStrategyInfo();
            info.setCommentNum(i);
            info.setReadNum(i * 2);
            info.setStrategyContentType(11);
            info.setStrategyDesc("评论内容相当精彩评论内容相当精彩评论内容相当精彩评论内容相当精彩评论内容相当精彩评论内容相当精彩");
            info.setStrategyTitle("南海局势一触即发");
            info.setAgreeNum(100 + i);
            info.setReadNum(200 * i);
            data.add(info);
        }
        adapter = new GameStrategyAdapter(getActivity());
        adapter.setData(data);
        mRecyclerView.setIAdapter(adapter);
        return view;
    }

    GameStrategyAdapter adapter;

    @Override
    public void onRefresh() {
        Toast.makeText(getActivity(), "onRefresh", Toast.LENGTH_SHORT).show();
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);
    }

    @Override
    public void onLoadMore(View loadMoreView) {
        Toast.makeText(getActivity(), "onLoadMore", Toast.LENGTH_SHORT).show();
        if (loadMoreFooterView.canLoadMore() && adapter.getItemCount() > 0) {
            loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
