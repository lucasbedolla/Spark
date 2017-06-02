// Generated code from Butter Knife. Do not modify!
package cool.lucasleabres.ruby;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import cool.lucasleabres.ruby.activities.LikedActivity;

public class LikedActivity$$ViewBinder<T extends LikedActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558520, "field 'mback'");
    target.mback = finder.castView(view, 2131558520, "field 'mback'");
    view = finder.findRequiredView(source, 2131558522, "field 'recycler'");
    target.recycler = finder.castView(view, 2131558522, "field 'recycler'");
    view = finder.findRequiredView(source, 2131558523, "field 'progress'");
    target.progress = finder.castView(view, 2131558523, "field 'progress'");
    view = finder.findRequiredView(source, 2131558521, "field 'likedTitle'");
    target.likedTitle = finder.castView(view, 2131558521, "field 'likedTitle'");
  }

  @Override public void unbind(T target) {
    target.mback = null;
    target.recycler = null;
    target.progress = null;
    target.likedTitle = null;
  }
}
