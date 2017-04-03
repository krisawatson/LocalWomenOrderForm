package com.kricko.constants;

public enum AdvertTypeEnum {
    ADVERT(1L),
    FEATURE(2L),
    EDITORIAL(3L),
    PHOTOSHOOT(4L),
    FRONTCOVER(5L);
    
    private final long value;
    
    AdvertTypeEnum(long value){
        this.value = value;
    }
    
    public long getValue() {
        return value;
    }
}
