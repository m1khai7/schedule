package myapp.schedule.misha.myapplication.common.core;


import io.reactivex.disposables.CompositeDisposable;
import myapp.schedule.misha.myapplication.common.rx.AppSchedulerProvider;
import myapp.schedule.misha.myapplication.common.rx.SchedulerProvider;
import myapp.schedule.misha.myapplication.data.Repository;
import myapp.schedule.misha.myapplication.data.RepositoryManager;

public abstract class BasePresenter<View, Root> {
    private View view;

    private Root root;

    private SchedulerProvider mSchedulerProvider = new AppSchedulerProvider();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    public RepositoryManager getRepositoryManager() {
        return Repository.getInstance();
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public abstract void init();

    public abstract void onStart();

    public boolean isViewAttached() {
        return view != null;
    }

    public abstract void onStop();

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root router) {
        this.root = router;
    }
}
