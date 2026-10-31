 package com.bug.gacor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler(Looper.getMainLooper());
    int count = 0, max = 0;
    String target = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40);

        final EditText etNumber = new EditText(this);
        etNumber.setHint("Nomor Target (628xxx)");
        layout.addView(etNumber);

        final EditText etCount = new EditText(this);
        etCount.setHint("Jumlah Spam");
        layout.addView(etCount);

        Button btn = new Button(this);
        btn.setText("MULAI BUG!");
        layout.addView(btn);

        final TextView tvStatus = new TextView(this);
        layout.addView(tvStatus);

        setContentView(layout);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                target = etNumber.getText().toString();
                max = Integer.parseInt(etCount.getText().toString());
                count = 0;
                btn.setEnabled(false);
                startBug(tvStatus, btn);
            }
        });
    }

    void startBug(final TextView tv, final Button btn) {
        if (count >= max) {
            tv.setText("BUG SELESAI!");
            btn.setEnabled(true);
            return;
        }
        count++;
        tv.setText("Tembak ke-" + count + " / " + max);

        try {
            String url = "https://wa.me/" + target + "?text=" + Uri.encode("SPAM KE-" + count + " 💀");
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            i.setPackage("com.whatsapp");
            startActivity(i);
        } catch (Exception e) {
            tv.setText("WA tidak ditemukan");
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startBug(tv, btn);
            }
        }, 200);
    }
}
