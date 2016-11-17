package br.com.pechinchadehoje.activity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.io.IOException;

import br.com.pechinchadehoje.R;
import br.com.pechinchadehoje.SettingsApplication;
import br.com.pechinchadehoje.dialog.LoginOuCadastroDialog;
import br.com.pechinchadehoje.fragment.BaseFragment;
import br.com.pechinchadehoje.fragment.MainFragment;
import br.com.pechinchadehoje.fragment.MenorPrecoFragment;
import br.com.pechinchadehoje.util.LocationUtil;
import br.com.pechinchadehoje.util.PermissionUtils;
import br.com.pechinchadehoje.util.constantes.Permissoes;
import de.greenrobot.event.EventBus;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG_FRAGMENT = "FRAGMENT";


    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private SettingsApplication mSettingsApplication;
    private Toolbar toolbar;
    private Bundle savedInstanceState;
    private MainFragment mainFragment;
    private EventBus mEventBus;
    private LocationUtil locationUtil;
    private ActionBarDrawerToggle drawerToggle;

    private  TextView tvCadastroOuLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationUtil = new LocationUtil(getBaseContext(), null);

        this.mainFragment = new MainFragment();
        mEventBus = EventBus.getDefault();

        this.savedInstanceState = savedInstanceState;

        mSettingsApplication = ((SettingsApplication) getApplication());
        setUpToolbar();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        drawerLayout.addDrawerListener(drawerToggle);


        navigationView = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(navigationView);

        View headerLayout = navigationView.getHeaderView(0);

        tvCadastroOuLogin = (TextView) headerLayout.findViewById(R.id.cadastroOuLogin);

        tvCadastroOuLogin.setOnClickListener(this);

        if (PermissionUtils.validate(this, Permissoes.REQUEST_CODE, Permissoes.permissoes)) {

            replaceFragment(mainFragment, false);

        }
    }


    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }



    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = MainFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = MainFragment.class;
                break;
            default:
                fragmentClass = MainFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }


    public void replaceFragment(BaseFragment fragment, boolean backStack) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStackImmediate();
        }
        if (backStack) {
            ft.addToBackStack(null);
        }
        ft.replace(R.id.container, fragment, TAG_FRAGMENT);
        ft.commitAllowingStateLoss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                //alertAndFinish();
                Snackbar.make(toolbar, R.string.msg_ativar_localizacao, Snackbar.LENGTH_SHORT).show();
                return;
            }
        }

        //isolar em um método
        this.mainFragment = new MainFragment();
        replaceFragment(mainFragment, false);
    }

    @Override
    public void onClick(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final MediaType JSON
                        = MediaType.parse("application/json; charset=utf-8");

                OkHttpClient client = new OkHttpClient();
                JsonObject innerObject = new JsonObject();
                innerObject.addProperty("email", "alexsandro@mail.com");
                innerObject.addProperty("senha", "1234");
                RequestBody body = RequestBody.create(JSON, innerObject.toString());
                Request request = new Request.Builder()
                        .url("http://ec2-52-38-136-232.us-west-2.compute.amazonaws.com:3000/usuario/public/auth")
                        .post(body)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    System.out.print(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
       // LoginOuCadastroDialog.show(MainActivity.this);

    }
}