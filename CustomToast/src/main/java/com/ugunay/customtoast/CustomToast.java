package com.ugunay.customtoast;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer ile çalışan özel toast tasarımı.
 * Özelleştirilmiş toast (new Toast().setView(View view)) kullanımı API 30 ve sonrasında kalktığı için
 * bu sınıf tasarlanmıştır.
 */
public class CustomToast {

    // Toast background colors...
    public static final int COLOR_NEUTRAL = 0;
    public static final int COLOR_SUCCESS = 1;
    public static final int COLOR_ERROR = 2;
    public static final int COLOR_WARNING = 3;
    public static final int COLOR_INFO = 4;

    // Toast icons...
    public static final int IC_CHECK_CIRCLE = R.drawable.ic_check_circle_for_custom_toast;
    public static final int IC_ERROR = R.drawable.ic_error_for_custom_toast;
    public static final int IC_WARNING = R.drawable.ic_warning_for_custom_toast;
    public static final int IC_INFO = R.drawable.ic_info_for_custom_toast;
    public static final int IC_MOOD = R.drawable.ic_mood_for_custom_toast;
    public static final int IC_MOOD_BAD = R.drawable.ic_mood_bad_for_custom_toast;

    // Extra short duration: 1000 milliseconds.
    public static final int LENGTH_EX_SHORT = 1000;

    // Short duration: 2000 milliseconds.
    public static final int LENGTH_SHORT = 2000;

    // Long duration: 3000 milliseconds.
    public static final int LENGTH_LONG = 3000;


    /**
     * Ekstra özelliklerle toast mesajı gösterir.
     *
     * @param context         context.
     * @param message         toast mesajı.
     * @param duration        toast mesajının görüntüleneceği süre.
     *                        Sınıf içindeki hazır değerler (LENGTH_SHORT) kullanılabilir.
     *                        Milisaniye cinsinden istenilen bir değer de girilebilir.
     * @param iconResId       toast ikonu. (Sıfır değeri girilirse ikon görünmeyecektir.)
     * @param backgroundColor toast penceresinin arka plan rengi.
     */
    public static void makeText(final Context context, final String message, final int duration,
                                final int iconResId, final int backgroundColor) {
        if (context != null) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_toast);
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawableResource(android.R.color.transparent);
                window.getAttributes().windowAnimations = R.style.custom_toast_animation;
            }

            // Toast background color...
            CardView crdToast = dialog.findViewById(R.id.crdToast);
            crdToast.setCardBackgroundColor(getBackgroundColor(context.getResources(), backgroundColor));

            // Toast icon...
            if (iconResId != 0) {
                ImageView imgIcon = dialog.findViewById(R.id.imgIcon);
                imgIcon.setVisibility(View.VISIBLE);
                imgIcon.setImageResource(iconResId);
            }

            // Toast message...
            if (message != null && !message.isEmpty()) {
                TextView txtMessage = dialog.findViewById(R.id.txtMessage);
                txtMessage.setText(message);
            }

            dialog.show();

            // duration süresi sonunda dialog penceresini kapatır.
            final Handler handler = new Handler();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Runnable runnable = dialog::dismiss;
                    // Handler nesnesi burada tanımlanırsa handler nesnesi oluşturulamıyor.
                    // Bu sebeple yukarda tanımlanmıştır.
                    handler.post(runnable);
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, duration);
        }
    }

    // Gelen renk tercihine ait renk kodunu döndürür.
    private static int getBackgroundColor(Resources res, int colorId) {
        switch (colorId) {
            case COLOR_NEUTRAL:
                return res.getColor(R.color.custom_toast_color_neutral);
            case COLOR_SUCCESS:
                return res.getColor(R.color.custom_toast_color_success);
            case COLOR_ERROR:
                return res.getColor(R.color.custom_toast_color_error);
            case COLOR_WARNING:
                return res.getColor(R.color.custom_toast_color_warning);
            case COLOR_INFO:
                return res.getColor(R.color.custom_toast_color_info);
            default:
                return colorId;
        }
    }

    /**
     * Gelen mesajı ikonsuz olarak toast penceresinde gösterir.
     * Bu fonksiyonun amacı en az parametre ile toast mesajı göstermek içindir.
     *
     * @param context context.
     * @param message toast message.
     */
    public static void shortMakeText(final Context context, final String message) {
        makeText(context, message, LENGTH_SHORT, 0, COLOR_NEUTRAL);
    }

    /**
     * "Works continues" hazır toast mesajını gösterir.
     * Uygulamaya yeni eklenecek özelliklerde hazır olarak bu metod kullanılabilir.
     *
     * @param context context.
     */
    public static void worksContinues(Context context) {
        makeText(context, context.getString(R.string.works_continues), LENGTH_EX_SHORT, IC_MOOD, COLOR_SUCCESS);
    }

}
