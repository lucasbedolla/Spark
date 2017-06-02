// Generated code from Butter Knife. Do not modify!
package cool.lucasleabres.ruby;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import cool.lucasleabres.ruby.activities.SearchyActivity;

public class SearchyActivity$$ViewBinder<T extends SearchyActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558563, "field 'query'");
    target.query = finder.castView(view, 2131558563, "field 'query'");
    view = finder.findRequiredView(source, 2131558562, "field 'search'");
    target.search = finder.castView(view, 2131558562, "field 'search'");
    view = finder.findRequiredView(source, 2131558564, "field 'go'");
    target.go = finder.castView(view, 2131558564, "field 'go'");
    view = finder.findRequiredView(source, 2131558560, "field 'recycler'");
    target.recycler = finder.castView(view, 2131558560, "field 'recycler'");
  }

  @Override public void unbind(T target) {
    target.query = null;
    target.search = null;
    target.go = null;
    target.recycler = null;
  }
}
