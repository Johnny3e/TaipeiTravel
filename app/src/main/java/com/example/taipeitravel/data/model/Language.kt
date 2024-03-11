package com.example.taipeitravel.data.model

import androidx.appcompat.app.AppCompatDelegate
import timber.log.Timber
import timber.log.Timber.Forest.tag
import java.util.Locale

data class Language(
    val text: String,
    val tag: String
) {
    companion object {
        val chineseTradition = Language("繁體中文", "zh-tw")
        val chineseSimplified = Language("简体中文", "zh-cn")
        val english = Language("English", "en")
        val japan = Language("日本語", "ja")
        val korea = Language("한국어", "ko")
        val spanish = Language("español", "es")
        val thai = Language("แบบไทย", "th")
        val vietnamese = Language("Tiếng Việt", "vi")

        val supportedList = listOf(
            chineseTradition,
            chineseSimplified,
            english,
            japan,
            korea,
            spanish,
            thai,
            vietnamese
        )

        fun getCurrentLanguage(): Language {
            val locale = AppCompatDelegate.getApplicationLocales()[0]
            Timber.d("language-script-country ${locale?.language}-${locale?.script}-${locale?.country}")
            Timber.d("toLanguageTag ${locale?.toLanguageTag()}")
            return if (locale == null) {
                chineseTradition
            } else {
                if (locale.language == "zh") {
                    when {
                        locale.script == "Hant" || locale.country == "TW" -> chineseTradition
                        locale.script == "Hans" || locale.country == "CN" -> chineseSimplified
                        else -> chineseTradition
                    }
                } else {
                    supportedList.find { it.tag == locale.language } ?: chineseTradition
                }
            }
        }
    }
}