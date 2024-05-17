package com.example.pricepilot;

import android.provider.BaseColumns;

public class ProductDbContract {
    public static final String DATABASE_NAME  = "ProductsDB.db";

    public class ProductsDb {
      public static final String TABLE_NAME = "Products";

      public static final String COLOMN_NAME_ID = BaseColumns._ID;
      public static final String COLOMN_NAME_TITLE = "ProductTitle";
      public static final String COLOMN_NAME_PRICE = "ProductPrice";
      public static final String COLOMN_NAME_STORE_URL = "StoreUrl";
      public static final String COLOMN_NAME_PRODUCT_URL = "ProductUrl";
      public static final String COLOMN_NAME_STORE = "Store";
      public static final String COLOMN_NAME_PRODUCT_PIC_URL = "ProductPic";
      public static final String COLOMN_NAME_SIMILAR_PRODUCTS = "SimilarProducts";
      public static final String COLOMN_NAME_IS_LIKED = "IsLiked";
    }
}
