package com.example.springdemo.type.Impl;

import com.example.springdemo.data.MyData;
import com.example.springdemo.data.MyTarget;
import com.example.springdemo.type.ICustomType;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class CustomTypeName implements ICustomType {
    @Override
    public boolean validate(MyTarget target, MyData src) {
        if(Strings.isNotEmpty(src.getName())){
            target.setTargetName(src.getName());
            return true;
        }
        return false;
    }
}
