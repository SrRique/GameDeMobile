package com.mygtx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {


	private  SpriteBatch batch;
	private  Texture passaro;
	private Texture fundo;

	private int movimentaY = 0;
	private  int movimentaX = 0;

	//Valores do tamanho da tela
	private float larguraDispositivo;
	private  float alturaDispositivo;

	@Override
	public void create () {
		batch = new SpriteBatch();
		passaro = new Texture("passaro1.png");
		fundo = new Texture("fundo.png");

		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
	}

	@Override
	public void render () {

		batch.begin();

		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
		batch.draw(passaro, 50, 50, movimentaX, movimentaY );

		movimentaY++;
		movimentaX++;

		batch.end();
	}
	
	@Override
	public void dispose () {

	}
}
