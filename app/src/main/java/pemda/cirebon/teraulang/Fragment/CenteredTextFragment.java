package pemda.cirebon.teraulang.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import io.paperdb.Paper;
import pemda.cirebon.teraulang.Login;
import pemda.cirebon.teraulang.Monitoring;
import pemda.cirebon.teraulang.R;
import pemda.cirebon.teraulang.RekamData;
import pemda.cirebon.teraulang.ReportTera;

public class CenteredTextFragment extends Fragment {

    private static final String EXTRA_TEXT = "text";

    LinearLayout inputTera, reportTera, monitoringTera;
    RelativeLayout logout;

    public CenteredTextFragment() {

    }

    public static CenteredTextFragment createFor(String text) {
        CenteredTextFragment fragment = new CenteredTextFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        inputTera = view.findViewById(R.id.relativeLayout);
        reportTera = view.findViewById(R.id.relativeLayout2);
        monitoringTera = view.findViewById(R.id.relativeLayout3);
        logout = view.findViewById(R.id.relativeLayout4);

        inputTera.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), RekamData.class);
            startActivity(intent);
        });
        reportTera.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ReportTera.class);
            startActivity(intent);
        });
        monitoringTera.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Monitoring.class);
            startActivity(intent);
        });
        logout.setOnClickListener(v -> {
            Paper.book().destroy();
            Intent intent = new Intent(getContext(), Login.class);
            startActivity(intent);
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text, container, false);
    }


}