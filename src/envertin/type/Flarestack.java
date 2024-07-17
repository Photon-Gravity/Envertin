package envertin.type;

import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;
import arc.util.Eachable;
import envertin.graphics.EnvFx;
import mindustry.entities.Effect;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;

import static envertin.util.EnvVars.*;

public class Flarestack extends Block {
	float effectChance = 0.04f;
	float effectRadius = 8*px;
	public float removeSpeed = 6/s;
	public DrawBlock drawer;
	public Flarestack(String name) {
		super(name);
		acceptsItems = false;
		hasLiquids = true;
		update = true;
		solid = true;
	}

	@Override
	public void load() {
		super.load();
		drawer.load(this);
	}

	@Override
	public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list){
		drawer.drawPlan(this, plan, list);
	}

	public TextureRegion[] icons(){
		return drawer.finalIcons(this);
	}

	@Override
	public void getRegionsToOutline(Seq<TextureRegion> out){
		drawer.getRegionsToOutline(this, out);
	}


	@SuppressWarnings("unused")
	public class FlarestackBuild extends Building {

		Effect incinerateEffect = EnvFx.chemicalSmoke;

		@Override
		public void draw() {
			super.draw();
			drawer.draw(this);
		}

		@Override
		public void drawLight(){
			super.drawLight();
			drawer.drawLight(this);
		}

		@Override
		public void updateTile() {
			if(Math.random() < effectChance && liquids.current() != null && liquids.currentAmount() > 0 && efficiency > 0){
				tmpColor = liquids.current().color.cpy().mul(0.5f).add((liquids.current().flammability > 0 ? Color.black.cpy(): Color.white.cpy()).mul(0.5f));
				incinerateEffect.at(x + (float)Math.random()*effectRadius, y+(float)Math.random()*effectRadius, tmpColor);
			}
			if(liquids.current().explosiveness > 0){
				damage(Math.min(liquids.currentAmount(), efficiency * removeSpeed));
			}
			liquids.remove(liquids.current(), Math.min(liquids.currentAmount(), efficiency * removeSpeed));

			super.updateTile();
		}

		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return hasLiquids && (liquids.currentAmount() == 0 || liquids.current() == liquid);
		}
	}
}
