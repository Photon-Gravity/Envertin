package envertin.content;

import envertin.graphics.EnvPal;
import mindustry.type.Item;

public class EnvertinItems {
	public static Item antimony, debris, molybdenum, sulfur, dryfilm, neodymium;
	public static void load(){
		antimony = new Item("antimony");
		antimony.color = EnvPal.antimony;
		antimony.hardness = 1;

		debris = new Item("debris");
		debris.color = EnvPal.debris;

		molybdenum = new Item("molybdenum");
		molybdenum.color = EnvPal.molybdenum;

		sulfur = new Item("sulfur");
		sulfur.color = EnvPal.sulfur;
		sulfur.hardness = 2;
		sulfur.flammability = 0.5f;

		dryfilm = new Item("dryfilm");
		dryfilm.color = EnvPal.dryfilm;

		neodymium = new Item("neodymium");
		neodymium.color = EnvPal.neodymium;
		neodymium.hardness = 3;
	}
}
