package com.jaydenho.javafeature;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hedazhao on 2016/7/6.
 */
public class MyLambda {

    private static final String TAG = MyLambda.class.getSimpleName();
    private List<Person> mPersons = null;

    public MyLambda() {
        mPersons = new ArrayList<>();
    }

    public class Person {
        private int age;
        private String name;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public interface ISyntax {//Lambda表达式只适用于只含有一个抽象方法的接口或者抽象类

        int getInt(int i);
    }

    private void invokeSyntax(ISyntax s) {//调用这个方法需要传递ISyntax的实现类
        s.getInt(10);
    }

    private void learnSyntax() {
        //匿名内部类已经省略了声明对象这个步骤，看起来很简洁了。
        invokeSyntax(new ISyntax() {
            @Override
            public int getInt(int i) {
                return i++;
            }
        });

        // Lambda表达式其实也是匿名的，只不过不仅仅匿了对象名称，还匿了类名，方法名
        invokeSyntax((int i) -> { //这里的int i就是接口ISyntax中的getInt()方法中的参数
            return i++; //大括号中的内容就是getInt()方法的实现
        });

        invokeSyntax((i) -> i++);//可以省略参数类型，还可以省略大括号和“return”，直接写返回值
    }

    //Target Type
    public interface IInteger {
        void setInt(int i);
    }

    public interface IString {
        void setString(String str);
    }

    public interface IMultiParams {
        void setString(String str1, String str2);
    }

    public interface IReturn {
        String getString(String str);
    }

    private void use(IInteger e) {
        e.setInt(1);
    }

    private void use(IString t) {
        t.setString("IString");
    }

    private void use(IMultiParams m) {
        m.setString("param1", "param2");
    }

    private void use(IReturn r) {
        r.getString("IReturn");
    }

    public void learnTargetType() {
        //IInteger
        use((int i) -> {
            Log.d(TAG, "i: " + i);
        });
        //IString
        use((String str) -> {
            Log.d(TAG, "str: " + str);
        });
        use((param1, param2) -> {
            Log.d(TAG, "param1: " + param1 + " param2: " + param2);
        });
        //IReturn
        use((String str) -> "我有返回值" + str);
        FileFilter java = (File f) -> f.getName().endsWith("*.java");
    }

}