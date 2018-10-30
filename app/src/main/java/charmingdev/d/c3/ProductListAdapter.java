package charmingdev.d.c3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by LISA on 10/29/2018.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private Context mCtx;
    private List<ProductListCla> productListClaList;

    public ProductListAdapter(Context mCtx, List<ProductListCla> productListClaList) {
        this.mCtx = mCtx;
        this.productListClaList = productListClaList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recycler_home_recent, null);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        ProductListCla productListCla = productListClaList.get(position);

        //loading the image
        Picasso.with(mCtx)
                .load(productListCla.getImage())
                .into(holder.imageView);

        holder.textViewTitle.setText(productListCla.getName());
    }

    @Override
    public int getItemCount() {
        return productListClaList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle,textViewPrice,textViewId;
        ImageView imageView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.title);
            textViewPrice = itemView.findViewById(R.id.price);
            textViewId = itemView.findViewById(R.id.id);
            imageView = itemView.findViewById(R.id.imageLogo);
        }
    }
}
