public class TimerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("ACTION_PAUSE".equals(intent.getAction())) {
            context.stopService(new Intent(context, TimerService.class));
        }
    }
}
