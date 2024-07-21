package envertin.graphics;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.geom.Vec2;
import mindustry.entities.Effect;
import mindustry.graphics.Layer;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;
import static envertin.util.EnvVars.s;
import static mindustry.content.Fx.rand;

public class EnvFx {
	public static Vec2 v = new Vec2();
	public static Effect
			sulfurSmoke = new Effect(36*s, e -> {
				color(EnvPal.sulfuricSmoke);

				float z = Draw.z();
				Draw.z(111);

				rand.setSeed(e.id);
				float len = rand.random(4f) + 44f;

				e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
					alpha(b.fout() * 0.8f);
					v.trns(45, len * b.fin());
					Fill.circle(e.x + v.x, e.y + v.y, 3f * b.fin() + 0.5f + Math.min(b.fin() * 8, 1));
				});
				Draw.z(z);
			}),
			ventHydrogen = new Effect(140f, e -> {
				color(EnvPal.hydrogenLight, EnvPal.hydrogenDark, e.fin());

				alpha(e.fslope() * 0.78f);

				float length = 3f + e.finpow() * 10f;
				rand.setSeed(e.id);
				for(int i = 0; i < rand.random(3, 5); i++){
					v.trns(rand.random(360f), rand.random(length));
					Fill.circle(e.x + v.x, e.y + v.y, rand.random(1.2f, 3.5f) + e.fslope() * 1.1f);
				}
			}).layer(Layer.darkness - 1),
			debrisDust = new Effect(40, e -> {
				float z = Draw.z();
				randLenVectors(e.id, 5, 3f + e.fin() * 8f, (x, y) -> {
					color(EnvPal.molybdenum, EnvPal.cragsilt, e.fin());
					Draw.z(99);
					Fill.square(e.x + x, e.y + y, e.fout() * 2f + 0.5f, 45);
					Draw.z(110);
					Fill.square(e.x + x, e.y + y, e.fout() * 2f + 0.5f, 45);

				});
				Draw.z(z);
			}),
			acidicVapour = new Effect(12*s, e -> {
				color(EnvPal.acidicVapour);

				float z = Draw.z();
				Draw.z(111);

				rand.setSeed(e.id);
				float len = rand.random(4f) + 18f;

				e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
					alpha(b.fout() * 0.8f);
					v.trns(45, len * b.fin());
					Fill.circle(e.x + v.x, e.y + v.y, 1.5f * b.fin() + 0.5f + Math.min(b.fin() * 4, 1));
				});
				Draw.z(z);
			}),
			chemicalSmoke = new Effect(12*s, e -> {
				color(e.color);

				float z = Draw.z();
				Draw.z(111);

				rand.setSeed(e.id);
				float len = rand.random(4f) + 44f;

				e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
					alpha(b.fout() * 0.5f);
					v.trns(45, len * b.fin());
					Fill.circle(e.x + v.x, e.y + v.y, 3f * b.fin() + 0.5f + Math.min(b.fin() * 8, 1));
				});
				Draw.z(z);
			}),
			cragsiltDust = new Effect(40, e -> {
				float z = Draw.z();
				randLenVectors(e.id, 5, 3f + e.fin() * 8f, (x, y) -> {
					color(EnvPal.cragsilt);
					Draw.z(99);
					Fill.square(e.x + x, e.y + y, e.fout() * 2f + 0.5f, 45);
				});
				Draw.z(z);
			});
}
