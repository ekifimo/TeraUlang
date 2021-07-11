package pemda.cirebon.teraulang.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pemda.cirebon.teraulang.R;

public class Grafik_Fragment extends Fragment {

    public static Grafik_Fragment newInstance() {
        return new Grafik_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_grafik_fragment, container, false);
    }
}