package com.jabari.client.activity.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.SupportActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jabari.client.R;
import com.jabari.client.activity.finance.FinancialActivity;
import com.jabari.client.activity.finance.IntroductionActivity;
import com.jabari.client.activity.finance.ManagementActivity;
import com.jabari.client.activity.help.AboutUsActivity;
import com.jabari.client.activity.report.AddressActivity;
import com.jabari.client.activity.report.ArchiveActivity;
import com.jabari.client.custom.GlobalVariables;
import com.jabari.client.custom.PrefManager;
import com.jabari.client.fragment.OnGoingFragment;
import com.jabari.client.fragment.ScheduledFragment;
import com.jabari.client.fragment.UnSuccessfulFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private android.support.v7.widget.Toolbar toolbar;
    private FloatingActionButton fbtn_add_req;
    private TextView tv_scheduled, tv_unsuccessful, tv_ongoing;
    private int current_selected = 0;
    private Button btn_exit;
    private String ongoing = "ongoing", unsuccessful = "unsuccessful", scheduled = "scheduled";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpNavigationView();
        setUpTab_travelList();

        logOff();
        fbtn_add_req = findViewById(R.id.btn_login);
        fbtn_add_req.bringToFront();
        fbtn_add_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StartPosActivity.class));

            }
        });

    }

    private void setUpNavigationView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.credit_management:
                Intent intent = new Intent(MainActivity.this, ManagementActivity.class);
                startActivity(intent);
                break;
            case R.id.activities_archive:
                startActivity(new Intent(this, ArchiveActivity.class));
                break;
            case R.id.selected_addresses:
                startActivity(new Intent(this, AddressActivity.class));
                break;
            case R.id.about_:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.score_intro:
                startActivity(new Intent(this, IntroductionActivity.class));
                break;
            case R.id.sup:
                startActivity(new Intent(this, SupportActivity.class));
                break;
            case R.id.financial:
                startActivity(new Intent(this, FinancialActivity.class));
                break;


        }
        return true;
    }

    private void setUpTab_travelList() {
        tv_unsuccessful = findViewById(R.id.tv_unsuccessful);
        tv_scheduled = findViewById(R.id.tv_scheduled);
        tv_ongoing = findViewById(R.id.tv_ongoing);

        tv_ongoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_selected == 1) {
                } else {
                    tv_ongoing.setBackgroundResource(R.drawable.right_rounded_green);
                    tv_ongoing.setTextColor(getResources().getColor(R.color.white));
                    UnSelectedElse(1);
                    removeAllFragment(new OnGoingFragment(), false, ongoing);

                }
            }
        });


        tv_unsuccessful.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_selected == 2) {
                } else {
                    tv_unsuccessful.setBackgroundResource(R.drawable.back_middle_item_green);
                    tv_unsuccessful.setTextColor(getResources().getColor(R.color.white));
                    UnSelectedElse(2);
                    removeAllFragment(new UnSuccessfulFragment(), false, unsuccessful);

                }

            }
        });

        tv_scheduled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (current_selected == 3) {
                } else {
                    tv_scheduled.setBackgroundResource(R.drawable.left_rounded_green);
                    tv_scheduled.setTextColor(getResources().getColor(R.color.white));
                    UnSelectedElse(3);
                    removeAllFragment(new ScheduledFragment(), false, scheduled);

                }
            }
        });

    }

    private void UnSelectedElse(int current) {
        switch (current_selected) {
            case 1:
                tv_ongoing.setBackgroundResource(R.color.transparent);
                tv_ongoing.setTextColor(getResources().getColor(R.color.darkGray));
                current_selected = current;
                break;
            case 2:
                tv_unsuccessful.setBackgroundResource(R.color.transparent);
                tv_unsuccessful.setTextColor(getResources().getColor(R.color.darkGray));
                current_selected = current;
                break;
            case 3:
                tv_scheduled.setBackgroundResource(R.color.transparent);
                tv_scheduled.setTextColor(getResources().getColor(R.color.darkGray));
                current_selected = current;
                break;

        }
        if (current_selected == 0) {
            current_selected = current;
        }
    }

    private void removeAllFragment(Fragment replaceFragment, boolean addToBackStack, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }
        fragmentTransaction.replace(R.id.container_body, replaceFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void logOff() {
        View headerView = navigationView.getHeaderView(0);
        btn_exit = headerView.findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariables.isLogin = false;
                removePreferences();
                startActivity(new Intent(MainActivity.this, FirstActivity.class));

            }
        });

    }

    private void removePreferences() {

        PrefManager prefManager = new PrefManager(this);
        prefManager.removeToken();
        prefManager.removeUser();
        GlobalVariables.tok = "";
        GlobalVariables.phoneUser = "";

    }


}
