// Generated code from Butter Knife. Do not modify!
package cool.lucasleabres.ruby;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import cool.lucasleabres.ruby.activities.SettingsActivity;

public class SettingsActivity$$ViewBinder<T extends SettingsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558570, "field 'cachingChecked'");
    target.cachingChecked = finder.castView(view, 2131558570, "field 'cachingChecked'");
    view = finder.findRequiredView(source, 2131558569, "field 'multiPostChecked'");
    target.multiPostChecked = finder.castView(view, 2131558569, "field 'multiPostChecked'");
    view = finder.findRequiredView(source, 2131558565, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131558565, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131558568, "field 'info'");
    target.info = finder.castView(view, 2131558568, "field 'info'");
    view = finder.findRequiredView(source, 2131558567, "field 'logout'");
    target.logout = finder.castView(view, 2131558567, "field 'logout'");
    view = finder.findRequiredView(source, 2131558566, "field 'gem'");
    target.gem = finder.castView(view, 2131558566, "field 'gem'");
    view = finder.findRequiredView(source, 2131558518, "field 'rubyText'");
    target.rubyText = finder.castView(view, 2131558518, "field 'rubyText'");
  }

  @Override public void unbind(T target) {
    target.cachingChecked = null;
    target.multiPostChecked = null;
    target.toolbar = null;
    target.info = null;
    target.logout = null;
    target.gem = null;
    target.rubyText = null;
  }
}
