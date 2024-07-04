package envertin.graphics;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.util.Eachable;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.world.Block;
import mindustry.world.draw.DrawBlock;

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

		bottom.width *= build.block.size * 32;
		bottom.height *= build.block.size * 32;

		bottom.width *= 1-paddingFract;
		bottom.height *= 1-paddingFract;

		Drawf.liquid(bottom, build.x, build.y, 1f, color);
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
