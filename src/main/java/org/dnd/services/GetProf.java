package org.dnd.services;

import com.zaxxer.hikari.util.SuspendResumeLock;
import org.dnd.dto.ChrDTO;
import org.dnd.services.parsers.BackParser;
import org.dnd.services.parsers.ClassInfoParser;
import org.dnd.services.parsers.InfoParser;

public class GetProf {
    public static String getProfString(ChrDTO chr) throws Exception {
        String clasURL = InfoParser.classparser().get(chr.getClas());
        String backURL = InfoParser.backgroundparser().get(chr.getBackground());
        String toolProf = (String) BackParser.backParser(backURL).get("toolsProf");
        String armorProf = (String) ClassInfoParser.classinfoparser(clasURL).get("ArmorProf");
        String weaponProf = (String) ClassInfoParser.classinfoparser(clasURL).get("WeaponProf");
        return weaponProf + "\n" + armorProf + "\n" + toolProf;
    }
}
