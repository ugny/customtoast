package com.ugunay.myutils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Timer ile çalışan özel toast tasarımı.
 * Özelleştirilmiş toast (new Toast().setView()) kullanımı API 30 ve sonrasında kalktığı için bu sınıf tasarlanmıştır.
 */
public class MyToast {

    public static final int COLOR_SUCCESS = R.color.color_success;
    public static final int COLOR_ERROR = R.color.color_error;
    public static final int COLOR_WARNING = R.color.color_warning;
    public static final int COLOR_NEUTRAL = R.color.color_neutral;

    public static final int IC_CHECK_CIRCLE = R.drawable.ic_check_circle;
    public static final int IC_ERROR = R.drawable.ic_error;
    public static final int IC_WARNING = R.drawable.ic_warning;
    public static final int IC_INFO = R.drawable.ic_info;
    public static final int IC_MOOD = R.drawable.ic_mood;
    public static final int IC_MOOD_BAD = R.drawable.ic_mood_bad;

    public static final int LENGTH_SHORT = 2500;
    public static final int LENGTH_LONG = 4000;


    /**
     * Toast mesajı gösterir.
     *
     * @param context              mesajı gösterecek aktivite.
     * @param message              toast mesajı.
     * @param duration             mesajın görüntüleneceği süre.
     *                             Sınıf içindeki hazır değerler kullanılabilir.
     *                             Milisaniye cinsinden istenilen bir değer de girilebilir.
     * @param iconResId            mesajın başında gösterilecek olan ikon.
     * @param backgroundResColorId mesaj penceresinin arka plan rengi.
     */
    public static void makeText(final Context context, final String message, final int duration,
                                final int iconResId, final int backgroundResColorId) {

        final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;

        // Toast çerçevesi...
        CardView crdToast = new CardView(context);
        CardView.LayoutParams crdToastLayoutParams = new CardView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        crdToast.setLayoutParams(crdToastLayoutParams);
        crdToast.setCardElevation(20);
        crdToast.setRadius(40);

        // Toast kutusu...
        LinearLayout pnlToast = new LinearLayout(context);
        CardView.LayoutParams pnlToastLayoutParams = new CardView.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        pnlToast.setLayoutParams(pnlToastLayoutParams);
        pnlToast.setOrientation(LinearLayout.VERTICAL);
        pnlToast.setGravity(Gravity.CENTER);
        pnlToast.setPadding(50, 40, 50, 60);
        pnlToast.setBackgroundResource(backgroundResColorId);

        // Toast ikonu...
        ImageView imgIcon = new ImageView(context);
        LinearLayout.LayoutParams imgIconLayoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        imgIconLayoutParams.setMargins(0, 0, 0, 40);
        imgIcon.setLayoutParams(imgIconLayoutParams);
        imgIcon.setImageResource(iconResId);

        // Toast mesajı...
        TextView txtMessage = new TextView(context);
        LinearLayout.LayoutParams txtMessageLayoutParams = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        txtMessage.setLayoutParams(txtMessageLayoutParams);
        txtMessage.setGravity(Gravity.CENTER);
        txtMessage.setText(message);
        txtMessage.setTextColor(Color.WHITE);
        txtMessage.setTextSize(17);
        txtMessage.setTypeface(Typeface.MONOSPACE);

        // View eklemeleri...
        pnlToast.addView(imgIcon);
        pnlToast.addView(txtMessage);
        crdToast.addView(pnlToast);

        // Toast tasarımımızın dialog nesnesine set edilmesi.
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(crdToast);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

    /**
     * En az parametre ile toast penceresi gösterir.
     * @param context context.
     * @param message toast message.
     */
    public static void standardMakeText(final Context context, final String message) {
        makeText(context, message, LENGTH_SHORT, IC_INFO, COLOR_NEUTRAL);
    }

    public static void worksContinues(Context context) {
        makeText(context, context.getString(R.string.works_continues), LENGTH_SHORT, IC_MOOD, COLOR_SUCCESS);
    }

}
