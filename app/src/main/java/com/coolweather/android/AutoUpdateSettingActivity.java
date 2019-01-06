package com.coolweather.android;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AutoUpdateSettingActivity extends AppCompatActivity {

    private Button back;
    private CheckBox checkUpdate;
    private RelativeLayout interval_layout;
    private RadioButton time1, time2, time3,time4;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_update_setting);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        back = (Button) findViewById(R.id.back_button);
        checkUpdate = (CheckBox) findViewById(R.id.auto_update);
        interval_layout = (RelativeLayout) findViewById(R.id.interval_layout);
        time1 = (RadioButton) findViewById(R.id.time1);
        time2 = (RadioButton) findViewById(R.id.time2);
        time3 = (RadioButton) findViewById(R.id.time3);
        time4 = (RadioButton) findViewById(R.id.time4);
        final View view = (View) findViewById(R.id.view);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isChecked = pref.getBoolean("auto_update", true);
        int hour = pref.getInt("hour", 1);
        if (hour == 1) {
            time1.setChecked(true);
        } else if (hour == 2) {
            time2.setChecked(true);
        } else if (hour == 5) {
            time3.setChecked(true);
        } else {
            time4.setChecked(true);
            time4.setText(hour+"(自定义)");
        }
        if (isChecked) {
            checkUpdate.setChecked(true);
            interval_layout.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        } else {
            checkUpdate.setChecked(false);
            interval_layout.setVisibility(View.GONE);
            view.setVisibility(View.GONE);
        }

        checkUpdate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    interval_layout.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                } else {
                    interval_layout.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                }
            }
        });
        time4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText et = new EditText(AutoUpdateSettingActivity.this);
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog alertDialog = new AlertDialog.Builder(AutoUpdateSettingActivity.this)
                        .setTitle("请输入间隔时间")
                        .setView(et)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //按下确定键后的事件
                                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(AutoUpdateSettingActivity.this).edit();
                                int define_hour = Integer.valueOf(et.getText().toString());
                                editor.putInt("hour", define_hour);
                                editor.apply();
                                time4.setText(define_hour + "(自定义)");
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref = PreferenceManager.getDefaultSharedPreferences(AutoUpdateSettingActivity.this);
                int hour = pref.getInt("hour", 1);
                if (time1.isChecked()) {
                    hour = 1;
                } else if (time2.isChecked()) {
                    hour = 2;
                } else if (time3.isChecked()) {
                    hour = 5;
                } else { }
                editor = pref.edit();
                if (checkUpdate.isChecked()) {
                    editor.putBoolean("auto_update", true);
                    editor.putInt("hour", hour);
                } else {
                    editor.putBoolean("auto_update", false);
                }
                editor.apply();
                Toast.makeText(AutoUpdateSettingActivity.this, "设置已保存", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        pref = PreferenceManager.getDefaultSharedPreferences(AutoUpdateSettingActivity.this);
        int hour = pref.getInt("hour", 1);
        if (time1.isChecked()) {
            hour = 1;
        } else if (time2.isChecked()) {
            hour = 2;
        } else if (time3.isChecked()) {
            hour = 5;
        } else { }
        editor = pref.edit();
        if (checkUpdate.isChecked()) {
            editor.putBoolean("auto_update", true);
            editor.putInt("hour", hour);
        } else {
            editor.putBoolean("auto_update", false);
        }
        editor.apply();
        Toast.makeText(AutoUpdateSettingActivity.this, "设置已保存", Toast.LENGTH_SHORT).show();
        finish();
    }
}
