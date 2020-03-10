// Generated code from Butter Knife. Do not modify!
package com.app.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SavaPwdActivity$$ViewBinder<T extends SavePwdActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624147, "field 'mPatterLockView'");
    target.mPatterLockView = finder.castView(view, 2131624147, "field 'mPatterLockView'");
  }

  @Override public void unbind(T target) {
    target.mPatterLockView = null;
  }
}
