package com.gloomyer.myapplication;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    ViewPager mViewPager;
    TabLayout mTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mTableLayout = (TabLayout) findViewById(R.id.mTableLayout);
        final View[] recyclers = new View[2];

        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public CharSequence getPageTitle(int position) {
                return position == 0 ? "标题111" : "标题222";
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                if (recyclers[position] == null) {
                    RecyclerView recyclerView
                            = new RecyclerView(MainActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(new MyAdapter(position));
                    recyclerView.addItemDecoration(new CutofRuleItemDecoration(MainActivity.this));
                    recyclers[position] = recyclerView;
                }


                ViewGroup.LayoutParams layoutParems
                        = new ViewPager.LayoutParams();
                layoutParems.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParems.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                container.addView(recyclers[position], layoutParems);
                return recyclers[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        mTableLayout.setupWithViewPager(mViewPager);
    }

    private class Holder extends RecyclerView.ViewHolder {

        private TextView tv;

        public Holder(View itemView) {
            super(itemView);
            tv = (TextView) itemView;
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<Holder> {

        final String type;

        public MyAdapter(int position) {
            type = position == 0 ? "one" : "tow";
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(MainActivity.this).inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.tv.setText("type:" + type + ",pos:" + position);
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
