package envertin.content;

import envertin.graphics.DrawColor;
import envertin.graphics.EnvFx;
import envertin.graphics.EnvPal;
import envertin.type.RuinedBuilding;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.SteamVent;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawFlame;
import mindustry.world.draw.DrawLiquidTile;
import mindustry.world.draw.DrawMulti;
import mindustry.world.meta.Attribute;

import static envertin.content.EnvertinItems.*;
import static envertin.content.EnvertinLiquids.distilledWater;
import static envertin.util.EnvConstant.px;
import static envertin.util.EnvConstant.s;
import static mindustry.content.Liquids.hydrogen;
import static mindustry.type.ItemStack.with;

public class EnvertinBlocks {
	public static Block
			ancientFloor,
			ancientVent, ancientFoundation, ruinedAncientForge,
			sulfurBurner, hydrogenBurner,
			reclaimedAncientForge, molybdenumSmelter;
	public static void load(){
		//environment
		ancientFloor = new Floor("ancient-floor"){{
			variants = 5;
		}};
		ancientVent = new SteamVent("ancient-vent"){{
			attributes.set(Attribute.steam, 0f);
			attributes.set(EnvertinAttributes.hydrogen, 1f);
			blendGroup = ancientFloor;
		}};
		ancientFoundation = new Floor("ancient-foundation"){{
			attributes.set(EnvertinAttributes.ancientFoundations, 1f);
			variants = 0;
		}};
		ruinedAncientForge = new RuinedBuilding("ruined-ancient-forge"){{
			attributes.set(Attribute.steam, 0f);
			attributes.set(EnvertinAttributes.ruinedForge, 1/16f);
			blendGroup = ancientFloor;
		}};
		//power
		sulfurBurner = new ConsumeGenerator("sulfur-burner"){{
			size = 2;
			consumeItem(sulfur);
			powerProduction = 250/s;
			generateEffect = EnvFx.sulfurSmoke;
			generateEffectRange = 10 * px;
			effectChance = 0.05f;
			loopSound = Sounds.combustion;
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
		//crafting
		reclaimedAncientForge = new AttributeCrafter("reclaimed-ancient-forge"){{
			size = 4;
			attribute = EnvertinAttributes.ruinedForge;
			baseEfficiency = 0f;
			minEfficiency = 1f;
			consumeItem(debris, 2);
			consumePower(150/s);
			outputItem = with(molybdenum, 1)[0];
			craftTime = 4 * s;
			requirements(Category.crafting, with(debris, 200, antimony, 150));
		}};
	}
}
