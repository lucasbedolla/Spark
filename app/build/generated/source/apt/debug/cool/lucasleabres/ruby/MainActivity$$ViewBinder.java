// Generated code from Butter Knife. Do not modify!
package cool.lucasleabres.ruby;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import cool.lucasleabres.ruby.activities.MainActivity;

public class MainActivity$$ViewBinder<T extends MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558534, "field 'refreshLayout'");
    target.refreshLayout = finder.castView(view, 2131558534, "field 'refreshLayout'");
    view = finder.findRequiredView(source, 2131558545, "field 'fab'");
    target.fab = finder.castView(view, 2131558545, "field 'fab'");
    view = finder.findRequiredView(source, 2131558536, "field 'menu'");
    target.menu = finder.castView(view, 2131558536, "field 'menu'");
    view = finder.findRequiredView(source, 2131558533, "field 'coordinator'");
    target.coordinator = finder.castView(view, 2131558533, "field 'coordinator'");
    view = finder.findRequiredView(source, 2131558546, "field 'prog'");
    target.prog = finder.castView(view, 2131558546, "field 'prog'");
    view = finder.findRequiredView(source, 2131558547, "field 'reconnect'");
    target.reconnect = finder.castView(view, 2131558547, "field 'reconnect'");
    view = finder.findRequiredView(source, 2131558537, "field 'profileButton'");
    target.profileButton = finder.castView(view, 2131558537, "field 'profileButton'");
    view = finder.findRequiredView(source, 2131558543, "field 'settingsButton'");
    target.settingsButton = finder.castView(view, 2131558543, "field 'settingsButton'");
    view = finder.findRequiredView(source, 2131558535, "field 'mRecyclerView'");
    target.mRecyclerView = finder.castView(view, 2131558535, "field 'mRecyclerView'");
    view = finder.findRequiredView(source, 2131558539, "field 'profileText'");
    target.profileText = finder.castView(view, 2131558539, "field 'profileText'");
    view = finder.findRequiredView(source, 2131558540, "field 'dashButton'");
    target.dashButton = finder.castView(view, 2131558540, "field 'dashButton'");
    view = finder.findRequiredView(source, 2131558544, "field 'likes'");
    target.likes = finder.castView(view, 2131558544, "field 'likes'");
    view = finder.findRequiredView(source, 2131558542, "field 'search'");
    target.search = finder.castView(view, 2131558542, "field 'search'");
    view = finder.findRequiredView(source, 2131558541, "field 'post'");
    target.post = finder.castView(view, 2131558541, "field 'post'");
  }

  @Override public void unbind(T target) {
    target.refreshLayout = null;
    target.fab = null;
    target.menu = null;
    target.coordinator = null;
    target.prog = null;
    target.reconnect = null;
    target.profileButton = null;
    target.settingsButton = null;
    target.mRecyclerView = null;
    target.profileText = null;
    target.dashButton = null;
    target.likes = null;
    target.search = null;
    target.post = null;
  }
}
