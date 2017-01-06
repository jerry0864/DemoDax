package com.kugou.sdk.share.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 分享参数
 *@author liuxiong
 *@since 2017/1/3 10:12
 */
public class ShareParams implements Parcelable{
    public String title;//分享title
    public String desc;//分享描述
    public String imgUrl;//图片url地址
    public String imgLocalPath;//图片本地地址
    public String vedioUrl;//视频url地址
    public String vedioLocalPath;//视频本地地址
    public String webUrl;//网页链接地址
    public int scene;//分享场景 例如：QQ：qq好友和qzone；微信：微信好友和朋友圈；如没有，可以不设置

    public ShareParams(){}
    protected ShareParams(Parcel in) {
        title = in.readString();
        desc = in.readString();
        imgUrl = in.readString();
        imgLocalPath = in.readString();
        vedioUrl = in.readString();
        vedioLocalPath = in.readString();
        webUrl = in.readString();
        scene = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(imgUrl);
        dest.writeString(imgLocalPath);
        dest.writeString(vedioUrl);
        dest.writeString(vedioLocalPath);
        dest.writeString(webUrl);
        dest.writeInt(scene);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShareParams> CREATOR = new Creator<ShareParams>() {
        @Override
        public ShareParams createFromParcel(Parcel in) {
            return new ShareParams(in);
        }

        @Override
        public ShareParams[] newArray(int size) {
            return new ShareParams[size];
        }
    };
}
