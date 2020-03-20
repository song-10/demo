// Generated code from Butter Knife. Do not modify!
package com.example.sm4forandroid.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SavePwdActivity$$ViewBinder<T extends com.example.sm4forandroid.activity.SavePwdActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624139, "field 'mPatterLockView'");
    target.mPatterLockView = finder.castView(view, 2131624139, "field 'mPatterLockView'");
  }

  @Override public void unbind(T target) {
    target.mPatterLockView = null;
  }
}
