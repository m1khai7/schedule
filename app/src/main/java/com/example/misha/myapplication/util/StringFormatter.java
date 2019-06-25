package com.example.misha.myapplication.util;

import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.webkit.URLUtil;

import androidx.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFormatter {

    private String response;
    private String text;
    private int index_s;
    private int index_e;
    private Pattern mPattern;
    private Matcher mMatcher;

    public SpannableStringBuilder getFormattedString(String text) {

        SpannableStringBuilder formattedText;

        // Добавление спецсимволов
        final String textPost = text
                .replaceAll("<br>", "\n")
                .replaceAll("&nbsp;", " ")
                .replaceAll("&gt;", ">")
                .replaceAll("&lt;", "<")
                .replaceAll("&quot;", "\"")
                .replaceAll("&laquo;", "«")
                .replaceAll("&raquo;", "»")
                .replaceAll("&ndash;", "–")
                .replaceAll("&mdash;", "—")
                .replaceAll("&pound;", "£")
                .replaceAll("&euro;", "€")
                .replaceAll("&sect;", "§")
                .replaceAll("&copy;", "©")
                .replaceAll("&reg;", "®")
                .replaceAll("&trade;", "™")
                .replaceAll("&amp;", "&");

        formattedText = new SpannableStringBuilder(textPost);
        // Хеш
        if (textPost.contains("#"))
            formattedText = getHashtag(formattedText);
        // Url
        formattedText = getUrlLink(formattedText);
        // Почта
        if (text.contains("@"))
            formattedText = getEmail(formattedText);

        return formattedText;
    }

    private SpannableStringBuilder getHashtag(SpannableStringBuilder textSpannable) {

        text = textSpannable.toString();

        mPattern = Pattern.compile("#[а-яА-Яa-zA-Z0-9ё\\-_!@%$&*()=+\"№;:?{}]{2,}");
        mMatcher = mPattern.matcher(text);

        while (mMatcher.find()) {
            index_s = mMatcher.start();
            index_e = mMatcher.end();
            response = text.substring(index_s, index_e);

            textSpannable.setSpan(new LinkClickableSpan(response, 0), index_s, index_e, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return textSpannable;
    }

    public SpannableStringBuilder getUrlLink(SpannableStringBuilder textSpannable) {

        text = textSpannable.toString();

        mPattern = Pattern.compile("(((https?|ftp|file)://)|(www.))[a-zA-Zа-яА-Я0-9+&#/%?=~_\\-|!:,.;]+\\.[a-zA-Zа-яА-Я0-9+&@#/%?=~_\\-|]+");
        mMatcher = mPattern.matcher(text);

        while (mMatcher.find()) {
            index_s = mMatcher.start();
            index_e = mMatcher.end();
            response = text.substring(mMatcher.start(), mMatcher.end());

            textSpannable.setSpan(new LinkClickableSpan(response, 2), index_s, index_e, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return textSpannable;
    }

    public SpannableStringBuilder getEmail(SpannableStringBuilder textSpannable) {

        text = textSpannable.toString();

        mPattern = Pattern.compile("[a-zA-Z0-9+_\\-.]+@[a-zA-Z0-9+_\\-.]+\\.[a-zA-Z0-9+_\\-]+");
        mMatcher = mPattern.matcher(text);

        while (mMatcher.find()) {
            index_s = mMatcher.start();
            index_e = mMatcher.end();
            response = text.substring(index_s, index_e);

            textSpannable.setSpan(new LinkClickableSpan(response, 3), index_s, index_e, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        return textSpannable;
    }

    public SpannableString groupFormated(String text) {

        mPattern = Pattern.compile("[А-Я]{1,4}([а-я]{1})?-\\d{1,2}([а-яА-Я]{1})?");
        mMatcher = mPattern.matcher(text);
        final SpannableString mSpannableString = new SpannableString(text);

        while (mMatcher.find()) {
            index_s = mMatcher.start();
            index_e = mMatcher.end();

            mSpannableString.setSpan(new LinkClickableSpan(null, 5), index_s, index_e, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return mSpannableString;
    }

    class LinkClickableSpan extends ClickableSpan {

        String link;
        int id;

        LinkClickableSpan(String link, int id) {
            this.link = link;
            this.id = id;
        }

        public void onClick(@NonNull View tv) {

            String url = null;
            Intent mIntent = null;

            switch (id) {
                // url ссылка
                case 0:
                    if (URLUtil.isValidUrl(link)) {
                        mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    } else {
                        mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + link));
                    }

                    break;
                // Почта
                case 1:
                    mIntent = new Intent(Intent.ACTION_SENDTO)
                            .setType("text/plain")
                            .setData(Uri.parse("mailto:" + link));
                    break;
                case 2:
                    mIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", link, null));
                    break;
            }

            if (mIntent != null)
                tv.getContext().startActivity(mIntent);
        }

        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setUnderlineText(false);
            ds.setColor(ds.linkColor);
        }
    }
}