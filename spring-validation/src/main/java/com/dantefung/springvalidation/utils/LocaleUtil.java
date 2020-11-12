package com.dantefung.springvalidation.utils;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * @Author: DANTE FUNG
 * @Date:2019/8/27 16:00
 */
@Component
public class LocaleUtil {

    private static MessageSource resources;

    public LocaleUtil(MessageSource messageSource) {
        LocaleUtil.resources = messageSource;
    }

    private static final ThreadLocal<Locale> tlLocale = ThreadLocal.withInitial(() -> Locale.CHINESE);

    public static final String KEY_LANG = "lang";

    public static void setLocale(String locale) {
        setLocale(new Locale(locale));
    }

    public static void setLocale(Locale locale) {
        tlLocale.set(locale);
    }

    public static Locale getLocale() {
        return tlLocale.get();
    }

    public static void clearAllUserLang() {
        tlLocale.remove();
    }

    public static String getMessage(String msgKey, Object... args) {
        String msg = "";
        try {
            msg = resources.getMessage(msgKey, args, getLocale());
        } catch (Exception e) {
            msg = msgKey;
        }
        return msg;
    }
}
