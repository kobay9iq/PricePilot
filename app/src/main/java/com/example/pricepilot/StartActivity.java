package com.example.pricepilot;

import android.content.pm.PackageManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.button.MaterialButton;

public class StartActivity extends AppCompatActivity {
  public static final String REQUEST_KEY = "requestURL";

  private MaterialButton favoritesButton;
  private EditText searchBar;

  private Handler eTHandler = new Handler();
  private Runnable eTrunnable;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_start);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    setUpView();
    requestNotification();
  }

  private void setUpView() {
    favoritesButton = findViewById(R.id.favorites_start_button);
    searchBar = findViewById(R.id.start_search_bar);
    favoritesButton.setIconResource(R.drawable.filled_heart_icon);

    favoritesButton.setOnClickListener(v -> goToFavoritesActivity());
    searchBar.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (eTrunnable != null) {
          eTHandler.removeCallbacks(eTrunnable);
        }
      }

      @Override
      public void afterTextChanged(Editable s) {
        eTrunnable = new Runnable() {
          @Override
          public void run() {
            if (s != null) {
              goToMainActivity(s.toString());
            }
          }
        };
        eTHandler.postDelayed(eTrunnable, MainActivity.INPUT_FINISH_DELAY);
      }
    });
  }

  private void goToFavoritesActivity() {
    Intent intent = new Intent(this, FavoritesActivity.class);
    startActivity(intent);
  }

  private void goToMainActivity(String request) {
    Intent intent = new Intent(this, MainActivity.class);
    intent.putExtra(REQUEST_KEY, request);
    startActivity(intent);
  }

  private void requestNotification() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
      }
    }
  }

}
