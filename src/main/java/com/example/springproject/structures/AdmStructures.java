package com.example.springproject.structures;

import com.example.springproject.structures.benchmark.CorrectAddressBenchmark;
import com.example.springproject.structures.benchmark.JSONAddressCorrect;
import com.example.springproject.structures.entities.AdministrativeHierarchy;
import com.example.springproject.structures.entities.AdministrativeUnitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static com.example.springproject.structures.GlobalUtil.*;

@Slf4j
@Component
public class AdmStructures implements ApplicationRunner {

    private static AdministrativeHierarchy administrativeHierarchy;

    public static AdministrativeHierarchy getAdministrativeHierarchy() {
        return administrativeHierarchy;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.getSourceArgs().length > 0) {
            if (args.getSourceArgs()[0].equalsIgnoreCase("serialize")) {
                AdministrativeUnitUtil.serializeAdministrativeHierarchy(GlobalUtil.ADMINISTRATIVE_UNIT_SERIALIZE_PATH);
            }
        }

        //create internal structures
        log.info("Initializing Internal Administrative Structures");
        administrativeHierarchy = AdministrativeUnitUtil.loadAdministrativeHierarchy(GlobalUtil.ADMINISTRATIVE_UNIT_SERIALIZE_PATH);
        if (args.getSourceArgs().length > 0) {
            if (args.getSourceArgs()[0].equalsIgnoreCase("test")) {
                CorrectAddressBenchmark correctAddressBenchmark = JSONAddressCorrect.getNumberOfRightAddresses(ADDRESSES_PATH, CORRECTED_ADDRESSES_PATH, false);
                JSONAddressCorrect.printLogs("Shuffle addresses Report:", correctAddressBenchmark);

                CorrectAddressBenchmark correctAddressBenchmarkFuzzy = JSONAddressCorrect.getNumberOfRightAddresses(ADDRESSES_FUZZY_PATH, CORRECTED_ADDRESSES_FUZZY_PATH, false);
                JSONAddressCorrect.printLogs("Fuzzy addresses Report:", correctAddressBenchmarkFuzzy);
            }
        }
    }
}
