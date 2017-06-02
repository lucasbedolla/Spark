// Generated code from Butter Knife. Do not modify!
package cool.lucasleabres.ruby;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import cool.lucasleabres.ruby.activities.MyLauncher;

public class MyLauncher$$ViewBinder<T extends MyLauncher> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558517, "field 'animView'");
    target.animView = finder.castView(view, 2131558517, "field 'animView'");
    view = finder.findRequiredView(source, 2131558518, "field 'rubyText'");
    target.rubyText = finder.castView(view, 2131558518, "field 'rubyText'");
  }

  @Override public void unbind(T target) {
    target.animView = null;
    target.rubyText = null;
  }
}
