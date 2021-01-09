package com.ugunay.customtoast;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer ile çalışan özel toast tasarımı.
 * Özelleştirilmiş toast (new Toast().setView()) kullanımı API 30 ve sonrasında kalktığı için
 * bu sınıf tasarlanmıştır.
 */
public class CustomToast {

    // Toast background colors...
    public static final int COLOR_SUCCESS = R.color.color_success;
    public static final int COLOR_ERROR = R.color.color_error;
    public static final int COLOR_WARNING = R.color.color_warning;
    public static final int COLOR_NEUTRAL = R.color.color_neutral;

    // Toast icons...
    public static final int IC_CHECK_CIRCLE = R.drawable.ic_check_circle;
    public static final int IC_ERROR = R.drawable.ic_error;
    public static final int IC_WARNING = R.drawable.ic_warning;
    public static final int IC_INFO = R.drawable.ic_info;
    public static final int IC_MOOD = R.drawable.ic_mood;
    public static final int IC_MOOD_BAD = R.drawable.ic_mood_bad;

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
     * @param backgroundResId toast penceresinin arka planı.
     */
    public static void makeText(final Context context, final String message, final int duration,
                                final int iconResId, final int backgroundResId) {
        if (context != null) {
            final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

            // Toast window...
            CardView crdToast = new CardView(context);
            CardView.LayoutParams crdToastLayoutParams = new CardView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            crdToast.setLayoutParams(crdToastLayoutParams);
            crdToast.setCardElevation(20);
            crdToast.setRadius(40);

            // Toast box...
            LinearLayout pnlToast = new LinearLayout(context);
            CardView.LayoutParams pnlToastLayoutParams = new CardView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
            pnlToast.setLayoutParams(pnlToastLayoutParams);
            pnlToast.setOrientation(LinearLayout.VERTICAL);
            pnlToast.setGravity(Gravity.CENTER);
            pnlToast.setBackgroundResource(backgroundResId);
            pnlToast.setPadding(50, 60, 50, 60);

            // Toast icon...
            if (iconResId != 0) {
                ImageView imgIcon = new ImageView(context);
                LinearLayout.LayoutParams imgIconLayoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                imgIconLayoutParams.setMargins(0, 0, 0, 40);
                imgIcon.setLayoutParams(imgIconLayoutParams);
                imgIcon.setImageResource(iconResId);
                pnlToast.addView(imgIcon);
            }

            // Toast message...
            if (message != null && !message.isEmpty()) {
                TextView txtMessage = new TextView(context);
                LinearLayout.LayoutParams txtMessageLayoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
                txtMessage.setLayoutParams(txtMessageLayoutParams);
                txtMessage.setGravity(Gravity.CENTER);
                txtMessage.setText(message);
                txtMessage.setTextColor(Color.WHITE);
                txtMessage.setTextSize(17);
                txtMessage.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
                pnlToast.addView(txtMessage);
            }

            crdToast.addView(pnlToast);

            // Set toast design to dialog...
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(crdToast);
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawableResource(R.color.color_transparent);
                window.getAttributes().windowAnimations = R.style.animation;
            }
            dialog.show();

            final Handler handler = new Handler();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    };
                    // Handler nesnesi burada tanımlanırsa handler nesnesi oluşturulamıyor.
                    // Bu sebeple yukarda tanımlanmıştır.
                    handler.post(runnable);
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, duration);
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
     * "Works continues." hazır toast mesajını gösterir.
     * Uygulamaya yeni eklenecek özelliklerde hazır olarak bu metod kullanılabilir.
     *
     * @param context context.
     */
    public static void worksContinues(Context context) {
        makeText(context, context.getString(R.string.works_continues), LENGTH_SHORT, IC_MOOD, COLOR_SUCCESS);
    }

}
