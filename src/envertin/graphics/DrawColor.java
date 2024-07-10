package envertin.graphics;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;

import static envertin.util.EnvConstant.px;

public class DrawColor extends DrawBlock {

	Color color;
	float paddingFract;

	public DrawColor(Color color){
		this.color = color;
		this.paddingFract = 0f;
	}
	public DrawColor(Color color, float paddingFract){
		this.color = color;
		this.paddingFract = paddingFract;
	}

	@Override
	public void draw(Building build) {
		TextureRegion bottom = Core.atlas.find("white");

		float size = build.block.size * 32 * (1-paddingFract) * px;

		Draw.color(color);
		Draw.rect(bottom, build.x, build.y, size, size);
		Draw.color(Color.white);
	}

	@Override
	public void drawPlan(Block block, BuildPlan plan, Eachable<BuildPlan> list) {
		super.drawPlan(block, plan, list);
	}

	@Override
	public TextureRegion[] icons(Block block) {
		return super.icons(block);
	}
}
