package com.jx.androiddemo.testactivity.function.f31to40.f34

import android.view.View
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import com.jx.androiddemo.tool.ClickListenerUtil
import kotlinx.android.synthetic.main.activity_f34.*

class F34Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    private val mPath1 by lazy { "${externalCacheDir.toString()}${java.io.File.separator}f30.wav" }
    private val mPath2 =
        "https://guiyu-tici.oss-cn-shanghai.aliyuncs.com/tici/149-2-20220222095449261.aac"
    private var mAudioPlayerUtil: AudioPlayerUtil? = null

    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_f34
    }

    override fun initEventAndData() {
        initView()
    }

    private fun initView() {
        mAudioPlayerUtil = AudioPlayerUtil(mainLooper, lifecycle)
        mAudioPlayerUtil?.apply {
            mPath = mPath1
            mPlayImg = img_play_pause
            mSeekBar = seekbar
            mCurTimeTv = tv_current_time
            mTotalTimeTv = tv_total_time
            initPlayer()
        }
        tv_path1.setOnClickListener(this::onClick)
        tv_path2.setOnClickListener(this::onClick)
    }

    private fun onClick(view: View) {
        if (!ClickListenerUtil.canClick()) return
        when (view.id) {
            R.id.tv_path1 -> {
                mAudioPlayerUtil?.resetPath(mPath1)
            }
            R.id.tv_path2 -> {
                mAudioPlayerUtil?.resetPath(mPath2)
            }
        }
    }
}