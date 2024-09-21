public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = findViewById(R.id.start_button);
        Button pauseButton = findViewById(R.id.pause_button);

        startButton.setOnClickListener(v -> {
            startService(new Intent(MainActivity.this, TimerService.class));
        });

        pauseButton.setOnClickListener(v -> {
            stopService(new Intent(MainActivity.this, TimerService.class));
        });
    }
}
