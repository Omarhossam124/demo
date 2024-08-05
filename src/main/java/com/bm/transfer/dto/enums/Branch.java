package com.bm.transfer.dto.enums;
import lombok.Getter;


@Getter
public enum Branch {
    MAIN("Main Branch"),
    WEST("West Branch"),
    EAST("East Branch"),
    NORTH("North Branch"),
    SOUTH("South Branch");

    private final String description;

    Branch(String description) {
        this.description = description;
    }
}
