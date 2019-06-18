package com.example.misha.myapplication.data;

import com.example.misha.myapplication.data.database.DatabaseInterface;
import com.example.misha.myapplication.data.network.APIService;
import com.example.misha.myapplication.data.preferences.PreferencesInterface;

public interface RepositoryManager extends DatabaseInterface, APIService, PreferencesInterface {

}
