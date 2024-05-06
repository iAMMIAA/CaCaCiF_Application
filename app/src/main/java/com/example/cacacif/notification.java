package com.example.cacacif;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class notification extends AppCompatActivity {
    private ListView listView;
    //private ArrayAdapter<String> adapter;

    private CustomAdapter adapter;
    private ArrayList<FoodItem> foodList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Hiển thị nút quay lại
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Xử lý sự kiện khi nút quay lại được nhấn
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        listView = findViewById(R.id.listView);

        foodList = new ArrayList<>();
        // Thêm các mục thức ăn vào danh sách
        foodList.add(new FoodItem("Bánh mì", R.drawable.banhmi));
        foodList.add(new FoodItem("Phở", R.drawable.pho));
        foodList.add(new FoodItem("Bún chả", R.drawable.buncha));
        foodList.add(new FoodItem("Bánh xèo", R.drawable.banhxeo));

        adapter = new CustomAdapter(this, android.R.layout.simple_list_item_1, foodList);
        listView.setAdapter(adapter);
    }
    private static class FoodItem {
        String name;
        int imageResId;

        FoodItem(String name, int imageResId) {
            this.name = name;
            this.imageResId = imageResId;
        }
    }
    private static class CustomAdapter extends ArrayAdapter<FoodItem> {
        Context context;
        ArrayList<FoodItem> items;

        CustomAdapter(Context context, int resource, ArrayList<FoodItem> items) {
            super(context, resource, items);
            this.context = context;
            this.items = items;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = super.getView(position, convertView, parent);
            }

            TextView textView = convertView.findViewById(android.R.id.text1);
            FoodItem item = items.get(position);

            textView.setText(item.name);
            Drawable drawable = context.getResources().getDrawable(item.imageResId);
            drawable.setBounds(0, 0, 100, 100); // Đặt kích thước hình ảnh
            textView.setCompoundDrawables(drawable, null, null, null);

            return convertView;
        }
    }
}