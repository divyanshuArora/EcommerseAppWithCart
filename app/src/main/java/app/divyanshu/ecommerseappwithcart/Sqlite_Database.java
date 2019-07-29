package app.divyanshu.ecommerseappwithcart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Sqlite_Database  extends SQLiteOpenHelper {

    private static final String dbName = "shoppers";
    private static final String CART_TABLE = "cart";
    private static final String ID = "id";
    private static final String PRODUCT_NAME = "product_name";
    private static final String PRODUCT_AMOUNT = "product_amount";

    public SQLiteDatabase helper;
    public SQLiteDatabase sqLiteDatabase;

    public Sqlite_Database(Context context  ) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String CREATE_CART_TABLE = "create table IF NOT EXISTS " + CART_TABLE
                + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + PRODUCT_NAME + " TEXT,"
                + PRODUCT_AMOUNT + " TEXT"
                + ")";


        Log.d("TABLE", "onCreate: "+CREATE_CART_TABLE);

        sqLiteDatabase.execSQL(CREATE_CART_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    void addCart(ProductsModel productsModel)
    {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

          ContentValues contentValues = new ContentValues();
          contentValues.put(ID,productsModel.getId());
          contentValues.put(PRODUCT_NAME,productsModel.getProductName());
          contentValues.put(PRODUCT_AMOUNT,productsModel.getProductAmount());

          sqLiteDatabase.insert(CART_TABLE,null,contentValues);
          sqLiteDatabase.close();
    }




    public List<ProductsModel> getCart()
    {

    String[] column = {ID,PRODUCT_NAME,PRODUCT_AMOUNT};

    String sortOrder =  PRODUCT_AMOUNT;

    List<ProductsModel> productsModels = new ArrayList<>();
    SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();


        Cursor cursor = sqLiteDatabase.query(CART_TABLE,column,null,null,null,null,sortOrder);
        if (cursor.moveToFirst())
        {
            do
            {
                ProductsModel productsModel = new ProductsModel();
                productsModel.setId(cursor.getString(cursor.getColumnIndex(ID)));
                productsModel.setProductName(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
                productsModel.setProductAmount(cursor.getString(cursor.getColumnIndex(PRODUCT_AMOUNT)));
                productsModels.add(productsModel);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return  productsModels;
    }




}
