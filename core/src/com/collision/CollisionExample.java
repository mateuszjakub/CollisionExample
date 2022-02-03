package com.collision;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class CollisionExample extends ApplicationAdapter {
	private static final int PLAYER_SPEED = 100;
	SpriteBatch batch;
	Texture bearImg;
	Texture blockImg;
	private CollisionActor player;
	private CollisionActor[] blocks;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		bearImg = new Texture("bear.png");
		blockImg = new Texture("block.png");

		player = new CollisionActor(bearImg, 0,0);
		blocks = new CollisionActor[3];
		blocks[0] = new CollisionActor(blockImg, 100, 100);
		blocks[1] = new CollisionActor(blockImg, 200, 100);
		blocks[2] = new CollisionActor(blockImg, 300, 200);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		handleKeyboard();


		batch.begin();
		for (CollisionActor block : blocks) {
			block.draw(batch, 1);
		}
		player.draw(batch,1);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		blockImg.dispose();
		bearImg.dispose();
	}

	private void handleKeyboard() {
		float delta = Gdx.graphics.getDeltaTime();
		float deltaX = 0, deltaY = 0;

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			deltaX = PLAYER_SPEED * delta;
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			deltaX = -PLAYER_SPEED * delta;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			deltaY = PLAYER_SPEED * delta;
		} else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			deltaY = -PLAYER_SPEED * delta;
		}

		if (deltaX != 0 || deltaY != 0) {
			movePlayer(deltaX, deltaY);
		}
	}

	private void movePlayer(float deltaX, float deltaY) {
		player.moveBy(deltaX, deltaY);
		for(CollisionActor block : blocks){
			if(player.checkCollision(block)){
				player.moveBy(-deltaX, -deltaY);
				break;
			}
		}
	}
}
