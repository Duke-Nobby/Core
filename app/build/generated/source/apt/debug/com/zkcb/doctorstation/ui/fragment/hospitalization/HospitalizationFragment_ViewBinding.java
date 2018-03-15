// Generated code from Butter Knife. Do not modify!
package com.zkcb.doctorstation.ui.fragment.hospitalization;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.zkcb.doctorstation.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HospitalizationFragment_ViewBinding implements Unbinder {
  private HospitalizationFragment target;

  @UiThread
  public HospitalizationFragment_ViewBinding(HospitalizationFragment target, View source) {
    this.target = target;

    target.test = Utils.findRequiredViewAsType(source, R.id.test, "field 'test'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HospitalizationFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.test = null;
  }
}
