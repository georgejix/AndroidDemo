package com.jx.androiddemo.testactivity.function.f31to40.f32

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.jx.androiddemo.BaseMvpActivity
import com.jx.androiddemo.R
import com.jx.androiddemo.constant.Constants
import com.jx.androiddemo.testactivity.empty.EmptyContract
import com.jx.androiddemo.testactivity.empty.EmptyPresenter
import com.jx.androiddemo.tool.ClickListenerUtil
import kotlinx.android.synthetic.main.activity_f32.*
import java.util.concurrent.TimeUnit

class F32Activity : BaseMvpActivity<EmptyPresenter>(), EmptyContract.View {

    val mFoodProduction: Production<Food> = FoodProduction()
    val mFastFoodProduction: Production<Food> = FastFoodProduction()
    val mBurgerProduction: Production<Food> = BurgerProduction()
    val mFoodConsumer: Consumer<Burger> = FoodConsumer()
    val mFastFoodConsumer: Consumer<Burger> = FastFoodConsumer()
    val mBurgerConsumer: Consumer<Burger> = BurgerConsumer()

    companion object {
        val TAG2 = "f32"
    }

    override fun initInject() {
        activityComponent.inject(this)
    }

    override fun getLayout(): Int {
        return R.layout.activity_f32
    }

    override fun initEventAndData() {
        initView()
    }

    @SuppressLint("CheckResult")
    private fun initView() {
        val views = arrayListOf<TextView>(tv_1, tv_2, tv_3)
        var num = 0
        while (num < views.size) {
            var i = num
            views[num].setOnClickListener { v -> Log.d(TAG2, "${i}  ${num}") }
            num++
        }
        RxView.clicks(tv_4)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { v ->
                mFoodProduction.produce()
                mFastFoodProduction.produce()
                mBurgerProduction.produce()
            }
        RxView.clicks(tv_5)
            .throttleFirst(Constants.CLICK_TIME.toLong(), TimeUnit.MILLISECONDS)
            .compose(this.bindToLifecycle())
            .subscribe { v ->
                val burger = Burger()
                mFoodConsumer.consume(burger)
                mFastFoodConsumer.consume(burger)
                mBurgerConsumer.consume(burger)
            }
    }

    private fun onClick(view: View) {
        if (!ClickListenerUtil.canClick()) return
        when (view.id) {
        }
    }

    class FoodProduction : Production<Food> {
        override fun produce(): Food {
            Log.d(TAG2, "produce food")
            return Food()
        }
    }

    class FastFoodProduction : Production<FastFood> {
        override fun produce(): FastFood {
            Log.d(TAG2, "produce fastfood")
            return FastFood()
        }
    }

    class BurgerProduction : Production<Burger> {
        override fun produce(): Burger {
            Log.d(TAG2, "produce burger")
            return Burger()
        }
    }

    class FoodConsumer : Consumer<Food> {
        override fun consume(item: Food) {
            Log.d(TAG2, "eat food")
        }
    }

    class FastFoodConsumer : Consumer<FastFood> {
        override fun consume(item: FastFood) {
            Log.d(TAG2, "eat fastfood")
        }
    }

    class BurgerConsumer : Consumer<Burger> {
        override fun consume(item: Burger) {
            Log.d(TAG2, "eat burger")
        }
    }
}