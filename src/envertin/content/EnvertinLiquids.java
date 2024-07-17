package envertin.content;

import envertin.graphics.EnvPal;
import mindustry.type.Liquid;

public class EnvertinLiquids {
	public static Liquid distilledWater, acid, slurry, hydrogenSulfide, nitrocaust;
	public static void load(){
		distilledWater = new Liquid("distilled-water");
		distilledWater.color = EnvPal.distilledWater;

		acid = new Liquid("acid");
		acid.color = EnvPal.acid;
		acid.coolant = false;

		slurry = new Liquid("slurry");
		slurry.color = EnvPal.slurry;
		slurry.coolant = false;

		hydrogenSulfide = new Liquid("hydrogen-sulfide");
		hydrogenSulfide.color = EnvPal.hydrogenSulfide;
		hydrogenSulfide.gas = true;
		hydrogenSulfide.flammability = 0.25f;

		nitrocaust = new Liquid("nitrocaust");
		nitrocaust.color = EnvPal.nitrocaust;
		nitrocaust.explosiveness = 0.75f;
		nitrocaust.coolant = false;
	}
}
