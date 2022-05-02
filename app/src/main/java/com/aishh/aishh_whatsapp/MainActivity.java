package com.aishh.aishh_whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.aishh.aishh_whatsapp.Adapters.FragmentsAdapter;
import com.aishh.aishh_whatsapp.databinding.ActivityMainBinding;
import com.google.android.gms.common.api.internal.LifecycleFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    FirebaseAuth auth;
    private String[] titles=new String[]{"CHATS","STATUS","CALLS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        auth=FirebaseAuth.getInstance();
        FragmentsAdapter fragmentsAdapter=new FragmentsAdapter(this);
        activityMainBinding.viewpager.setAdapter(fragmentsAdapter);
        new TabLayoutMediator(activityMainBinding.tablayout, activityMainBinding.viewpager, (((tab, position) -> tab.setText(titles[position])))).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent intent2=new Intent(MainActivity.this,Settings.class);
                startActivity(intent2);
                break;
            case R.id.logout:
                auth.signOut();
                Intent intent=new Intent(MainActivity.this,SignIn.class);
                startActivity(intent);
                break;
            case R.id.groupchat:
                Intent intent1=new Intent(MainActivity.this,GroupChat.class);
                startActivity(intent1);
                break;
        }
        return true;
    }
}