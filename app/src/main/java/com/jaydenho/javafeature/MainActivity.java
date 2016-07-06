package com.jaydenho.javafeature;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.common.base.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSONObject person = getPerson();
        Optional<JSONObject> personOptional = Optional.fromNullable(person);
        Log.d(TAG, "print person's name: " + (personOptional.isPresent() ? personOptional.get().optString("name") : "null"));//时刻提醒需要考虑操作对象为空的情况
        JSONObject defaultPerson = new JSONObject();
        try {
            defaultPerson.put("name", "default");
            defaultPerson.put("age", 18);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "print person's name: " + personOptional.or(() -> {
            return new JSONObject();
        }).optString("name"));//用一行代码就可以提供默认值
        if (person == null) { //这种方式容易忘记判空,而且也复杂
            Log.d(TAG, "print person's name: " + defaultPerson.optString("name"));
        } else {
            Log.d(TAG, "print person's name: " + person.optString("name"));
        }

        View v = new View(this);
        v.setOnClickListener(view -> {
            view.setVisibility(View.GONE);
        });
    }

    private JSONObject getPerson() {
        JSONObject obj = null;
        try {
            obj = new JSONObject("{name:\"zhangsan\",age:18}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private void lambda01() {
        invoke(() -> "");
        invoke(() -> {
            JSONObject obj = getPerson();
        });
        //IString
        use((String str) -> {
            Log.d(TAG, "str: " + str);
        });
        //IInteger
        use((int i) -> {
            Log.d(TAG, "i: " + i);
        });
    }

    private void invoke(Runnable r) {
        r.run();
    }

    private void invoke(Callable c) {
        c.call();
    }

    public interface Runnable {
        void run();
    }

    public interface Callable<V> {
        V call();
    }

    private void use(IInteger e) {
        e.setInt(1);
    }

    private void use(IString t) {
        t.setString("");
    }

    public interface IInteger {
        void setInt(int i);
    }

    public interface IString {
        void setString(String str);
    }
}
