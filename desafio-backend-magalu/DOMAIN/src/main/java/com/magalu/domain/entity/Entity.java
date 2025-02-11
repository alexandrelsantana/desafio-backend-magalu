package com.magalu.domain.entity;

import com.magalu.domain.utils.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public abstract class Entity {
    private String uuid;

    public static String uuid(){
        return IdUtil.generateUUID();
    }
}
