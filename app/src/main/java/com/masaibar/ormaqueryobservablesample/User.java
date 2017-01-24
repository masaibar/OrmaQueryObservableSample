package com.masaibar.ormaqueryobservablesample;

import android.support.annotation.NonNull;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

/**
 * Created by masaibar on 2017/01/24.
 */

@Table
public class User {

    public static User create(@NonNull String name) {
        User user = new User();
        user.name = name;
        return user;
    }

    @PrimaryKey
    public long id;

    @Column(indexed =  true)
    public String name;

    @Override
    public String toString() {
        return String.format("id = %s, name = %s", id, name);
    }
}
