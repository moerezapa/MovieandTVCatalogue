package zap.dev.movieandtvcatalogue.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import zap.dev.movieandtvcatalogue.MainActivity;
import zap.dev.movieandtvcatalogue.R;
import zap.dev.movieandtvcatalogue.model.Movie;
import zap.dev.movieandtvcatalogue.model.MovieResponse;
import zap.dev.movieandtvcatalogue.utilities.API;
import zap.dev.movieandtvcatalogue.utilities.RetrofitClient;

import static android.support.constraint.Constraints.TAG;


public class ReminderReceiver extends BroadcastReceiver {

    public static final String MESSAGE = "Yuk cek film apa aja yang udah rilis!";
    public static final String REMINDER_TYPE = "type";
    public static final String DAILY_REMINDER = "dailyreminder";
    public static final String RELEASETODAY_REMINDER = "releasetodayreminder";
    private final int ID_DAILY = 102;
    private final int ID_RELEASETODAY = 103;

    private ArrayList<Movie> listMovie = new ArrayList<>();
    private int notifId;

    @Override
    public void onReceive(final Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String type = intent.getStringExtra(REMINDER_TYPE);
        String message = intent.getStringExtra(MESSAGE);
        String titleApp = context.getResources().getString(R.string.app_name);
        if (type.equalsIgnoreCase(DAILY_REMINDER)) {
            notifId = ID_DAILY;
            showAlarmNotification(context, titleApp, message, notifId);
        }
            else {
                Date cDate = new Date();
                String date = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
                API api = RetrofitClient.getClient().create(API.class);
                api.getUpcomingMovie(MainActivity.myAPI, date, date)
                        .enqueue(new Callback<MovieResponse>() {
                            @Override
                            public void onResponse(Call<MovieResponse> call, retrofit2.Response<MovieResponse> response) {
                                if (response.isSuccessful()) {
                                    listMovie = response.body().getResults();
                                    Log.e(TAG, "Hasil List TV: " + listMovie);
                                    int index = new Random().nextInt(listMovie.size());
                                    notifId = ID_RELEASETODAY;
                                    String titleMovie = listMovie.get(index).getTitle();
                                    String overviewMovie = listMovie.get(index).getOverview();
                                    showAlarmNotification(context, titleMovie, overviewMovie, notifId);
                                }

                                Log.e(TAG, "Failed movie because: " + response.raw());
                            }

                            @Override
                            public void onFailure(Call<MovieResponse> call, Throwable t) {

                            }
                        });
            }
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_MovieAndTVShow";
        String CHANNEL_NAME = "Movie and TVShow channel";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null)
                notificationManagerCompat.createNotificationChannel(channel);

        }

        Notification notification = builder.build();
        if (notificationManagerCompat != null)
            notificationManagerCompat.notify(notifId, notification);

    }

    public void setReminder(Context context, String type, String time, String message) {
        String TIME_FORMAT = "HH:mm";
        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra(MESSAGE, message);
        intent.putExtra(REMINDER_TYPE, type);

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        int idType;
        if (type.equalsIgnoreCase(DAILY_REMINDER))
            idType = ID_DAILY;
            else
                idType = ID_RELEASETODAY;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idType, intent, 0);

        if (alarmManager != null)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancelReminder(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        int idType;
        if (type.equalsIgnoreCase(DAILY_REMINDER))
            idType = ID_DAILY;
            else
                idType = ID_RELEASETODAY;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, idType, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null)
            alarmManager.cancel(pendingIntent);
    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        }
            catch (ParseException e) {
                return true;
            }
    }
}
