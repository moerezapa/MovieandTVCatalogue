package zap.dev.movieandtvcatalogue.reminder;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;

import zap.dev.movieandtvcatalogue.R;

public class ReminderSettingActivity extends AppCompatActivity {
    private ReminderReceiver reminderReceiver;

    private Boolean isReleaseReminder, isDailyReminder;
    private Switch switchDailyReminder, switchReleaseReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        getSupportActionBar().setTitle(getString(R.string.setting_reminder));

        switchDailyReminder = findViewById(R.id.switch_dailyreminder);
        switchReleaseReminder = findViewById(R.id.switch_releasereminder);

        reminderReceiver = new ReminderReceiver();

        switchDailyReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isDailyReminder = switchDailyReminder.isChecked();
                if (isDailyReminder)
                    reminderReceiver.setReminder(getApplicationContext(), ReminderReceiver.DAILY_REMINDER,
                                                        "07:00", getString(R.string.daily_reminder));
                    else
                        reminderReceiver.cancelReminder(getApplicationContext(), ReminderReceiver.DAILY_REMINDER);
            }
        });

        switchReleaseReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isReleaseReminder = switchReleaseReminder.isChecked();
                if (isReleaseReminder){
                    switchReleaseReminder.setEnabled(true);
                    reminderReceiver.setReminder(getApplicationContext(), ReminderReceiver.RELEASETODAY_REMINDER,
                            "08:00", getString(R.string.upcoming_reminder_msg));
                }
                    else {
                        switchReleaseReminder.setChecked(false);
                        reminderReceiver.cancelReminder(getApplicationContext(), ReminderReceiver.RELEASETODAY_REMINDER);
                    }
            }
        });

        findViewById(R.id.change_language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            }
        });
    }

}
