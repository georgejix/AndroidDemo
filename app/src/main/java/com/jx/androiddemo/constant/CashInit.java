package com.jx.androiddemo.constant;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.DialogFragment;

import com.jx.arch.util.GeneralUtils;
import com.jx.arch.util.QMLog;

import java.util.Stack;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CashInit
{

    @Inject
    CashInit(Context context)
    {

    }

    /**
     * 本地activity集合
     */
    private static Stack<Activity> storeActivities = new Stack<>();

    private static Stack<DialogFragment> storeDialogFragments = new Stack<>();


    /**
     * <删除>
     */
    public void deleteActivity(Activity activity)
    {
        if (activity != null)
        {
            storeActivities.remove(activity);
            activity.finish();
        }
    }

    /**
     * <添加activity>
     */
    public void addActivity(Activity activity)
    {
        storeActivities.add(activity);
    }

    /**
     * 获取当前的Activity
     *
     * @return
     */
    public Activity getCurActivity()
    {
        return storeActivities.empty() ? null : storeActivities.lastElement();
    }

    /**
     * <跳转登陆界面>
     */
    public void startToLoginActivity()
    {
        try
        {
            for (Activity activity : storeActivities)
            {
                /*if (!(activity instanceof LoginActivity))
                {
                    activity.finish();
                }*/
            }
        } catch (Exception e)
        {
            QMLog.e("CashInit", "finish activity exception:" + e.getMessage());
        }
    }

    /**
     * <退出应用>
     */
    public void exitApplication()
    {
        try
        {
            for (Activity activity : storeActivities)
            {
                activity.finish();
            }
        } catch (Exception e)
        {
            QMLog.e("CashInit", "finish activity exception:" + e.getMessage());
        } finally
        {
            System.exit(0);
        }
    }

    /**
     * <删除DialogFragment>
     */
    public void removeDialogFragment(DialogFragment dialogFragment)
    {
        if (dialogFragment != null)
        {
            storeDialogFragments.remove(dialogFragment);
        }
    }

    /**
     * <添加DialogFragment>
     */
    public void addDialogFragment(DialogFragment dialogFragment)
    {
        storeDialogFragments.add(dialogFragment);
    }

    public void dismissAllDialogFragment()
    {
        if (GeneralUtils.isNotNullOrZeroSize(storeDialogFragments))
        {
            for (DialogFragment dialogFragment : storeDialogFragments)
            {
                if (dialogFragment != null)
                {
                    dialogFragment.dismiss();
                }
            }
            storeDialogFragments.clear();
        }
    }

    public DialogFragment getCurDialogFragment()
    {
        return storeDialogFragments.lastElement();
    }
}
