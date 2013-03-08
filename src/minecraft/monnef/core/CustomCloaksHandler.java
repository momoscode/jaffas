package monnef.core;

import com.google.common.base.Joiner;
import monnef.core.utils.UrlHelper;
import net.minecraft.entity.player.EntityPlayer;

import java.util.HashSet;

public class CustomCloaksHandler implements ICloakHandler {
    private static final String JAFFA_CLOAK_URL_BASE = Reference.URL + "/skin/cloak/_get.php";
    private static final String JAFFA_CLOAK_URL = JAFFA_CLOAK_URL_BASE + "?name=%s";
    private static HashSet<String> specialNames = new HashSet<String>();

    static {
        UrlHelper.getNames(JAFFA_CLOAK_URL_BASE, specialNames);
        MonnefCorePlugin.Log.printFine("special names: " + Joiner.on(", ").join(specialNames));
    }

    @Override
    public void handleCloak(EntityPlayer player, String name) {
        if (MonnefCorePlugin.debugEnv) {
            name = "monnef";
        }
        String url = String.format(JAFFA_CLOAK_URL, name);
        if (specialNames.contains(name)) {
            player.cloakUrl = player.playerCloakUrl = url;
        }
    }
}
