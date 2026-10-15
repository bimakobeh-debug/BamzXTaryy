package com.bamzxtary.proxy;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    private final int bg = Color.rgb(10, 7, 20);
    private final int card = Color.rgb(25, 15, 42);
    private final int pink = Color.rgb(239, 45, 232);
    private final int purple = Color.rgb(137, 50, 255);
    private LinearLayout root;
    private TextView connectionStatus;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);
        buildUi();
    }

    private GradientDrawable shape(int color, int stroke) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(16));
        d.setStroke(dp(1), stroke);
        return d;
    }

    private int dp(float v) { return (int)(v * getResources().getDisplayMetrics().density + 0.5f); }

    private TextView text(String s, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(s); t.setTextSize(size); t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        t.setGravity(Gravity.CENTER_VERTICAL);
        return t;
    }

    private LinearLayout column() {
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        return l;
    }

    private void pad(View v, int all) { v.setPadding(dp(all), dp(all), dp(all), dp(all)); }

    private void addCard(String title, String subtitle, View content) {
        LinearLayout c = column();
        c.setBackground(shape(card, purple));
        c.setPadding(dp(16), dp(12), dp(16), dp(14));
        TextView h = text(title, 18, Color.WHITE, true);
        c.addView(h);
        if (subtitle != null) {
            TextView sub = text(subtitle, 12, Color.LTGRAY, false);
            LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(-1, -2);
            sp.topMargin = dp(3); sp.bottomMargin = dp(8);
            c.addView(sub, sp);
        }
        if (content != null) c.addView(content);
        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(-1, -2);
        cp.bottomMargin = dp(12);
        root.addView(c, cp);
    }

    private Switch addSwitch(LinearLayout box, String label, String note) {
        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout labels = column();
        labels.addView(text(label, 15, Color.WHITE, true));
        labels.addView(text(note, 11, Color.LTGRAY, false));
        row.addView(labels, new LinearLayout.LayoutParams(0, -2, 1));
        Switch sw = new Switch(this);
        sw.setButtonTintList(null);
        row.addView(sw);
        LinearLayout.LayoutParams rp = new LinearLayout.LayoutParams(-1, -2);
        rp.topMargin = dp(8);
        box.addView(row, rp);
        return sw;
    }

    private EditText field(String hint, String value) {
        EditText e = new EditText(this);
        e.setSingleLine(true);
        e.setText(value);
        e.setHint(hint);
        e.setHintTextColor(Color.GRAY);
        e.setTextColor(Color.WHITE);
        e.setTextSize(14);
        e.setPadding(dp(12), dp(8), dp(12), dp(8));
        e.setBackground(shape(bg, purple));
        return e;
    }

    private void buildUi() {
        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bg);
        root = column();
        root.setPadding(dp(18), dp(18), dp(18), dp(24));
        scroll.addView(root);
        setContentView(scroll);

        TextView brand = text("✦  PROXY", 15, pink, true);
        root.addView(brand);
        TextView title = text("BamzXTary", 32, Color.WHITE, true);
        root.addView(title);
        TextView tagline = text("PROXY • VPN • VISUAL TOOLS", 12, pink, true);
        LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(-1, -2);
        tp.bottomMargin = dp(18);
        root.addView(tagline, tp);

        LinearLayout statusBox = column();
        statusBox.setBackground(shape(card, pink));
        statusBox.setPadding(dp(14), dp(12), dp(14), dp(12));
        connectionStatus = text("●  DEMO — BELUM TERHUBUNG", 14, Color.YELLOW, true);
        statusBox.addView(connectionStatus);
        TextView disclaimer = text("Antarmuka demo. Belum membuat koneksi proxy/VPN.", 12, Color.LTGRAY, false);
        statusBox.addView(disclaimer);
        LinearLayout.LayoutParams sbp = new LinearLayout.LayoutParams(-1, -2);
        sbp.bottomMargin = dp(14);
        root.addView(statusBox, sbp);

        LinearLayout proxyFields = column();
        TextView hostLabel = text("Host / IP proxy", 13, Color.WHITE, true);
        proxyFields.addView(hostLabel);
        EditText host = field("Masukkan host dari penyedia proxy", "");
        LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(-1, -2);
        fp.topMargin = dp(5); fp.bottomMargin = dp(10);
        proxyFields.addView(host, fp);
        proxyFields.addView(text("Port", 13, Color.WHITE, true));
        EditText port = field("Contoh: 8080", "");
        LinearLayout.LayoutParams pp = new LinearLayout.LayoutParams(-1, -2);
        pp.topMargin = dp(5); pp.bottomMargin = dp(10);
        proxyFields.addView(port, pp);
        Button connect = new Button(this);
        connect.setText("SIMPAN PENGATURAN");
        connect.setTextColor(Color.WHITE);
        connect.setBackground(shape(purple, pink));
        proxyFields.addView(connect);
        connect.setOnClickListener(v -> {
            if (host.getText().toString().trim().isEmpty() || port.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Isi host dan port dari penyedia proxy.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Pengaturan disimpan untuk demo; belum menghubungkan jaringan.", Toast.LENGTH_LONG).show();
            }
        });
        addCard("🌐  Proxy & VPN", "Form pengaturan demo", proxyFields);

        LinearLayout tools = column();
        addSwitch(tools, "Aim Assist", "Pengaturan latihan saja");
        addSwitch(tools, "ESP Box", "Kotak simulasi di aplikasi");
        addSwitch(tools, "ESP Line", "Garis simulasi di aplikasi");
        addSwitch(tools, "ESP Name", "Label objek contoh");
        addSwitch(tools, "ESP Health", "Indikator kesehatan simulasi");
        addCard("🎮  Visual Tools", "Tidak terhubung ke Free Fire", tools);

        LinearLayout colors = new LinearLayout(this);
        colors.setOrientation(LinearLayout.HORIZONTAL);
        String[] names = {"Kuning", "Biru", "Ungu", "Pink"};
        int[] colorsArr = {Color.YELLOW, Color.CYAN, purple, pink};
        for (int i = 0; i < names.length; i++) {
            final int selected = colorsArr[i];
            Button b = new Button(this);
            b.setText(names[i]); b.setTextSize(10); b.setTextColor(Color.WHITE);
            b.setBackground(shape(selected, Color.WHITE));
            LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(0, dp(46), 1);
            bp.setMargins(dp(2), dp(4), dp(2), dp(4));
            colors.addView(b, bp);
            b.setOnClickListener(v -> {
                title.setTextColor(selected);
                connectionStatus.setTextColor(selected);
            });
        }
        addCard("🎨  Warna Tema", "Pilih warna aksen", colors);

        TextView foot = text("Proxy BamzXTary • v1.0 • Demo UI", 11, Color.GRAY, false);
        foot.setGravity(Gravity.CENTER);
        root.addView(foot);
    }
}
