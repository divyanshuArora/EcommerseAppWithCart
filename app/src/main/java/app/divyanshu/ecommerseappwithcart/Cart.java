package app.divyanshu.ecommerseappwithcart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.nikartm.support.ImageBadgeView;

public class Cart extends AppCompatActivity {
    List<ProductsModel> productsModelList = new ArrayList<>();
    ProductGridAdapter productGridAdapter;
    RecyclerView recyclerViewCart;
    Sqlite_Database sqlite_database;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerViewCart = findViewById(R.id.recyclerCart);

        loadCart();
    }

     void loadCart()
    {
        sqlite_database = new Sqlite_Database(Cart.this);
        productsModelList =  sqlite_database.getCart();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewCart.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(Cart.this,DividerItemDecoration.VERTICAL);
        recyclerViewCart.addItemDecoration(dividerItemDecoration);

        productGridAdapter = new ProductGridAdapter(Cart.this,productsModelList,1);
        recyclerViewCart.setAdapter(productGridAdapter);



    }



}
