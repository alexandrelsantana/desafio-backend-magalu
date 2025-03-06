package com.magalu.domain.entity;

import com.magalu.domain.utils.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Entity {
    protected String uuid;

    public static String uuid(){
        return IdUtil.generateUUID();
    }
}
