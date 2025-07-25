package com.jx.androiddemo.testactivity.function.f31to40.f36

import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import com.jx.androiddemo.tool.ClickListenerUtil

class F36Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    var mF36AudioUtil: F36AudioUtil? = null
    private val tv_load1 by lazy { findViewById<View>(R.id.tv_load1) }
    private val tv_load2 by lazy { findViewById<View>(R.id.tv_load2) }
    private val img_play_pause by lazy { findViewById<ImageView>(R.id.img_play_pause) }
    private val img_play_pause2 by lazy { findViewById<ImageView>(R.id.img_play_pause2) }
    private val seekbar by lazy { findViewById<SeekBar>(R.id.seekbar) }
    private val seekbar2 by lazy { findViewById<SeekBar>(R.id.seekbar2) }
    private val tv_current_time by lazy { findViewById<TextView>(R.id.tv_current_time) }
    private val tv_total_time by lazy { findViewById<TextView>(R.id.tv_total_time) }
    private val tv_current_time2 by lazy { findViewById<TextView>(R.id.tv_current_time2) }
    private val tv_total_time2 by lazy { findViewById<TextView>(R.id.tv_total_time2) }

    companion object {
        val TAG = "F36Activity"
        val URL1 =
            "https://guiyu-tici.oss-cn-shanghai.aliyuncs.com/tici/149-2-20220331174043925.aac"

        //"https://guiyu-tici.oss-cn-shanghai.aliyuncs.com/tici/149-2-20220222095449261.aac"
        //"https://guiyu-tici.oss-cn-shanghai.aliyuncs.com/tici/149-2-20220411173608441.wav"
        val URL2 =
            "https://digital-public-dev.obs.cn-east-3.myhuaweicloud.com/bianyin-tts/audio/zh_male_chunhou.wav"

    }

    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_f36
    }

    override fun initEventAndData() {
        initView()
    }

    override fun onPause() {
        super.onPause()
        mF36AudioUtil?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mF36AudioUtil?.release()
    }

    private fun initView() {
        mF36AudioUtil = F36AudioUtil()
        mF36AudioUtil?.mListener = object : F36AudioUtil.Listener() {
            override fun onSetPlayImg(img: ImageView?, play: Boolean) {
                super.onSetPlayImg(img, play)
                img?.setImageResource(if (play) R.mipmap.img_audio_pause else R.mipmap.img_audio_play)
            }
        }
        tv_load1.setOnClickListener(this::onClick)
        tv_load2.setOnClickListener(this::onClick)
        img_play_pause.setOnClickListener(this::onClick)
        img_play_pause2.setOnClickListener(this::onClick)
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {
                mF36AudioUtil?.pause()
            }

            override fun onStopTrackingTouch(sb: SeekBar?) {
                setView1()
                mF36AudioUtil?.seek(seekbar.progress, URL1, img_play_pause.id)
            }

        })
        seekbar2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {
                mF36AudioUtil?.pause()
            }

            override fun onStopTrackingTouch(sb: SeekBar?) {
                setView2()
                mF36AudioUtil?.seek(seekbar2.progress, URL2, img_play_pause2.id)
            }

        })
    }

    private fun onClick(view: View) {
        if (!ClickListenerUtil.canClick()) return
        when (view.id) {
            R.id.tv_load1 -> {
                setView1()
                mF36AudioUtil?.setUrl(URL1, img_play_pause.id)
            }
            R.id.tv_load2 -> {
                setView2()
                mF36AudioUtil?.setUrl(URL2, img_play_pause2.id)
            }
            R.id.img_play_pause -> {
                setView1()
                mF36AudioUtil?.playOrPause(URL1, img_play_pause.id)
            }
            R.id.img_play_pause2 -> {
                setView2()
                mF36AudioUtil?.playOrPause(URL2, img_play_pause2.id)
            }
        }
    }

    private fun setView1() {
        mF36AudioUtil?.mSeekBar = seekbar
        mF36AudioUtil?.mCurTv = tv_current_time
        mF36AudioUtil?.mTotalTv = tv_total_time
        mF36AudioUtil?.mPlayImg = img_play_pause
    }

    private fun setView2() {
        mF36AudioUtil?.mSeekBar = seekbar2
        mF36AudioUtil?.mCurTv = tv_current_time2
        mF36AudioUtil?.mTotalTv = tv_total_time2
        mF36AudioUtil?.mPlayImg = img_play_pause2
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!hasFocus) {
            mF36AudioUtil?.pause()
        }
    }
}