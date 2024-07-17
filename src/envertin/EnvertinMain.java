package envertin;

import arc.util.Log;
import envertin.content.EnvertinBlocks;
import envertin.content.EnvertinItems;
import envertin.content.EnvertinLiquids;
import envertin.content.EnvertinSounds;
import mindustry.mod.Mod;

@SuppressWarnings("unused")
public class EnvertinMain extends Mod{

    public EnvertinMain(){

    }

    @Override
    public void loadContent(){
        Log.info("Loading Envertin content...");

        EnvertinSounds.load();

        EnvertinItems.load();
        EnvertinLiquids.load();
        //EnvertinStatusEffects.load();

        //EnvertinWeathers.load();

        //EnvertinUnitTypes.load();

        EnvertinBlocks.load();

        //EnvertinPlanets.load();

        //EnvertinSectors.load();

        //EnvertinTechTree.load();

        Log.info("Envertin content loaded!");
    }

}
