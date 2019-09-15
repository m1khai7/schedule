package myapp.schedule.misha.myapplication.common.core;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import myapp.schedule.misha.myapplication.R;
import myapp.schedule.misha.myapplication.ScheduleApp;
import myapp.schedule.misha.myapplication.common.ErrorView;
import myapp.schedule.misha.myapplication.common.core.snack.SnackBehavior;
import myapp.schedule.misha.myapplication.common.core.snack.SnackBehaviorInterface;
import myapp.schedule.misha.myapplication.data.preferences.Preferences;

import static myapp.schedule.misha.myapplication.data.preferences.Preferences.DARK_THEME;
import static myapp.schedule.misha.myapplication.data.preferences.Preferences.LIGHT_THEME;


public abstract class BaseActivity extends AppCompatActivity implements Root {

    protected ProgressDialog progressDialog;

    protected CharSequence currentTitle;

    protected Toolbar toolbar;

    protected ActionBar actionBar;

    protected AppBarLayout appBarLayout;

    protected CollapsingToolbarLayout collapsingToolbar;

    private CompositeDisposable compositeDisposable;

    private SnackBehaviorInterface snack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        compositeDisposable = new CompositeDisposable();
        setTheme(R.style.DarkTheme);
        String nameTheme = Preferences.getInstance().getSelectedTheme();

