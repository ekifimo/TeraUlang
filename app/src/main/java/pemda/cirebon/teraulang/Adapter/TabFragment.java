package pemda.cirebon.teraulang.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TabFragment extends FragmentPagerAdapter {

    private final List<Fragment> fragmentsList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    public TabFragment (FragmentManager fm) {
        super(fm);
    }

    @Override
    public @NotNull Fragment getItem(int i) {
        return fragmentsList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentsList.add(fragment);
        fragmentTitleList.add(title);
    }
}
