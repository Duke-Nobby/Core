// Generated code from Butter Knife. Do not modify!
package com.zkcb.doctorstation.ui.fragment.doctorCenter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.zkcb.doctorstation.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DoctorCenterFragment_ViewBinding implements Unbinder {
  private DoctorCenterFragment target;

  @UiThread
  public DoctorCenterFragment_ViewBinding(DoctorCenterFragment target, View source) {
    this.target = target;

    target.text = Utils.findRequiredViewAsType(source, R.id.text, "field 'text'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DoctorCenterFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.text = null;
  }
}
