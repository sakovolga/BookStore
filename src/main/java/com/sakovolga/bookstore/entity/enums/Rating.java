package com.sakovolga.bookstore.entity.enums;

import lombok.Getter;

@Getter
public enum Rating {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final Integer value;

    Rating(Integer value) {
        this.value = value;
    }

}

