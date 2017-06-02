// Generated code from Butter Knife. Do not modify!
package cool.lucasleabres.ruby;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import cool.lucasleabres.ruby.activities.ProfileActivity;

public class ProfileActivity$$ViewBinder<T extends ProfileActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558553, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 2131558553, "field 'mRecyclerView'");
    view = finder.findRequiredView(source, 2131558519, "field 'mToolbar'");
    target.mToolbar = finder.castView(view, 2131558519, "field 'mToolbar'");
    view = finder.findRequiredView(source, 2131558554, "field 'prog'");
    target.prog = finder.castView(view, 2131558554, "field 'prog'");
    view = finder.findRequiredView(source, 2131558552, "field 'profName'");
    target.profName = finder.castView(view, 2131558552, "field 'profName'");
    view = finder.findRequiredView(source, 2131558551, "field 'mBack'");
    target.mBack = finder.castView(view, 2131558551, "field 'mBack'");
  }

  @Override public void unbind(T target) {
    target.mRecyclerView = null;
    target.mToolbar = null;
    target.prog = null;
    target.profName = null;
    target.mBack = null;
  }
}
