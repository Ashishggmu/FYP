package com.Ashish.foodnetwork.UserDashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.Ashish.foodnetwork.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav;
    HomeFragment homeFragment;
    CartFragment  cartFragment;
    HistoryFragment historyFragment;
    ProfileFragment profileFragment;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottom_nav=findViewById(R.id.bottom_nav);

        homeFragment= new HomeFragment();
        cartFragment = new CartFragment();
        historyFragment= new HistoryFragment();
        profileFragment= new ProfileFragment();
        currentFragment= homeFragment;

        getSupportFragmentManager().beginTransaction().add(R.id.nav, currentFragment).commit();

        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        replaceFragment(new HomeFragment());
                        break;
                    case R.id.cart:
                        replaceFragment(new CartFragment());
                        if(CartFragment.IS_CART_CHANGED)
                            if(cartFragment.isAdded())
                                cartFragment.refreshCart();
                        break;
                    case R.id.history:
                        replaceFragment(new HistoryFragment());
                        break;
                    case R.id.profile:
                        replaceFragment(new ProfileFragment());
                        break;
                }

                return true;
            }
        });

    }

    private void replaceFragment(Fragment fragment){
        if(fragment== currentFragment){
            return;
        }
        getSupportFragmentManager().beginTransaction().hide(currentFragment).commit();
        if (fragment.isAdded()){
            getSupportFragmentManager().beginTransaction().show(fragment).commit();
        }
        else{
            getSupportFragmentManager().beginTransaction().add(R.id.nav, fragment, "").commit();

        }
        currentFragment= fragment;
    }
}