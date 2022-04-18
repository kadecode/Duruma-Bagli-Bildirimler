package com.example.durumabaglibildirim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button buttonBildir;
    private NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBildir = findViewById(R.id.buttonBildir);

        buttonBildir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DurumaBagli();
            }
        });
    }

    public void DurumaBagli() {

        NotificationManager bildirimYoneticisi = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(MainActivity.this, KarsilamaEkraniActivity.class);

        PendingIntent gidilecekInten = PendingIntent.getActivities(this, 1, new Intent[]{intent}, PendingIntent.FLAG_UPDATE_CURRENT);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             String kanalId = "kanalId";
             String kanalAd= "kanalAd";
             String kanalTanım = "kanalTanım";
             int kanalOnceligi = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel kanal = bildirimYoneticisi.getNotificationChannel(kanalId);

            if (kanal == null) {
                kanal = new NotificationChannel(kanalId, kanalAd, kanalOnceligi);
                String kanalTanıman = kanalTanım;
                kanal.setDescription(kanalTanıman);
                bildirimYoneticisi.createNotificationChannel(kanal);
            }
            builder = new NotificationCompat.Builder(this,kanalId);
            builder.setContentTitle("Başlık");
            builder.setContentText("İçerik");
            builder.setSmallIcon(R.drawable.resim);
            builder.setAutoCancel(true);
            builder.setContentIntent(gidilecekInten);
        }else {
            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("Başlık");
            builder.setContentText("İçerik");
            builder.setSmallIcon(R.drawable.resim);
            builder.setAutoCancel(true);
            builder.setContentIntent(gidilecekInten);
            builder.setPriority(Notification.PRIORITY_HIGH);
            }
        bildirimYoneticisi.notify(1,builder.build());
        }

    }
