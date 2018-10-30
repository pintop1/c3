package charmingdev.d.c3.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import charmingdev.d.c3.Category;
import charmingdev.d.c3.CategoryAdapter;
import charmingdev.d.c3.MainPage;
import charmingdev.d.c3.ProductListAdapter;
import charmingdev.d.c3.ProductListCla;
import charmingdev.d.c3.Profile;
import charmingdev.d.c3.R;
import charmingdev.d.c3.Utils;
import charmingdev.d.c3.VolleySingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryList extends Fragment {

    View fragView;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    FragmentManager fragmentManager;

    private String TAG = MainPage.class.getSimpleName();
    private static int TIME_OUT = 4000;

    List<ProductListCla> productListClaList;
    String category;

    TextView TITLE;


    public CategoryList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragView = inflater.inflate(R.layout.fragment_category_list, container, false);
        progressBar = fragView.findViewById(R.id.loader);
        fragmentManager = getActivity().getSupportFragmentManager();
        TITLE = fragView.findViewById(R.id.title);
        TITLE.setVisibility(View.GONE);
        recyclerView = fragView.findViewById(R.id.recyclerView);
        Bundle bundle = this.getArguments();
        category = bundle.getString("CAT", "");
        loadProducts();
        return fragView;
    }

    private void loadProducts() {
        progressBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Utils.GET_BY_CAT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "server Response: " + response.toString());
                        if(!TextUtils.isEmpty(response)){
                            progressBar.setVisibility(View.GONE);
                            TITLE.setVisibility(View.VISIBLE);
                            try {
                                //converting the string to json array object
                                JSONArray array = new JSONArray(response);

                                //traversing through all the object
                                for (int i = 0; i < array.length(); i++) {

                                    //getting product object from json array
                                    JSONObject product = array.getJSONObject(i);

                                    //adding the product to product list
                                    productListClaList.add(new ProductListCla(
                                            product.getInt("id"),
                                            product.getString("name"),
                                            product.getString("price"),
                                            product.getString("thumb")
                                    ));
                                }

                                ProductListAdapter adapter = new ProductListAdapter(getActivity(), productListClaList);
                                recyclerView.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "connection error: " + error.getMessage());
                        Toast.makeText(getActivity(), "Connection error: "+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("category", category);

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }

}
