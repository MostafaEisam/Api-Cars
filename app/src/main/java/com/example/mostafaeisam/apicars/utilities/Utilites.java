package com.example.mostafaeisam.apicars.utilities;

import java.util.Locale;

public class Utilites {

    public static boolean isArabicLocale() {
        return Locale.getDefault().getLanguage().equals("ar");
    }

}
