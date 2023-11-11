package com.example.demo.location.entity;

import com.example.demo.location.dto.CoordinateVo;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter @Setter
public abstract class Location {
    @Column(name = "name")
    protected String name;

    @Column(name = "latitude")
    protected Long latitude;

    @Column(name = "longitude")
    protected Long longitude;

    public void setCoordinates(CoordinateVo coordinates) {
        this.setLatitude(coordinates.Latitude());
        this.setLongitude(coordinates.Longitude());
    }

    public boolean hasCoordinates() {
        return hasValue(this.latitude) && hasValue(this.longitude);
    }

    private static boolean hasValue(Long value) {
        return value != null;
    }

    public void setName(String name) {
        this.name = name;
        this.latitude = null;
        this.longitude = null;
    }
}
