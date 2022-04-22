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

import java.io.Serializable;
import java.util.List;

/**
 * Desction:视频文件夹
 */
public class VideoFolderInfo implements Serializable {
    private long folderId;
    private String folderName;
    private VideoInfo coverPhoto;
    private List<VideoInfo> videoList;

    public long getFolderId() {
        return folderId;
    }

    public void setFolderId(long folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public VideoInfo getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(VideoInfo coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public List<VideoInfo> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoInfo> videoList) {
        this.videoList = videoList;
    }
}
