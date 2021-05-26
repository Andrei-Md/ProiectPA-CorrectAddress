package com.example.springproject.structures;

import com.example.springproject.structures.entities.AdministrativeHierarchy;
import com.example.springproject.structures.entities.AdministrativeUnitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdmStructures implements ApplicationRunner {

    private static AdministrativeHierarchy administrativeHierarchy;

    public static AdministrativeHierarchy getAdministrativeHierarchy() {
        return administrativeHierarchy;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //TODO create internal structures
        if(args.getSourceArgs().length>0){
            if(args.getSourceArgs()[0].equalsIgnoreCase("serialize")) {
                AdministrativeUnitUtil.serializeAdministrativeHierarchy(AdministrativeUnitUtil.ADMINISTRATIVE_UNIT_SERIALIZE_PATH);
            }
        }

        log.info("Initializing Internal Administrative Structures");
        administrativeHierarchy = AdministrativeUnitUtil.loadAdministrativeHierarchy(AdministrativeUnitUtil.ADMINISTRATIVE_UNIT_SERIALIZE_PATH);
    }

}
