package myapp.schedule.misha.myapplication.common.core;

import androidx.annotation.CallSuper;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;

import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.ScheduleApp;
import retrofit2.HttpException;

public abstract class BaseMainPresenter<View extends BaseView> extends BasePresenter<View, Root> {

    @CallSuper
    @Override
    public void onStart() {
        getRoot().hideErrorView();
    }

    protected String handleApiError(Throwable throwable) {
        if (!(throwable instanceof HttpException)) {
            return ScheduleApp.getStr(R.string.unknown_error);
        }
        HttpException error = (HttpException) throwable;
        try {
            int errorCode = error.response().code();
            if (errorCode == HttpsURLConnection.HTTP_INTERNAL_ERROR) {
                return ScheduleApp.getStr(R.string.error_api_unexpected);
            }
            String errorBody = error.response().errorBody().string();
            return errorBody;

        } catch (IOException e) {
            return ScheduleApp.getStr(R.string.error_api_unexpected);
        }
    }

    protected void processGlobalError(Throwable throwable) {
        BaseView baseMainView = getView();
        if (!ScheduleApp.isConnectionAvailable()) {
            baseMainView.showGlobalError(R.string.network_error);
        } else {
            String message = handleApiError(throwable);
            baseMainView.showGlobalError(message);
        }
    }

    @CallSuper
    @Override
    public void onStop() {
        getRoot().hideProgressBar();
    }

    protected void processSimpleError(Throwable throwable) {
        BaseView baseMainView = getView();
        if (!ScheduleApp.isConnectionAvailable()) {
            baseMainView.showError(R.string.network_error);
        } else {
            String message = handleApiError(throwable);
            baseMainView.showError(message);
        }
    }
}
