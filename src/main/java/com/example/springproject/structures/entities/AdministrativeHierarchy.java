package com.example.springproject.structures.entities;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;


public class AdministrativeHierarchy implements Serializable {

    private String path;
    private Map<String, AdministrativeUnit> administrativeUnitMap;
    private SetMultimap<String, String> administrativeUnitHierarchy;

//    private SetMultimap<String, AdministrativeUnit>
    public AdministrativeHierarchy() {
        administrativeUnitMap = new TreeMap<>();
    }

    public AdministrativeHierarchy(String path) {
        this.administrativeUnitMap = new TreeMap<>();
        this.administrativeUnitHierarchy = HashMultimap.create();
        this.path = path;
    }

    public AdministrativeHierarchy(AdministrativeHierarchy administrativeHierarchy) {
        this.path = administrativeHierarchy.getPath();

        this.administrativeUnitHierarchy = HashMultimap.create(administrativeHierarchy.getAdministrativeUnitHierarchy());
        this.administrativeUnitMap = new TreeMap<>(administrativeHierarchy.getAdministrativeUnitMap());
    }

    public Map<String, AdministrativeUnit> getAdministrativeUnitMap() {
        return new TreeMap<>(administrativeUnitMap);
    }

    public void setAdministrativeUnitMap(Map<String, AdministrativeUnit> administrativeUnitMap) {
        this.administrativeUnitMap = new TreeMap(administrativeUnitMap);
    }

    public SetMultimap<String, String> getAdministrativeUnitHierarchy() {
        return HashMultimap.create(administrativeUnitHierarchy);
    }

    public void setAdministrativeUnitHierarchy(SetMultimap<String, String> administrativeUnitHierarchy) {
        this.administrativeUnitHierarchy = HashMultimap.create(administrativeUnitHierarchy);
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
