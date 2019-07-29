package app.divyanshu.ecommerseappwithcart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProductGridAdapter extends RecyclerView.Adapter<ProductGridAdapter.ItemViewHolder> {

    Sqlite_Database sqlite_database;
    Context context;
    List<ProductsModel> productsModelList = new ArrayList<>();
    int type;

    public ProductGridAdapter(Context context, List<ProductsModel> productsModelList,int i)
    {
        this.context = context;
        this.productsModelList = productsModelList;
        this.type = i;
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        return type;}

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View view;
        switch (i) {
            case 0:


                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item, null, false);
                return new ItemViewHolder(view);

            default:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item,null,false);
                return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder itemViewHolder, final int i) {

        if (type==0) {

            Glide.with(context).load(productsModelList.get(i).getImage()).placeholder(R.drawable.no_img_found).error(R.drawable.no_img_found).into(itemViewHolder.productImage);
            itemViewHolder.productName.setText(productsModelList.get(i).getProductName());
            itemViewHolder.productAmt.setText("₹ " + productsModelList.get(i).getProductAmount() + "/-");
            itemViewHolder.addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String productName = productsModelList.get(i).getProductName();
                    String productAmt = productsModelList.get(i).getProductAmount();
                    try {
                        sqlite_database = new Sqlite_Database(context);
                        sqlite_database.addCart(new ProductsModel(productName, productAmt));
                        Toast.makeText(context, productsModelList.get(i).getProductName() + " Added To Cart", Toast.LENGTH_SHORT).show();
                        ((MainActivity) context).loadCount();

                    } catch (Exception e) {
                        Log.d("Adapter", "add Item Error: " + e);
                    }
                }
            });

        }
        else {

            itemViewHolder.cart_productName.setText(productsModelList.get(i).getProductName());
            itemViewHolder.cart_productAmt.setText("₹ " + productsModelList.get(i).getProductAmount() + "/-");
            itemViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {


                    sqlite_database = new Sqlite_Database(context);
                    SQLiteDatabase sqLiteDatabase  =sqlite_database.getWritableDatabase();

                    String deleteRow = "DELETE FROM cart Where id="+productsModelList.get(i).getId();
                    Log.d("adapter", "onDelete: "+deleteRow);

                    sqLiteDatabase.execSQL(deleteRow);

                    ((Cart)context).loadCart();


                }
            });
        }
        }
    @Override
    public int getItemCount() {
        return productsModelList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        Button addToCart,delete;
        TextView productName,productAmt;
        TextView cart_productName, cart_productAmt;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
                addToCart = itemView.findViewById(R.id.addToCart);
                productName = itemView.findViewById(R.id.productName);
                productAmt = itemView.findViewById(R.id.productAmt);


                    cart_productName = itemView.findViewById(R.id.cartProductName);
                    cart_productAmt = itemView.findViewById(R.id.cartProductAmt);
                    delete = itemView.findViewById(R.id.delete);
            }
        }
    }

