package monnef.jaffas;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;

import java.util.Arrays;

// TODO all module should inherit this
public abstract class mod_jaffas {

    protected mod_jaffas() {
    }

    private void handleMetadata() {
        ModContainer container = FMLCommonHandler.instance().findContainerFor(this);
        ModMetadata metaData = container.getMetadata();
        fillCommonMetadata(metaData);
        fillModuleSpecificMetadata(metaData);
    }

    protected abstract void fillModuleSpecificMetadata(ModMetadata data);

    protected void fillCommonMetadata(ModMetadata data) {
        data.autogenerated = false;
        data.authorList = Arrays.asList(monnef.core.Reference.MONNEF, monnef.core.Reference.TIARTYOS);
        data.logoFile = "jaffas_logo.png";
    }

    public void load(FMLInitializationEvent event) {
        handleMetadata();
    }
}
