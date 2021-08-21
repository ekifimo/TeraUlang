package pemda.cirebon.teraulang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import pemda.cirebon.teraulang.Adapter.TabFragment;
import pemda.cirebon.teraulang.Fragment.Data_Fragment;
import pemda.cirebon.teraulang.Fragment.Grafik_Fragment;
import pemda.cirebon.teraulang.Fragment.Grafik_Uttp;

public class ReportTera extends AppCompatActivity {

    TabLayout tabs;
    View viewIndicator;
    ViewPager viewPager;
    ImageView backButton;

    private int indicatorWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_tera);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        tabs = findViewById(R.id.tab);
        viewIndicator = findViewById(R.id.indicator);
        viewPager = findViewById(R.id.ViewPager);
        backButton = findViewById(R.id.backbutton);

        backButton.setOnClickListener(v-> {
            Intent intent = new Intent(this, Dashboard.class);
            startActivity(intent);
        });

        TabFragment adapter = new TabFragment(getSupportFragmentManager());
        adapter.addFragment(Data_Fragment.newInstance(), "Data Tera");
        adapter.addFragment(Grafik_Fragment.newInstance(), "Grafik Retribusi");
        adapter.addFragment(Grafik_Uttp.newInstance(), "Grafik UTTP");
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);

        tabs.post(() -> {
            indicatorWidth = tabs.getWidth() / tabs.getTabCount();

            FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) viewIndicator.getLayoutParams();
            indicatorParams.width = indicatorWidth;
            viewIndicator.setLayoutParams(indicatorParams);
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewIndicator.getLayoutParams();

                float translationOffSet = (positionOffset+position) * indicatorWidth;
                params.leftMargin = (int) translationOffSet;
                viewIndicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}