package charmingdev.d.c3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.List;

import charmingdev.d.c3.fragments.CategoryList;

/**
 * Created by LISA on 10/29/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context mCtx;
    private List<Category> categoryList;

    public CategoryAdapter(Context mCtx, List<Category> categoryList) {
        this.mCtx = mCtx;
        this.categoryList = categoryList;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recyclerview_custom_layout, null);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);

        //loading the image
        Picasso.with(mCtx)
                .load(category.getImage())
                .into(holder.imageView);

        holder.textViewTitle.setText(category.getName());

        final String categories = holder.textViewTitle.getText().toString();

        holder.Content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager fragmentManager;
                fragmentManager =  ((MainPage) mCtx).getSupportFragmentManager();
                CategoryList categoryList = new CategoryList();
                Bundle bundle = new Bundle();
                bundle.putString("CAT", categories);
                categoryList.setArguments(bundle);

                fragmentManager.beginTransaction().replace(R.id.container_root,
                        categoryList).addToBackStack("shopSearch").commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        ImageView imageView;
        LinearLayout Content;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            textViewTitle = itemView.findViewById(R.id.nameCate);
            imageView = itemView.findViewById(R.id.imageLogo);
            Content = itemView.findViewById(R.id.content);
        }
    }
}
