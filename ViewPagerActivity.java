import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.healthcare.Adapter.ImageSliderAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ImageSliderAdapter adapter;
    private Handler handler;
    private Runnable runnable;
    private static final long SLIDER_DELAY_MS = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);  // Make sure the layout file name is correct

        viewPager = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.doctor);
        images.add(R.drawable.doctor);
        images.add(R.drawable.doctor);

        adapter = new ImageSliderAdapter(images, this);
        viewPager.setAdapter(adapter);
//        tabLayout.setSelectedTabIndicator(R.drawable.tab_indicator);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {}).attach();

        // Customize TabLayout programmatically
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.app_color_blue));

        autoSlideImages();
    }

    private void autoSlideImages() {
        handler = new Handler(Looper.getMainLooper());
        final int NUM_PAGES = adapter.getItemCount();

        runnable = new Runnable() {
            int currentPage = 0;

            @Override
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, SLIDER_DELAY_MS);
            }
        };

        handler.postDelayed(runnable, SLIDER_DELAY_MS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
