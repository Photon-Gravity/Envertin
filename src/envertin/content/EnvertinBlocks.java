package envertin.content;

import envertin.graphics.DrawColor;
import envertin.graphics.EnvFx;
import envertin.graphics.EnvPal;
import envertin.type.RuinedBuilding;
import mindustry.content.Fx;
import mindustry.gen.Sounds;
import envertin.type.RaycastPylon;
import mindustry.graphics.CacheLayer;
import mindustry.type.Category;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.power.Battery;
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
			corrodilith, stibnite, ancientFloor, deepAcid, shallowAcid, corrodilithAcid, stibniteAcid,
			corrodilithWall, stibniteWall,
			corrodilithBoulder, stibniteBoulder,
			ancientVent, ancientFoundation, ruinedAncientForge,
			antimonyOre,
			ventAdapter,
			raycastPylon, receptor, capacitor, sulfurBurner, hydrogenBurner,
			reclaimedAncientForge;


	public static void load(){
		//environment
		corrodilith = new Floor("corrodilith"){{
			variants = 6;
		}};

		stibnite = new Floor("stibnite"){{
			variants = 6;
		}};
		ancientFloor = new Floor("ancient-floor"){{
			variants = 5;
		}};

		deepAcid = new Floor("deep-acid"){{
			variants=0;
			liquidDrop = EnvertinLiquids.acid;
			cacheLayer = CacheLayer.water;
			isLiquid=true;
		}};
		shallowAcid = new Floor("shallow-acid"){{
			variants=0;
			liquidDrop = EnvertinLiquids.acid;
			cacheLayer = CacheLayer.water;
			isLiquid=true;
		}};
		corrodilithAcid = new Floor("corrodilith-acid"){{
			variants=6;
			liquidDrop = EnvertinLiquids.acid;
			cacheLayer = CacheLayer.water;
			isLiquid=true;
		}};
		stibniteAcid = new Floor("stibnite-acid"){{
			variants=6;
			liquidDrop = EnvertinLiquids.acid;
			cacheLayer = CacheLayer.water;
			isLiquid=true;
		}};
		corrodilithWall = new StaticWall("corrodilith-wall"){{
			variants = 6;
			corrodilith.asFloor().wall = this;
		}};
		stibniteWall = new StaticWall("stibnite-wall"){{
			variants = 6;
			corrodilith.asFloor().wall = this;
		}};

		corrodilithBoulder = new Prop("corrodilith-boulder"){{
			variants = 6;
			corrodilith.asFloor().decoration =this;
		}};

		stibniteBoulder = new Prop("stibnite-boulder"){{
			variants = 6;
			corrodilith.asFloor().decoration =this;
		}};

		ancientVent = new SteamVent("ancient-vent"){{
			variants = 0;
			attributes.set(Attribute.steam, 0f);
			attributes.set(EnvertinAttributes.hydrogen, 1f);
			blendGroup = ancientFloor;
			effect = EnvFx.ventHydrogen;
		}};
		//ores/points of interest
		antimonyOre = new OreBlock("antimony-ore"){{
			itemDrop = antimony;
			variants = 4;
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
	}
}
