package monnef.jaffas;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import monnef.jaffas.food.Reference;

import java.util.Arrays;

public abstract class mod_jaffas {
    protected boolean thisIsMainModule = false;

    protected mod_jaffas() {
    }

    private void handleMetadata() {
        ModContainer container = FMLCommonHandler.instance().findContainerFor(this);
        ModMetadata metaData = container.getMetadata();
        fillCommonMetadata(metaData);
        fillModuleSpecificMetadata(metaData);
    }

    protected void fillModuleSpecificMetadata(ModMetadata data) {
    }

    protected void fillCommonMetadata(ModMetadata data) {
        data.autogenerated = false;
        data.authorList = Arrays.asList(monnef.core.Reference.MONNEF, monnef.core.Reference.TIARTYOS);
        data.logoFile = "jaffas_logo.png";
        data.url = monnef.core.Reference.URL;

        if (!thisIsMainModule) {
            setAsChildOfFoodModule(data);
        }
    }

    protected void setAsChildOfFoodModule(ModMetadata data) {
        data.parent = Reference.ModId;
    }

    public void load(FMLInitializationEvent event) {
        handleMetadata();
    }
}