        if (nameTheme.equals(DARK_THEME)) {
            setTheme(R.style.DarkTheme);
        }
        if (nameTheme.equals(LIGHT_THEME)) {
            setTheme(R.style.LightTheme);
        }
        setContentView(getLayoutId());
        snack = new SnackBehavior(findViewById(R.id.root_view));
        initToolbar();
    }


    private void initProgressDialog() {
        progressDialog.setMessage(null);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.progress_dialog);
    }

    @LayoutRes
    protected abstract int getLayoutId();

    protected void initToolbar() {
        appBarLayout = findViewById(R.id.app_bar);
        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        toolbar = findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        setupActionBar();
        toolbar.setNavigationOnClickListener(createDrawerClick());
    }
    /**
     * Disable collapsing.
     */
    public void disableCollapsing() {
        appBarLayout.setActivated(false);
        collapsingToolbar.setActivated(false);
        collapsingToolbar.setTitleEnabled(false);
        collapsingToolbar.setVisibility(View.GONE);
        AppBarLayout.LayoutParams params
                = (AppBarLayout.LayoutParams) collapsingToolbar.getLayoutParams();
        params.setScrollFlags(0);
        collapsingToolbar.setLayoutParams(params);
        CoordinatorLayout.LayoutParams lp
                = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        lp.height = getResources().getDimensionPixelSize(R.dimen.toolbar_height);
        toolbar.setTitle(currentTitle);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onSaveInstanceState(Bundle savedInstance) {
        //do nothing
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        sendPermissionToFragment(requestCode, permissions, grantResults);
    }

    /**
     * Send permission to fragments.
     *
     * @param requestCode  request code
     * @param permissions  permissions
     * @param grantResults grant result
     */
    private void sendPermissionToFragment(int requestCode, String[] permissions,
                                          int[] grantResults) {
        List<Fragment> activeFragments = getFragments(getSupportFragmentManager());
        for (Fragment fragment : activeFragments) {
            if (fragment == null) continue;
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            sendToChildes(fragment, requestCode, permissions, grantResults);
        }
    }

    /**
     * Send permission to all child fragments.
     *
     * @param rootFragment root fragment
     * @param requestCode  request code
     * @param permissions  permissions
     * @param grantResults grant results
     */
    private void sendToChildes(Fragment rootFragment, int requestCode,
                               String[] permissions, int[] grantResults) {
        List<Fragment> activeFragments = getFragments(rootFragment.getChildFragmentManager());
        if (activeFragments == null) return;
        for (Fragment fragment : activeFragments) {
            if (fragment == null) continue;
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            sendToChildes(fragment, requestCode, permissions, grantResults);
        }
    }

    /**
     * Enable collapsing.
     *
     * @param show show flag
     */
    public void enableCollapsing(boolean show) {
        collapsingToolbar.setVisibility(View.VISIBLE);
        appBarLayout.setActivated(true);
        collapsingToolbar.setActivated(true);
        collapsingToolbar.setTitleEnabled(true);
        AppBarLayout.LayoutParams params
                = (AppBarLayout.LayoutParams) collapsingToolbar.getLayoutParams();
        params.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        collapsingToolbar.setLayoutParams(params);
        CoordinatorLayout.LayoutParams coordinatorParams
                = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
        coordinatorParams.height = ScheduleApp.getDimen(R.dimen.toolbar_height);
        appBarLayout.setExpanded(show, false);
        toolbar.setTitle(currentTitle);
    }

    /**
     * Setup action bar.
     */
    protected void setupActionBar() {
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

    public void setCurrentTitle(String title) {
        collapsingToolbar.setTitle(title);
        toolbar.setTitle(title);
        actionBar.setTitle(title);
        currentTitle = title;
    }

    public void setCurrentTitle(@StringRes int title) {
        setCurrentTitle(ScheduleApp.getStr(title));
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(fragment.getClass().getName());
        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName())
                .commitAllowingStateLoss();
    }

    public List<Fragment> getFragments(FragmentManager fragmentManager) {
        List<Fragment> activeFragments = new ArrayList<>();
        try {
            Method method = fragmentManager.getClass()
                    .getDeclaredMethod("getActiveFragments");
            method.setAccessible(true);
            activeFragments = (List<Fragment>) method.invoke(fragmentManager);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return activeFragments;
    }

    public void popBackStackInclusive(String name) {
        getSupportFragmentManager().popBackStack(name,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void popBackStackInclusive(Class className) {
        popBackStackInclusive(className.getName());
    }

    public void popBackStack(String name) {
        getSupportFragmentManager().popBackStack(name, 0);
    }

    public void popBackStack() {
        getSupportFragmentManager().popBackStack();
    }

    public void popBackStack(Class className) {
        popBackStack(className.getName());
    }


    protected View.OnClickListener createDrawerClick() {
        return view -> onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (!handleBackPressed(getSupportFragmentManager())) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                super.onBackPressed();
            } else {
                finish();
            }
        }
    }

    public boolean handleBackPressed(FragmentManager manager) {
        if (manager.getFragments() == null) return false;
        for (Fragment frag : manager.getFragments()) {
            if (frag == null) continue;
            if (frag.isVisible() && frag instanceof BaseMainFragment) {
                if (((BaseMainFragment) frag).onBackPressed()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void clearBackStack() {
        if (getSupportFragmentManager() != null) {
            if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                String name = getSupportFragmentManager().getBackStackEntryAt(1).getName();
                getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        }
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbarVisibility(boolean toolbarEnabled) {
        findViewById(R.id.app_bar).setVisibility(toolbarEnabled ? View.VISIBLE : View.GONE);
    }

    private void sendResultToTarget(Class target, Fragment root, int request,
                                    int result, Intent data) {
        if (root != null) {
            //   if (!root.isAdded()) return;
        }
        List<Fragment> activeFragments = getFragments(root == null
                ? getSupportFragmentManager()
                : root.getChildFragmentManager());
        if (activeFragments == null) return;
        for (Fragment fragment : activeFragments) {
            if (fragment == null) continue;
            Class fragmentClass = fragment.getClass();
            boolean instance = target.isInstance(fragment);
            if (fragmentClass.equals(target) || instance) {
                fragment.onActivityResult(request, result, data);
                return;
            }
            sendResultToTarget(target, fragment, request, result, data);
        }
    }


    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.show();
            initProgressDialog();
        } else {
            progressDialog.show();
            initProgressDialog();
        }
    }

    @Override
    public void showErrorView() {
        hideProgressBar();
        findViewById(R.id.fragment_container).setVisibility(View.GONE);
        findViewById(R.id.view_error).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorView() {
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
        findViewById(R.id.view_error).setVisibility(View.GONE);
    }

    @Override
    public void showSnack(String message) {
        snack.showSnack(message);
    }

    @Override
    public void showSnack(String message, String button, View.OnClickListener callback) {
        snack.showSnack(message, button, callback);
    }

    @Override
    public void showSnack(@StringRes int stringRes) {
        snack.showSnack(stringRes);
    }

    @Override
    public void setOnErrorClick(ErrorView.ErrorListener listener) {
        ErrorView errorView = findViewById(R.id.view_error);
        errorView.setErrorListener(listener);
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void hideProgressBar() {
        findViewById(R.id.progress_bar).setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
    }

    public void sendResultToTarget(Class target, int request, int result, Intent data) {
        sendResultToTarget(target, null, request, result, data);
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
        compositeDisposable = null;
    }


}
