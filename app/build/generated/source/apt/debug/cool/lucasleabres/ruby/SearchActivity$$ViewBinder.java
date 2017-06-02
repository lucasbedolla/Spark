// Generated code from Butter Knife. Do not modify!
package cool.lucasleabres.ruby;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import cool.lucasleabres.ruby.activities.SearchActivity;

public class SearchActivity$$ViewBinder<T extends SearchActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558557, "field 'editText'");
    target.editText = finder.castView(view, 2131558557, "field 'editText'");
    view = finder.findRequiredView(source, 2131558555, "field 'searchBar'");
    target.searchBar = finder.castView(view, 2131558555, "field 'searchBar'");
    view = finder.findRequiredView(source, 2131558556, "field 'back'");
    target.back = finder.castView(view, 2131558556, "field 'back'");
  }

  @Override public void unbind(T target) {
    target.editText = null;
    target.searchBar = null;
    target.back = null;
  }
}
