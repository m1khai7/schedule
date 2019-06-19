package com.example.misha.myapplication.module.settings.transfer;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.misha.myapplication.R;
import com.example.misha.myapplication.common.core.BaseMainFragment;
import com.example.misha.myapplication.common.core.BasePresenter;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.module.schedule.exploreDays.ScheduleFragment;
import com.example.misha.myapplication.util.PermissionUtils;

import org.jetbrains.annotations.NotNull;

import static android.app.Activity.RESULT_OK;
import static com.example.misha.myapplication.data.preferences.Preferences.DARK_THEME;
import static com.example.misha.myapplication.data.preferences.Preferences.LIGHT_THEME;

public class TransferFragment extends BaseMainFragment implements TransferFragmentView, View.OnClickListener {

    private static final int READ_EXTERNAL = 2;
    private TransferPresenter presenter;

    @Override
    public void onResume() {
        super.onResume();
        getContext().setCurrentTitle(R.string.title_transfer_data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TransferPresenter(getContext());
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer_data, container, false);
        RelativeLayout layoutImport = view.findViewById(R.id.rel_import);
        RelativeLayout layoutExport = view.findViewById(R.id.rel_export);
        ImageView imageImport = view.findViewById(R.id.image_date_start_semestr);
        ImageView imageExport = view.findViewById(R.id.image_export);
        if (Preferences.getInstance().getSelectedTheme().equals(DARK_THEME)) {
            imageImport.setImageResource(R.drawable.ic_unarchive_white);
            imageExport.setImageResource(R.drawable.ic_archive_white);
        }
        if (Preferences.getInstance().getSelectedTheme().equals(LIGHT_THEME)) {
            imageImport.setImageResource(R.drawable.ic_unarchive_black);
            imageExport.setImageResource(R.drawable.ic_archive_black);
        }

        layoutImport.setOnClickListener(this);
        layoutExport.setOnClickListener(this);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.init();
    }

    public void openFragmentSchedule() {
        Fragment newFragment = new ScheduleFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void openDirectory() {
        if (!PermissionUtils.isGranted(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            PermissionUtils.requestPermission(getContext(), READ_EXTERNAL,
                    Manifest.permission.READ_EXTERNAL_STORAGE, "");
        } else {
            open();
        }
    }

    private void open() {
        Intent intent = new Intent();
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("text/*");
        startActivityForResult(intent, 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                String filePath = null;
                Uri _uri = data.getData();
                if (_uri != null && "content".equals(_uri.getScheme())) {
                    Cursor cursor = getContext().getContentResolver().query(_uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                    cursor.moveToFirst();
                    filePath = cursor.getString(0);
                    cursor.close();
                } else {
                    filePath = _uri.getPath();
                }
                System.out.println(filePath);
                presenter.import_data(filePath);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == READ_EXTERNAL) {
            openDirectory();
        }
    }


    @NonNull
    @Override
    protected BasePresenter getSchedulePagePresenter() {
        return presenter;
    }

//Todo /external_files/Android/data/com.example.misha.myapplication/files/export.txt
//Todo /storage/emulated/0/Android/data/com.example.misha.myapplication/files/export.txt

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rel_import) {
            presenter.onClickImport();
        }
        if (v.getId() == R.id.rel_export) {
            presenter.onClickExport();
        }
    }
}
