package com.example.springproject.structures.entities;

import com.example.springproject.structures.GlobalUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class AdministrativeUnitUtilTest {

    @Test
    void loadAdministrativeHierarchy() {
        assertDoesNotThrow(() -> AdministrativeUnitUtil.loadAdministrativeHierarchy(GlobalUtil.ADMINISTRATIVE_UNIT_SERIALIZE_PATH));
    }

    @Test
    void saveAdministrativeHierarchy() {
        AdministrativeHierarchy administrativeHierarchy = AdministrativeUnitUtil.loadAdministrativeHierarchy(GlobalUtil.ADMINISTRATIVE_UNIT_SERIALIZE_PATH);
        assertDoesNotThrow(() -> AdministrativeUnitUtil.saveAdministrativeHierarchy(administrativeHierarchy));
    }

    @Test
    void serializeAdministrativeHierarchy() {
        assertDoesNotThrow(() -> AdministrativeUnitUtil.serializeAdministrativeHierarchy(GlobalUtil.ADMINISTRATIVE_UNIT_SERIALIZE_PATH));
    }
}