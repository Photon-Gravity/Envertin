package envertin.content;

import envertin.graphics.DrawColor;
import envertin.graphics.EnvFx;
import envertin.graphics.EnvPal;
import envertin.type.RaycastPylon;
import mindustry.type.Category;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawFlame;
import mindustry.world.draw.DrawLiquidTile;
import mindustry.world.draw.DrawMulti;

import static envertin.content.EnvertinItems.*;
import static envertin.content.EnvertinLiquids.distilledWater;
import static envertin.util.EnvConstant.s;
import static mindustry.content.Liquids.hydrogen;
import static mindustry.type.ItemStack.with;

public class EnvertinBlocks {
	public static Block raycastPylon, receptor, capacitor, sulfurBurner, hydrogenBurner;

	public static void load(){
		//power
		raycastPylon = new RaycastPylon("raycast-pylon"){{
			size = 1;
			range = 15;
			consumesPower = outputsPower = true;
			fogRadius = 1;
			consumePowerBuffered(20);
			requirements(Category.power, with(antimony, 10, debris, 5));
		}};
		receptor = new Battery("receptor"){{
			size = 1;
			consumePowerBuffered(100);
			requirements(Category.power, with(antimony, 4, debris, 1));
		}};

		capacitor = new Battery("capacitor"){{
			size = 1;
			consumePowerBuffered(1000);
			requirements(Category.power, with(antimony, 20, molybdenum, 5));
			emptyLightColor =  EnvPal.powerDark;
			fullLightColor =  EnvPal.powerLight;
		}};
		sulfurBurner = new ConsumeGenerator("sulfur-burner"){{
			size = 2;
			consumeItem(sulfur);
			powerProduction = 250/s;
			generateEffect = EnvFx.sulfurSmoke;
			drawer = new DrawMulti(
					new DrawColor(EnvPal.outline),
					new DrawDefault()
			);
			requirements(Category.power, with(antimony, 40, molybdenum, 30));
		}};
		hydrogenBurner = new ConsumeGenerator("hydrogen-burner"){{
			size = 3;
			consumeLiquid(hydrogen, 4/s);
			outputLiquid = new LiquidStack(distilledWater, 4/s);
			powerProduction = 50/s;
			drawer = new DrawMulti(
					new DrawColor(EnvPal.outline),
					new DrawLiquidTile(distilledWater),
					new DrawLiquidTile(hydrogen),
					new DrawFlame(),
					new DrawDefault()
			);
			requirements(Category.power, with(antimony, 60, debris, 45));
		}};
	}
}
