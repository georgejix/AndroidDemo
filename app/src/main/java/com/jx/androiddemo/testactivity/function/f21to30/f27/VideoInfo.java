/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.jx.androiddemo.testactivity.function.f21to30.f27;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Desction:视频信息
 */
public class VideoInfo implements Serializable {

    private long videoId;
    private String videoPath;
    private long duration;
    private long size;
    private int width;
    private int height;

    public VideoInfo() {
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }
    //
    //public String getThumbPath() {
    //    return thumbPath;
    //}
    //
    //public void setThumbPath(String thumbPath) {
    //    this.thumbPath = thumbPath;
    //}


    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof VideoInfo)) {
            return false;
        }
        VideoInfo info = (VideoInfo) o;
        if (info == null) {
            return false;
        }

        return TextUtils.equals(info.getVideoPath(), getVideoPath());
    }
}
