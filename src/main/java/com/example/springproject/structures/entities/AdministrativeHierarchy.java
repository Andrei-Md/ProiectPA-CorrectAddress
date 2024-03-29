package com.example.springproject.structures.entities;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * class that encapsulates an administrative hierarchy
 */
public class AdministrativeHierarchy implements Serializable {

    private String path;
    private Map<String, AdministrativeUnit> administrativeUnitMap;
    private SetMultimap<String, String> administrativeUnitHierarchy;
    private List<SetMultimap<String, AdministrativeUnit>> soundexUnitsMapList;
    private List<SetMultimap<String, AdministrativeUnit>> nysiisUnitsMapList;

    public AdministrativeHierarchy() {
        administrativeUnitMap = new TreeMap<>();
        this.administrativeUnitHierarchy = HashMultimap.create();
        this.soundexUnitsMapList = new ArrayList<>();
        this.nysiisUnitsMapList = new ArrayList<>();
    }

    public AdministrativeHierarchy(String path) {
        this.administrativeUnitMap = new TreeMap<>();
        this.administrativeUnitHierarchy = HashMultimap.create();
        this.soundexUnitsMapList = new ArrayList<>();
        this.nysiisUnitsMapList = new ArrayList<>();
        this.path = path;
    }

    /**
     * copy constructor
     *
     * @param administrativeHierarchy entity to copy
     */
    public AdministrativeHierarchy(AdministrativeHierarchy administrativeHierarchy) {
        this.path = administrativeHierarchy.getPath();

        this.administrativeUnitHierarchy = HashMultimap.create(administrativeHierarchy.getAdministrativeUnitHierarchy());
        this.administrativeUnitMap = new TreeMap<>(administrativeHierarchy.getAdministrativeUnitMap());
        this.soundexUnitsMapList = administrativeHierarchy.getSoundexUnitsMapList();
        this.nysiisUnitsMapList = administrativeHierarchy.getNysiisUnitsMapList();

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

    public List<SetMultimap<String, AdministrativeUnit>> getSoundexUnitsMapList() {
        List<SetMultimap<String, AdministrativeUnit>> returnSoundexUnitsMapList = new ArrayList<>();
        for (SetMultimap<String, AdministrativeUnit> map : this.soundexUnitsMapList) {
            returnSoundexUnitsMapList.add(HashMultimap.create(map));
        }
        return returnSoundexUnitsMapList;
    }

    public void setSoundexUnitsMapList(List<SetMultimap<String, AdministrativeUnit>> soundexUnitsMapList) {
        this.soundexUnitsMapList = new ArrayList<>();
        for (SetMultimap<String, AdministrativeUnit> map : soundexUnitsMapList) {
            this.soundexUnitsMapList.add(HashMultimap.create(map));
        }
    }

    public List<SetMultimap<String, AdministrativeUnit>> getNysiisUnitsMapList() {
        List<SetMultimap<String, AdministrativeUnit>> returnNysiisUnitsMapList = new ArrayList<>();
        for (SetMultimap<String, AdministrativeUnit> map : this.nysiisUnitsMapList) {
            returnNysiisUnitsMapList.add(HashMultimap.create(map));
        }
        return returnNysiisUnitsMapList;
    }

    public void setNysiisUnitsMapList(List<SetMultimap<String, AdministrativeUnit>> nysiisUnitsMapList) {
        this.nysiisUnitsMapList = new ArrayList<>();
        for (SetMultimap<String, AdministrativeUnit> map : nysiisUnitsMapList) {
            this.nysiisUnitsMapList.add(HashMultimap.create(map));
        }
    }

}
