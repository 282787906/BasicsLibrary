package com.liqg.basicslibrary.module;

/**
 * Created by heyao
 * 2015/11/23.
 */
public class ModuleUpdate {

    /**
     * 下载路径
     */
    private String updateUrl;
    /**
     * 更新类型
     * 0：无更新
     * 1：非强制更新
     * 2：强制更新
     */
    private int updateTag;
    /**
     * updateMd5
     */
    private String updateMd5;
    /**
     * 更新内容
     */
    private String updateContent;
    /**
     * 更新时间
     */
    private String updateTime;

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl;
    }
    /**
     * 更新类型
     * 0：无更新
     * 1：非强制更新
     * 2：强制更新
     */
    public int getUpdateTag() {
        return updateTag;
    }
    /**
     * 更新类型
     * 0：无更新
     * 1：非强制更新
     * 2：强制更新
     */
    public void setUpdateTag(int updateTag) {
        this.updateTag = updateTag;
    }

    public String getUpdateMd5() {
        return updateMd5;
    }

    public void setUpdateMd5(String updateMd5) {
        this.updateMd5 = updateMd5;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
