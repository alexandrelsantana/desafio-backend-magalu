package com.magalu.domain.ValueObject.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Message {
    String message;
    String to;
}
