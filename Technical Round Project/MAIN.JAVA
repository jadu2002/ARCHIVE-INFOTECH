public class TimerService extends Service {

    private CountDownTimer timer;
    private long elapsedTime = 0; // Elapsed time in milliseconds
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "TimerServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIFICATION_ID, createNotification());
        startTimer();
        return START_STICKY;
    }

    private void startTimer() {
        timer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                elapsedTime += 1000;
                updateNotification();
            }

            @Override
            public void onFinish() {}
        }.start();
    }

    private void updateNotification() {
        Notification notification = createNotification();
        NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, notification);
    }

    private Notification createNotification() {
        String timeString = formatElapsedTime(elapsedTime);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Timer")
                .setContentText(timeString)
                .setSmallIcon(R.drawable.ic_timer)
                .setOngoing(true)
                .addAction(createPauseAction())
                .setPriority(NotificationCompat.PRIORITY_LOW);

        return builder.build();
    }

    private NotificationCompat.Action createPauseAction() {
        Intent pauseIntent = new Intent(this, TimerReceiver.class);
        pauseIntent.setAction("ACTION_PAUSE");
        PendingIntent pausePendingIntent = PendingIntent.getBroadcast(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action(R.drawable.ic_pause, "Pause", pausePendingIntent);
    }

    @Override
    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }

    private String formatElapsedTime(long millis) {
        long hours = (millis / 3600000) % 24;
        long minutes = (millis / 60000) % 60;
        long seconds = (millis / 1000) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Timer Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}
