package com.example.pricepilot;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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


}
