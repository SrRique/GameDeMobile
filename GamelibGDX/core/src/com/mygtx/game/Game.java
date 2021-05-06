package com.mygtx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Game extends ApplicationAdapter {

	//vars para as texturas serem renderizadas
	private  SpriteBatch batch;
	private  Texture[] passaros;
	private Texture fundo;

	//var para movimentacao
	private int movimentaY = 0;
	private  int movimentaX = 0;

	//Valores do tamanho da tela
	private float larguraDispositivo;
	private  float alturaDispositivo;

	//var para criar a fisica
	private  float variacao = 0;
	private  float gravidade = 0;
	private  float posicaoInicialVerticalPassaro = 0;

	@Override
	public void create () {
		batch = new SpriteBatch();
		//guardando as texturas nas var
		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");
		fundo = new Texture("fundo.png");

		//guardando as infos da resolução do aparelho em variaveis para definir o tamanho das texturas
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();

		//definindo a posicao inicial do passaro
		posicaoInicialVerticalPassaro = alturaDispositivo / 2;
	}

	@Override
	//renderiza os objetos na tela
	public void render () {

		batch.begin();

		//se a variacao passar de 3, volta ao 0 assim fazendo a animação de voo
		if (variacao > 3)
			variacao = 0;

		//pega o toque na tela
		boolean toqueTela = Gdx.input.justTouched();

		//diminui a gravidade ao tocar na tela
		if (Gdx.input.justTouched())
		{
			gravidade = -25;
		}

		//faz o passaro cair
		if (posicaoInicialVerticalPassaro > 0 || toqueTela)
			posicaoInicialVerticalPassaro  = posicaoInicialVerticalPassaro - gravidade;

		//renderizando e definindo o tamanho dos objetos
		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
		batch.draw(passaros[(int) variacao], 30, posicaoInicialVerticalPassaro);

		//muda a variacao fazendo com que o passaro fique animado
		variacao += Gdx.graphics.getDeltaTime() * 10;

		gravidade++;

		//movimento em x e y
		movimentaY++;
		movimentaX++;

		batch.end();
	}
	
	@Override
	public void dispose () {

	}
}
