package com.example.springdemo.type;

import com.example.springdemo.data.MyData;
import com.example.springdemo.data.MyTarget;

public interface ICustomType {
    boolean validate(MyTarget target, MyData src);
}
