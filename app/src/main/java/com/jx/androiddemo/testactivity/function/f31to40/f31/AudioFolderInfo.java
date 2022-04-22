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

package com.jx.androiddemo.testactivity.function.f31to40.f31;

import java.io.Serializable;
import java.util.List;

/**
 * Desction:视频文件夹
 */
public class AudioFolderInfo implements Serializable {
    private long folderId;
    private String folderName;
    private AudioInfo coverPhoto;
    private List<AudioInfo> audioList;

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

    public AudioInfo getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(AudioInfo coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public List<AudioInfo> getAudioList() {
        return audioList;
    }

    public void setAudioList(List<AudioInfo> audioList) {
        this.audioList = audioList;
    }
}
