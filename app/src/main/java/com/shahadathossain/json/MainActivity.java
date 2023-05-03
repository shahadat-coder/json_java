package com.shahadathossain.json;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    GridView gridView;
    ProgressBar progressBar;
    ImageSlider image_slider;


    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    HashMap<String, String> hashMap;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        gridView = findViewById(R.id.gridView);
        image_slider = findViewById(R.id.image_slider);

        //-------------------------------------------------------------------------------

        ArrayList<SlideModel> arrayList1 = new ArrayList<>();
        arrayList1.add(new SlideModel(R.drawable.camera, ScaleTypes.FIT));
        arrayList1.add(new SlideModel(R.drawable.laptop, ScaleTypes.FIT));
        arrayList1.add(new SlideModel(R.drawable.headphn, ScaleTypes.FIT));
        arrayList1.add(new SlideModel(R.drawable.man, ScaleTypes.FIT));
        arrayList1.add(new SlideModel(R.drawable.juta, ScaleTypes.FIT));
        arrayList1.add(new SlideModel(R.drawable.mac, ScaleTypes.FIT));

        image_slider.setImageList(arrayList1);





//_______________________________________________________________________________________________

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://dummyjson.com/products", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            JSONArray jsonArray = response.getJSONArray("products");

                            for (int x = 0; x < jsonArray.length(); x++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(arrayList.size());


                                String title = jsonObject.getString("title");
                                String price = jsonObject.getString("price");
                                String discountPercentage = jsonObject.getString("discountPercentage");
                                String category = jsonObject.getString("category");
                                String rating = jsonObject.getString("rating");
                                String thumbnail = jsonObject.getString("thumbnail");

                                hashMap = new HashMap<>();
                                hashMap.put("title", title);
                                hashMap.put("price", price);
                                hashMap.put("discountPercentage", discountPercentage);
                                hashMap.put("category", category);
                                hashMap.put("rating", rating);
                                hashMap.put("thumbnail", thumbnail);
                                arrayList.add(hashMap);


                            }

                            MyAdapter myAdapter = new MyAdapter();
                            gridView.setAdapter(myAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);


                Toast.makeText(MainActivity.this, "NOT WORKING ", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);



    }


    //====================================================================================

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View myView = layoutInflater.inflate(R.layout.layout_second, null);
            TextView title = myView.findViewById(R.id.title);
            TextView discount = myView.findViewById(R.id.Discount);
            TextView price = myView.findViewById(R.id.Price);
            TextView Cat = myView.findViewById(R.id.Cat);
            TextView Rat = myView.findViewById(R.id.Rat);
            ImageView ImageShow = myView.findViewById(R.id.ImageShow);
            RelativeLayout LayAll= myView.findViewById(R.id.LayAll);

            HashMap<String, String> hashMap = arrayList.get(i);

            String todo = hashMap.get("title");
            String pr = hashMap.get("price");
            String dis = hashMap.get("discountPercentage");
            String cat = hashMap.get("category");
            String rat = hashMap.get("rating");
            String thumbnail = hashMap.get("thumbnail");


            Picasso.get().load(thumbnail)
                    .placeholder(R.drawable.empty)
                    .into(ImageShow);


            title.setText(todo);
            discount.setText(dis + "%");
            price.setText(pr + ".00 TK");
            Cat.setText(cat);
            Rat.setText("*" + rat);
            ImageShow.setTag(thumbnail);

            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            discount.setBackgroundColor(color);

            LayAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent= new Intent(MainActivity.this,Product_Details_Activity.class);
                    startActivity(intent);
                }
            });


            return myView;


        }

    }
}