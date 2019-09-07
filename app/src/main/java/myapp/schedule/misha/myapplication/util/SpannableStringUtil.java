package myapp.schedule.misha.myapplication.util;

import android.text.SpannableStringBuilder;

public class SpannableStringUtil {

    public static SpannableStringBuilder getString(String stringData) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(stringData);
        spannableStringBuilder = new StringFormatter().getUrlLink(spannableStringBuilder);
        spannableStringBuilder = new StringFormatter().getEmail(spannableStringBuilder);
        return spannableStringBuilder;
    }
}
