package com.example.pricepilot;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

public class ProductViewHolder extends RecyclerView.ViewHolder {
  private ImageView productImageView = itemView.findViewById(R.id.image_product);
  private ImageView marketLogoView = itemView.findViewById(R.id.image_market_logo);
  private TextView marketNameView = itemView.findViewById(R.id.text_market_name);
  private TextView priceTagView = itemView.findViewById(R.id.text_price_tag);
  private ImageView heartIconView = itemView.findViewById(R.id.button_favorite);
  private TextView productNameView = itemView.findViewById(R.id.text_product_name);
  private TextView displayedPrice = itemView.findViewById(R.id.text_displayed_price);

  public ProductViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  public void bind(Product product) {
    marketNameView.setText(product.getMarketName());
    priceTagView.setText(product.getProductPrice());
    productNameView.setText(product.getProductName());
    displayedPrice.setText(product.getProductPrice());

    marketLogoView.setImageDrawable(getStoreLogo(marketLogoView.getContext(), product.getMarketName()));


    Glide.with(productImageView.getContext())
        .load(product.getPicUrl())
        .into(productImageView);
  }

  public Drawable getStoreLogo(Context context, String key) {
    try {
      storeLogoEnum logoEnum = storeLogoEnum.fromKey(key);
      return context.getResources().getDrawable(logoEnum.getDrawableResId(), null);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }


  private enum storeLogoEnum {
    DNS("DNS", R.drawable.dns_logo),
    OZON("Ozon", R.drawable.ozon_logo);
    private final String key;
    private final int drawableResId;

    storeLogoEnum(String key, @DrawableRes int drawableResId) {
      this.key = key;
      this.drawableResId = drawableResId;
    }

    public String getKey() {
      return key;
    }

    public int getDrawableResId() {
      return drawableResId;
    }

    public static storeLogoEnum fromKey(String key) {
      for (storeLogoEnum drawableEnum : values()) {
        if (drawableEnum.getKey().equals(key)) {
          return drawableEnum;
        }
      }
      throw new IllegalArgumentException("No enum constant with key " + key);
    }
  }
}
