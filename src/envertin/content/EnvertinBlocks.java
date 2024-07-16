package envertin.content;

import envertin.graphics.DrawColor;
import envertin.graphics.DrawGrindingWheels;
import envertin.graphics.EnvFx;
import envertin.graphics.EnvPal;
import envertin.type.RuinedBuilding;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import envertin.type.RaycastPylon;
import mindustry.type.Category;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.SteamVent;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.ConsumeGenerator;
import mindustry.world.blocks.production.AttributeCrafter;
import mindustry.world.blocks.production.GenericCrafter;
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
			ventAdapter,
			raycastPylon, receptor, capacitor, sulfurBurner, hydrogenBurner,
			reclaimedAncientForge, recycler;


	public static void load(){
		//environment
		ancientFloor = new Floor("ancient-floor"){{
			variants = 5;
		}};
		ancientVent = new SteamVent("ancient-vent"){{
			variants = 0;
			attributes.set(Attribute.steam, 0f);
			attributes.set(EnvertinAttributes.hydrogen, 1f);
			blendGroup = ancientFloor;
			effect = EnvFx.ventHydrogen;
		}};
		//production
		ventAdapter = new AttributeCrafter("vent-adapter"){{
			requirements(Category.production, with(antimony, 45, debris, 25));
			attribute = EnvertinAttributes.hydrogen;
			minEfficiency = 9f - 0.0001f;
			baseEfficiency = 0f;
			displayEfficiency = false;
			craftEffect = Fx.none;
			drawer = new DrawMulti(
					new DrawColor(EnvPal.outline, 0.2f),
					new DrawLiquidTile(hydrogen, 1f),
					new DrawDefault()
			);
			craftTime = 120f;
			size = 3;
			ambientSound = Sounds.hum;
			ambientSoundVolume = 0.06f;
			hasLiquids = true;
			boostScale = 1f / 9f;
			itemCapacity = 0;
			outputLiquid = new LiquidStack(hydrogen, 12/s);
			liquidCapacity = 60f;
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
			outputLiquid = new LiquidStack(distilledWater, 1/s);
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
		recycler = new GenericCrafter("recycler"){{
			size = 3;
			craftEffect = EnvFx.molybdenicDust;
			consumeItem(debris, 3);
			consumePower(100/s);
			craftTime = 3 * s;
			outputItems = with(molybdenum, 2);
			drawer = new DrawMulti(
					new DrawColor(EnvPal.outline),
					new DrawGrindingWheels(),
					new DrawDefault()
			);
			requirements(Category.crafting, with(antimony, 80, molybdenum, 10));
		}};
	}
}
