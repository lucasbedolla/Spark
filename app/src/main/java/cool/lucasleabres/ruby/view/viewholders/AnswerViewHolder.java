package cool.lucasleabres.ruby.view.viewholders;

import android.view.View;
import android.widget.TextView;

import cool.lucasleabres.ruby.R;

/**
 * Created by Lucas Bedolla on 5/31/2017.
 */

public class AnswerViewHolder extends BasicViewHolder {
    private TextView vQuestion;
    private TextView vAnswer;

    public AnswerViewHolder(View v) {
        super(v);
        vQuestion = (TextView) v.findViewById(R.id.question);
        vAnswer = (TextView) v.findViewById(R.id.answer);
    }
}

