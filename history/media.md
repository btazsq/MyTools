## MediaPlay的主活动代码
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity**********";

    MediaService mediaService = null;

    public View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            binder.changeCD(binder.getCD()>0x1000?0:R.raw.kksk);
        }
    };

    MediaService.MyBinder binder = null;

    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MediaService.MyBinder)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //测试
        Button button = findViewById(R.id.change);
        button.setOnClickListener(clickListener);
        bindService(new Intent(this,MediaService.class),connection,BIND_AUTO_CREATE);
        mediaService = new MediaService(R.raw.kksk);
        startService(new Intent(MainActivity.this,MediaService.class));
    }
}