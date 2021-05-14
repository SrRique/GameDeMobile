package com.mygtx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
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
	private int pontos = 0;
	private int movimentaY = 0;
	private  int movimentaX = 0;

	//random usado nos canos
	private  Random random;

	//var usados para setar elemenos na tela
	private float larguraDispositivo;
	private  float alturaDispositivo;
	private  float espacoentrecanos;
	private  float posicaoCanohorizontal;
	private  float posicaocanovertical;


	//var usados na fisica
	private  float variacao = 0;
	private  int gravidade = 0;
	private  float posicaoInicialVerticalPassaro = 0;

	BitmapFont textoPontuacao;
	private  boolean passouCano = false;

	private ShapeRenderer shapeRenderer;
	private Circle circulopassaro;
	private Rectangle retanguloCanoCima;
	private Rectangle RetanguloCanoBaixo;

	@Override
	public void create () {
		inicializaImagens();
		inicializaTela();
	}

	private void inicializaTela() {

		batch = new SpriteBatch();

		//guardando as infos da resolução do aparelho em variaveis para definir o tamanho das texturas
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();
		//definindo a posicao inicial do passaro
		posicaoInicialVerticalPassaro = alturaDispositivo / 2;
		posicaoCanohorizontal = larguraDispositivo;
		espacoentrecanos = 350;

		textoPontuacao = new BitmapFont();
		textoPontuacao.setColor(Color.WHITE);
		textoPontuacao.getData().setScale(10);
	}

	private void inicializaImagens() {

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
		validarPontos();
		desenhaImagens();
		detectarColisao();
	}

	private void detectarColisao() {

		//setando colisores
		circulopassaro.set(50 + passaros[0].getWidth() / 2, posicaoInicialVerticalPassaro + passaros[0].getHeight() / 2, passaros[0].getWidth() / 2);

		retanguloCanoCima.set(posicaoCanohorizontal, alturaDispositivo / 2 - canotopo.getHeight() - espacoentrecanos / 2 + posicaocanovertical, canotopo.getWidth(), canotopo.getHeight() );

		RetanguloCanoBaixo.set(posicaoCanohorizontal, alturaDispositivo / 2 - canobaixo.getHeight() - espacoentrecanos / 2 + posicaocanovertical, canobaixo.getWidth(), canobaixo.getHeight());

		//detecta a colisao
		boolean colisaoCanoCima = Intersector.overlaps(circulopassaro, retanguloCanoCima);
		boolean colisaoCanoBaixo = Intersector.overlaps(circulopassaro, RetanguloCanoBaixo);

		//avisa se colidiu
		if (colisaoCanoBaixo || colisaoCanoCima)
		{
			Gdx.app.log("Log", "Bateu");
		}

	}


	private void gameplay() {

		//canos vem em direcao ao player
		posicaoCanohorizontal -= Gdx.graphics.getDeltaTime() * 200;
		if(posicaoCanohorizontal < - canobaixo.getWidth()){
			posicaoCanohorizontal = larguraDispositivo;
			posicaocanovertical = random.nextInt(400)-200;
			passouCano = false;
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
		batch.draw(passaros[(int) variacao],50, posicaoInicialVerticalPassaro);//instancia o passarinho

		//instancia o cano na tela e o espaçamento entre eles
		batch.draw( canobaixo, posicaoCanohorizontal , alturaDispositivo/2 - canobaixo.getHeight() - espacoentrecanos/2 + posicaocanovertical);
		batch.draw( canotopo, posicaoCanohorizontal ,alturaDispositivo/2 + espacoentrecanos / 2 + posicaocanovertical);

		textoPontuacao.draw(batch, String.valueOf(pontos), larguraDispositivo / 2, alturaDispositivo - 100);

		//para a execucao
		batch.end();
	}

	private void validarPontos() {
		if (posicaocanovertical < 50 - passaros[0].getWidth())
		{
			if (!passouCano){
				pontos++;
				passouCano = true;
			}
		}
	}



	@Override
	public void dispose () {

	}
}
