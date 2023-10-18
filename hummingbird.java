import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

public class SoundboardApp extends AppCompatActivity {

    private static final String SOUNDBOARD_FOLDER = "SoundboardAppSounds";
    private MediaPlayer mediaPlayer;
    private String[] soundFileNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the sound folder exists, if not, create it
        File soundFolder = new File(Environment.getExternalStorageDirectory(), SOUNDBOARD_FOLDER);
        if (!soundFolder.exists()) {
            soundFolder.mkdirs();
        }

        // Create an array to store the sound file names
        soundFileNames = soundFolder.list();

        if (soundFileNames == null) {
            soundFileNames = new String[0];
        }

        // Display the list of sound file names in a ListView
        ListView soundListView = findViewById(R.id.soundListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, soundFileNames);
        soundListView.setAdapter(adapter);

        soundListView.setOnItemClickListener((parent, view, position, id) -> {
            // Play the selected sound
            String selectedSound = soundFileNames[position];
            playSound(selectedSound);
        });
    }

    private void playSound(String soundFileName) {
        mediaPlayer = new MediaPlayer();

        try {
            File soundFile = new File(Environment.getExternalStorageDirectory() + "/" + SOUNDBOARD_FOLDER, soundFileName);
            mediaPlayer.setDataSource(soundFile.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error playing sound", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
