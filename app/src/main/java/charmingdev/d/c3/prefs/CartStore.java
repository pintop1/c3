package charmingdev.d.c3.prefs;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by LISA on 10/28/2018.
 */

public class CartStore {
    private static final String TAG = UserInfo.class.getSimpleName();
    private static final String PREF_NAME = "cartItems";
    private static final String KEY_ID = "id";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context ctx;

    public CartStore(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences(PREF_NAME, ctx.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setKeyId(String id){
        editor.putString(KEY_ID, id);
        editor.apply();
    }

    public String getKeyId(){
        return prefs.getString(KEY_ID, "");
    }
}
