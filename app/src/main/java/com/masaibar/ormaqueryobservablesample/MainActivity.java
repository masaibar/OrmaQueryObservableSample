package com.masaibar.ormaqueryobservablesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editName = (EditText) findViewById(R.id.edit_name);
                String name = editName.getText().toString();

                saveUser(name);
            }
        });

        findViewById(R.id.button_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectUsers();
            }
        });

        setQueryObserver();
    }

    private void saveUser(String name) {
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Name is null or empty, return.", Toast.LENGTH_SHORT).show();
            return;
        }

        insert(User.create(name));
    }

    private void selectUsers() {
        List<User> users = getOrma().selectFromUser().toList();

        if (users.size() < 1) {
            Toast.makeText(this, "No user data, return.", Toast.LENGTH_SHORT).show();
            return;
        }

        for (User user : users) {
            Log.d("!!!", user.toString());
        }
    }

    private synchronized void insert(final User user) {
        ThreadUtil.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                getOrma().insertIntoUser(user);
            }
        });
    }

    private void setQueryObserver() {
        Observable<User_Selector> observable = getOrma().relationOfUser().createQueryObservable();

        observable.flatMap(new Function<User_Selector, ObservableSource<User>>() {
            @Override
            public ObservableSource<User> apply(User_Selector users) throws Exception {
                Log.d("!!!", "User has been changed");
                selectUsers();
                return users.executeAsObservable();
            }
        }).map(new Function<User, String>() {
            @Override
            public String apply(User user) throws Exception {
                return user.toString();
            }
        }).subscribe();
    }

    private OrmaDatabase getOrma() {
        return OrmaUtil.getInstance(getApplicationContext());
    }
}
