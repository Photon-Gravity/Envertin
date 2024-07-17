package envertin.graphics;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.draw.DrawRegion;

public class DrawSpinItems extends DrawRegion {
	Item item;
	float rotateSpeed, radius;
	public DrawSpinItems(Item item, float rotateSpeed, float radius){
		this.item = item;
		this.rotateSpeed = rotateSpeed;
		this.radius = radius;
	}
	@Override
	public void draw(Building build) {
		rand.setSeed(build.id);
		for(int i=0; i < build.items.get(item); i++){
			float rotation = rand.random((float)Math.PI*2) + build.totalProgress()*rotateSpeed/180 * (float)Math.PI;
			float distance = rand.random(radius-Vars.itemSize/2) + Vars.itemSize/2; // prevent items from being stuck in the middle

			float x = build.x + (float)Math.cos(rotation) * distance;
			float y = build.y + (float)Math.sin(rotation) * distance;

			Draw.rect(item.fullIcon, x, y, Vars.itemSize, Vars.itemSize);
		}
	}

	@Override
	public TextureRegion[] icons(Block block) {
		return new TextureRegion[]{};
	}
}
