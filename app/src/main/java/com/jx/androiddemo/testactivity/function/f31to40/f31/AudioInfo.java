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

import android.net.Uri;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * Desction:视频信息
 */
public class AudioInfo implements Serializable {

    private long audioId;
    private String audioPath;
    private long duration;
    private long size;
    private Uri uri;

    public AudioInfo() {
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
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

    public long getAudioId() {
        return audioId;
    }

    public void setAudioId(long audioId) {
        this.audioId = audioId;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof AudioInfo)) {
            return false;
        }
        AudioInfo info = (AudioInfo) o;
        if (info == null) {
            return false;
        }

        return TextUtils.equals(info.getAudioPath(), getAudioPath());
    }
}
