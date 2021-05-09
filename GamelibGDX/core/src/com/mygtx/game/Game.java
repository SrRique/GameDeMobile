package com.mygtx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import javax.swing.JTextArea;

public class Game extends ApplicationAdapter {

	//vars para as texturas serem renderizadas
	private  SpriteBatch batch;
	private  Texture[] passaros;
	private Texture fundo;
	private  Texture canobaixo;
	private  Texture canotopo;


	//var para movimentacao
	private int movimentaY = 0;
	private  int movimentaX = 0;

	//random usado nos canos
	private  Random random;

	//var usados para setar elemenos na tela
	private float larguraDispositivo;
	private  float alturaDispositivo;
	private  float posicaoCanohorizontal = 0;
	private  float espacoentrecanos;
	private  float posicaocanovertical;

	//var usados na fisica
	private  float variacao = 0;
	private  float gravidade = 0;
	private  float posicaoInicialVerticalPassaro = 0;

	@Override
	public void create () {
		inicializaImagens();
		inicializaTela();
	}

	private void inicializaTela() {
		//guardando as infos da resolução do aparelho em variaveis para definir o tamanho das texturas
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		//definindo a posicao inicial do passaro
		posicaoInicialVerticalPassaro = alturaDispositivo / 2;
		posicaoCanohorizontal = larguraDispositivo;
		espacoentrecanos = 150;
	}

	private void inicializaImagens() {
		batch = new SpriteBatch();
		random = new Random();

		//guardando as texturas nas var
		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		canotopo = new Texture("cano_topo_maior.png");
		canobaixo = new Texture("cano_baixo_maior.png");

		//instancia background
		fundo = new Texture("fundo.png");

	}

	@Override
	//renderiza os objetos na aplicação
	public void render () {

		gameplay();
		desenhaImagens();
	}

	private void gameplay() {

		//canos vem em direcao ao player
		posicaoCanohorizontal -= Gdx.graphics.getDeltaTime() * 200;
		if(posicaoCanohorizontal < - canobaixo.getWidth()){
			posicaoCanohorizontal = larguraDispositivo;
			posicaoCanohorizontal = random.nextInt(400)-200;

		}
		//detecta o toque na tela
		boolean toqueTela = Gdx.input.justTouched();
		//ao tocar na tela faz o passaro subir
		if(Gdx.input.justTouched()){
			gravidade = -25;
		}
		if(posicaoInicialVerticalPassaro > 0 || toqueTela)
			posicaoInicialVerticalPassaro = posicaoInicialVerticalPassaro - gravidade;
		//faz a animacao do passaro
		variacao += Gdx.graphics.getDeltaTime() * 10;

		//faz o loop de animação
		if(variacao > 3)
			variacao = 0;

		//aumenta a gravidade e faz o movimento em X
		gravidade++;
		movimentaX++;
	}

	private void desenhaImagens() {
		//começa a execução
		batch.begin();
		batch.draw(fundo,0,0,larguraDispositivo,alturaDispositivo);//instancia o fundo, usando os parametros criados antes como altura e largura
		batch.draw(passaros[(int) variacao],30, posicaoInicialVerticalPassaro);//instancia o passarinho

		//instancia o cano na tela e o espaçamento entre eles
		batch.draw( canobaixo, posicaoCanohorizontal -100, alturaDispositivo/2 - canobaixo.getHeight() - espacoentrecanos/2 + posicaocanovertical);
		batch.draw( canotopo, posicaoCanohorizontal -100,alturaDispositivo/2 + espacoentrecanos + posicaocanovertical);

		//para a execucao
		batch.end();
	}


	@Override
	public void dispose () {

	}
}
