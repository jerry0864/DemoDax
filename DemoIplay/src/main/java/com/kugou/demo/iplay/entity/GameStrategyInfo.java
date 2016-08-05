package com.kugou.demo.iplay.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 游戏攻略实体类
 * @author jerryliu
 * @since 2016/7/25 15:49
 */
public class GameStrategyInfo implements Parcelable {

    private int strategyType;//攻略类型
    private String strategyTitle;//攻略item标题
    private String strategyDesc;//攻略描述
    private int readNum;//阅读数量
    private int agreeNum;//赞的数量
    private int commentNum;//评论数量
    private int strategyImgUrl;//图标
    private int strategyContentType;//攻略内容类型（是否有视频，等）

    public GameStrategyInfo(){}

    public int getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(int strategyType) {
        this.strategyType = strategyType;
    }

    public String getStrategyTitle() {
        return strategyTitle;
    }

    public void setStrategyTitle(String strategyTitle) {
        this.strategyTitle = strategyTitle;
    }

    public String getStrategyDesc() {
        return strategyDesc;
    }

    public void setStrategyDesc(String strategyDesc) {
        this.strategyDesc = strategyDesc;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public int getAgreeNum() {
        return agreeNum;
    }

    public void setAgreeNum(int agreeNum) {
        this.agreeNum = agreeNum;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getStrategyImgUrl() {
        return strategyImgUrl;
    }

    public void setStrategyImgUrl(int strategyImgUrl) {
        this.strategyImgUrl = strategyImgUrl;
    }

    public int getStrategyContentType() {
        return strategyContentType;
    }

    public void setStrategyContentType(int strategyContentType) {
        this.strategyContentType = strategyContentType;
    }

    protected GameStrategyInfo(Parcel in) {
        strategyType = in.readInt();
        strategyTitle = in.readString();
        strategyDesc = in.readString();
        readNum = in.readInt();
        agreeNum = in.readInt();
        commentNum = in.readInt();
        strategyImgUrl = in.readInt();
        strategyContentType = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(strategyType);
        dest.writeString(strategyTitle);
        dest.writeString(strategyDesc);
        dest.writeInt(readNum);
        dest.writeInt(agreeNum);
        dest.writeInt(commentNum);
        dest.writeInt(strategyImgUrl);
        dest.writeInt(strategyContentType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GameStrategyInfo> CREATOR = new Creator<GameStrategyInfo>() {
        @Override
        public GameStrategyInfo createFromParcel(Parcel in) {
            return new GameStrategyInfo(in);
        }

        @Override
        public GameStrategyInfo[] newArray(int size) {
            return new GameStrategyInfo[size];
        }
    };
}
