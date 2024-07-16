package envertin.graphics;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.draw.DrawRegion;

import static envertin.util.EnvConstant.px;

public class DrawGrindingWheels extends DrawRegion {
	TextureRegion iconRegion, white;
	Color[] wheelColors = new Color[]{EnvPal.metalLight, EnvPal.metal, EnvPal.metalDark};
	int wheelCount = 5, teeth = 6;
	float xOffset = -32*px, yOffset = -30*px,
			rowOffset = 16*px, wheelSpacing = 12 * px,
			width = 48*px, height = 12*px,
			rotationSpeed = 0.01f;
	@Override
	public void load(Block block) {
		super.load(block);
		iconRegion = Core.atlas.find(block.name + "-wheels-icon");
		white = Core.atlas.find("white");
	}

	@Override
	public void draw(Building build) {
		for(int i=0; i < wheelCount; i++){
			float rotation = (rotationSpeed * build.totalProgress()) % (float)(Math.PI * 2);
			if((i & 1) == 1) rotation *= -1;

			float wheelX = build.x + xOffset + ((i & 1) == 1 ? rowOffset : 0);
			float wheelY = build.y + yOffset + i * wheelSpacing;

			//Draw.color(wheelColors[1]);
			//Draw.rect(white, wheelX + width/2, wheelY + height/2, width, height);
			for(int j=0; j<teeth; j++){
				float r = (rotation + (float)Math.PI * 2 / teeth * j) % (float)(Math.PI*2);

				float r2 = (rotation + (float)Math.PI * 2 / teeth * (j+1)) % (float)(Math.PI*2);

				if(r2 > r) r2 -= Math.PI * 2;

				float toothX = ((float)Math.cos(r)+1) * width/2 + wheelX;
				float tooth2X = ((float)Math.cos(r2)+1) * width/2 + wheelX;

				float a = Math.min(1 + (float)Math.cos(r+r2), 1);

				if(tooth2X - toothX < 0) continue;

				Draw.color(wheelColors[1]);
				Draw.rect(white, toothX + (tooth2X - toothX)/2, wheelY + height/2, tooth2X - toothX, height);

				Draw.color(wheelColors[toothX + tooth2X- wheelX - wheelX > width ? 0 : 2].cpy().a(a));
				Draw.rect(white, toothX + (tooth2X - toothX)/2, wheelY + height/2, tooth2X - toothX, height);
			}
		}
		Draw.color(Color.white);
	}

	@Override
	public TextureRegion[] icons(Block block) {
		return new TextureRegion[]{};
	}
}
