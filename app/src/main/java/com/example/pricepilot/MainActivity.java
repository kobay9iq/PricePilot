package com.example.pricepilot;

import android.os.AsyncTask;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private static final int INPUT_FINISH_DELAY = 3000;
  private Handler inputFinishHandler = new Handler();
  private Runnable inputFinishChecker;
  private EditText searchBarView = null;
  private RecyclerView productRecycler = null;
  private MaterialButton favoritesButton = null;
  private ProductAdapter adapter = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main_activity);
    setUpViews();

    getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    favoritesButton.setIconResource(R.drawable.filled_heart_icon);

    searchBarView.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
      }

      @Override
      public void afterTextChanged(Editable s) {
        inputFinishHandler.removeCallbacks(inputFinishChecker);

        inputFinishChecker = () -> {
          onInputFinished(s.toString());
        };

        inputFinishHandler.postDelayed(inputFinishChecker, INPUT_FINISH_DELAY);
      }
    });
  }

  private void setUpViews() {
    searchBarView = findViewById(R.id.search_bar);
    productRecycler = findViewById(R.id.products_recycler_view);
    favoritesButton = findViewById(R.id.favorites_main_button);

    adapter = new ProductAdapter(Collections.emptyList(),this);
    productRecycler.setAdapter(adapter);
  }

  private void onInputFinished(String request) {
    DownloadProductsTask task = new DownloadProductsTask();
    task.execute(request);
  }

  private void makeToastWithError(String storeName) {
    runOnUiThread(new Runnable() {
      @Override
      public void run() {
        Toast.makeText(MainActivity.this,
            String.format(getString(R.string.store_parse_error), storeName),
            Toast.LENGTH_SHORT).show();
      }
    });
  }


  public class DownloadProductsTask extends AsyncTask<String, Integer, List<Product>> {

    @Override
    protected List<Product> doInBackground(String... strings) {
      List<Product> products = Collections.emptyList();

      DNSParse dnsParse = new DNSParse(MainActivity.this);

      try{
        Product dnsResult = dnsParse.get_Product(strings[0]);
        products.add(dnsResult);
      } catch (RuntimeException e) {
        makeToastWithError(dnsParse.SHOP_NAME);
      }

      return products;
    }

    @Override
    protected void onPostExecute(List<Product> products) {
      super.onPostExecute(products);
      adapter.changeData(products);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
    }
  }



}