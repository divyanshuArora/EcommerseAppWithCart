package app.divyanshu.ecommerseappwithcart;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;

public class MainActivity extends AppCompatActivity {

    List<ProductsModel> productsModelList = new ArrayList<>();
    ProductGridAdapter productGridAdapter;
    RecyclerView recyclerView;
    ImageBadgeView cartIcon;
    Sqlite_Database sqlite_database;
    EditText searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        recyclerView =findViewById(R.id.recycler);
        cartIcon = findViewById(R.id.cartIcon);

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cart = new Intent(MainActivity.this,Cart.class);
                startActivity(cart);
            }
        });




        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                filter(editable.toString());


            }
        });




        loadCount();
        loadData();

    }

    private void filter(String searchText)
    {

        List<ProductsModel> temp = new ArrayList<>();
        for (ProductsModel pro : productsModelList)
        {
            if (pro.getProductName().toLowerCase().trim().contains(searchText.toLowerCase().trim()))
            {
                temp.add(pro);
            }
            productGridAdapter.updateList(temp);
        }


    }

    public  void loadCount()
    {

        sqlite_database = new Sqlite_Database(MainActivity.this);
        SQLiteDatabase sqLiteDatabase  =sqlite_database.getReadableDatabase();

        String countCart ="select count(*) from cart";
        Log.d("Main Activity", "loadCount: "+countCart);
        Cursor cursor = sqLiteDatabase.rawQuery(countCart,null);

        int count = 0;

        if (cursor != null && cursor.moveToFirst())
        {
            do {
                cursor.moveToFirst();
                count = cursor.getInt(0);
               }
            while (cursor.moveToNext());

                cursor.close();
            }
            sqlite_database.close();
            Log.d("Main", "loadCountInt: "+count);

        cartIcon.setBadgeValue(count)
                .setBadgeOvalAfterFirst(true)
                .setBadgeTextSize(14)
                .setMaxBadgeValue(99)
                .setBadgePosition(BadgePosition.BOTTOM_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);


    }

    private void loadData()
    {

        productsModelList.add(new ProductsModel(R.drawable.bottle,"Bottle","500"));
        productsModelList.add(new ProductsModel(R.drawable.clothes,"Clothes","400"));
        productsModelList.add(new ProductsModel(R.drawable.cube,"Cube","150"));
        productsModelList.add(new ProductsModel(R.drawable.earphones,"Earphones","1200"));
        productsModelList.add(new ProductsModel(R.drawable.shoes,"Shoes","2500"));
        productsModelList.add(new ProductsModel(R.drawable.watch,"Watch","1500"));


        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(MainActivity.this,DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        productGridAdapter = new ProductGridAdapter(MainActivity.this,productsModelList,0);
        recyclerView.setAdapter(productGridAdapter);



    }

    @Override
    protected void onResume() {
        super.onResume();
       loadCount();
    }
}
