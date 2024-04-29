package com.example.pricepilot;

import android.graphics.drawable.Drawable;
import java.net.URL;

public class Product {
  private Drawable marketLogo;
  private Drawable productLogo;
  private String marketName;
  private String productName;
  private String price;
  private boolean isFavorite;
  private URL productURL;
  private URL similarProducts;

  public Product(Drawable marketLogo, Drawable productLogo, String marketName, String productName,
      String price, boolean isFavorite) {
    this.marketLogo = marketLogo;
    this.productLogo = productLogo;
    this.marketName = marketName;
    this.productName = productName;
    this.price = price;
    this.isFavorite = isFavorite;
  }

  public Drawable getMarketLogo() {
    return marketLogo;
  }

  public Drawable getProductLogo() {
    return productLogo;
  }

  public String getMarketName() {
    return marketName;
  }

  public String getProductName() {
    return productName;
  }

  public String getPrice() {
    return price;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  public void setFavorite(boolean favorite) {
    isFavorite = favorite;
  }
}

