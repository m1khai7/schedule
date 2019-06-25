package com.example.misha.myapplication.module.settings;

import android.app.DatePickerDialog;
import android.content.Context;

import com.example.misha.myapplication.common.core.BaseActivity;
import com.example.misha.myapplication.common.core.BaseMainPresenter;
import com.example.misha.myapplication.data.preferences.Preferences;
import com.example.misha.myapplication.entity.Licenses;

import java.util.ArrayList;
import java.util.Calendar;

public class SettingsPresenter extends BaseMainPresenter<SettingsFragmentView> implements SettingsPresenterInterface {

    private Context context;

    public SettingsPresenter(BaseActivity context) {
        this.context = context;
    }

    @Override
    public void init() {
    }

    @Override
    public void onClickDate() {
        getCurrentDate();
    }

    @Override
    public void onCreateDialogSelectTheme() {
        getView().showDialogSelectTheme();
    }

    @Override
    public void onCreateDialogAbout() {
        getView().onCreateDialogAbout().show();
    }

    @Override
    public void onOpenFragmentTransferData() {
        getView().openFragmentTransferData();
    }

    public void getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        final Calendar selectedDate = Calendar.getInstance();
        new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            selectedDate.set(Calendar.YEAR, year);
            selectedDate.set(Calendar.MONTH, month);
            selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Preferences.getInstance().setSemesterStart(String.valueOf(selectedDate.getTimeInMillis()));

            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(Long.parseLong(Preferences.getInstance().getSemestrStart()));
            mCalendar.setFirstDayOfWeek(Calendar.MONDAY);
            mCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            Preferences.getInstance().setSemesterStart(String.valueOf(mCalendar.getTimeInMillis()));

        },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onCreateDialogLicenses() {
        ArrayList<Licenses> licensesList = new ArrayList<>();
        licensesList.add(new Licenses("AppCompat and AndroidX by Google", "Copyright (C) 2011 The Android Open Source Project\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License."));

        // RXJAVA2
        licensesList.add(new Licenses("RxJava2", "Copyright (c) 2016-present, RxJava Contributors.\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License."));

        // OKHTTP
        licensesList.add(new Licenses("OkHttp", "Copyright 2016 Square, Inc.\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License."));

        // RETROFIT2
        licensesList.add(new Licenses("Retrofit2", "Copyright 2013 Square, Inc. \n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License. \n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an\"AS IS\"BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License."));
        // GSON
        licensesList.add(new Licenses("Gson", "Copyright 2008 Google Inc. \n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. \n" +
                "See the License for the specific language governing permissions and \n" +
                "limitations under the License."));

        // SQLITE
        licensesList.add(new Licenses("Sqlite", "Copyright (C) 2017 requery.io \n" +
                "Copyright (C) 2005-2012 The Android Open Source Project \n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License."));
        // JETBRAINS JAVA-ANNOTATIONS
        licensesList.add(new Licenses("JetBrains Java-Annotations", "Copyright 2000-2016 JetBrains s.r.o.\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License.\n"));
        // Android MVVM Helper
        licensesList.add(new Licenses("Android MVVM Helper", "Copyright (C) 2017 stfalcon.com\n" +
                "\n" +
                "Licensed under the Apache License, Version 2.0 (the \"License\");\n" +
                "you may not use this file except in compliance with the License.\n" +
                "You may obtain a copy of the License at\n" +
                "\n" +
                "http://www.apache.org/licenses/LICENSE-2.0\n" +
                "\n" +
                "Unless required by applicable law or agreed to in writing, software\n" +
                "distributed under the License is distributed on an \"AS IS\" BASIS,\n" +
                "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n" +
                "See the License for the specific language governing permissions and\n" +
                "limitations under the License.\n"));
        getView().showLicensesDialog(licensesList);
    }
}

