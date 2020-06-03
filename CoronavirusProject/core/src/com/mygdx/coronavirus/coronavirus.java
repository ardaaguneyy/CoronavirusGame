package com.mygdx.coronavirus;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

import sun.misc.SharedSecrets;

public class coronavirus extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background1;
	Texture background2;
	Texture background3;
	Texture background4;
	Texture coronavirus1;
	Texture coronavirus2;
	Texture coronavirus3;
	Texture coronavirus4;
	Texture player;
	//gamesettings
	float gravity = 0.20f;
	float velocity= 0;
	int gamestart=0;
	int score = 0 ;
	//player
	float playerx=0;
	float playery=0;
	//enemyrandomsy
	Random enemy1yrandom = new Random();
	Random enemy2yrandom = new Random();
	Random enemy3yrandom = new Random();
	Random enemy4random= new Random();
	//enemy
	float enemyx = 0;
	int enemy1y= 0 ;
	int enemy2y= 0 ;
	int enemy3y= 0 ;
	int enemy4y= 0;
	//circle
	 Circle playercircle;
	 Circle enemy1circle;
	 Circle enemy2circle;
	 Circle enemy3circle;
	 Circle enemy4circle;
	Preferences preferences;
	int maxscore = 0;
	CharSequence maxscorestr;
	BitmapFont maxscorefont;
	CharSequence scorestr;
	BitmapFont scorefont;
	ShapeRenderer shapeRenderer;
	@Override
	public void create () {
		batch = new SpriteBatch();
		background1 = new Texture("background1.png");
		background2= new Texture("background2.png");
		background3 = new Texture("background3.png");
		background4= new Texture("background4.png");
		coronavirus1= new Texture("coronavirus1.png");
		coronavirus2= new Texture("coronavirus1.png");
		coronavirus3 = new Texture("coronavirus1.png");
		coronavirus4 = new Texture("coronavirus1.png");
		//circle
		playercircle = new Circle();
		enemy1circle= new Circle();
		enemy2circle= new Circle();
		enemy3circle = new Circle();
		enemy4circle = new Circle();
		//player
		player = new Texture("player.png");
		playery=(float) Gdx.graphics.getHeight()/2;
		playerx= (float) Gdx.graphics.getWidth()/5;
		//enemy
		enemyx = Gdx.graphics.getWidth()-15;
		enemy1y = enemy1yrandom.nextInt(Gdx.graphics.getHeight()/2)+5;
		enemy2y = enemy2yrandom.nextInt(Gdx.graphics.getHeight());
		enemy3y= enemy3yrandom.nextInt(Gdx.graphics.getHeight()/2)+ Gdx.graphics.getHeight()/2;
		enemy4y= enemy4random.nextInt(Gdx.graphics.getHeight()/2)+25;
		//preferences
		preferences =  Gdx.app.getPreferences("com.mygdx.coronavirus");
		//score
		maxscore= preferences.getInteger("maxscore",0);
		maxscorefont= new BitmapFont();
		maxscorefont.getData().setScale(3);
		scorefont = new BitmapFont();
		scorefont.getData().setScale(3);
		shapeRenderer = new ShapeRenderer();
	}

	@Override
	public void render () {
		batch.begin();
		backgroundandlevelchange();
		batch.draw(player,playerx,playery,Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/10);
		playermove();
        playergameover();
		maxscore= preferences.getInteger("maxscore",0);
		maxscorestr = "Best Score:" + maxscore;
		maxscorefont.draw(batch,maxscorestr,Gdx.graphics.getWidth()-400,Gdx.graphics.getHeight()-15);
		//scoretable
		 scorestr = "Score:"+ score;
		scorefont.draw(batch,scorestr,0,Gdx.graphics.getHeight()-15);
		if (gamestart==1){

			enemydraw();
			velocity = velocity+gravity;
			playery = playery-velocity;
			score= score+1;
			//enemies
			if (enemyx<0){
				enemyx = Gdx.graphics.getWidth()-15;
				enemy1y = enemy1yrandom.nextInt(Gdx.graphics.getHeight()/2)+10;
				enemy2y = enemy2yrandom.nextInt(Gdx.graphics.getHeight()-10);
				enemy3y= enemy3yrandom.nextInt(Gdx.graphics.getHeight()/2)+ Gdx.graphics.getHeight()/2-10;
                enemy4y= enemy4random.nextInt(Gdx.graphics.getHeight()/2)+25;
			}

		}
		batch.end();

       //circle
		playercircle.set(playerx+Gdx.graphics.getWidth()/24,playery+Gdx.graphics.getHeight()/24,Gdx.graphics.getWidth()/26);
		enemy1circle.set(enemyx+Gdx.graphics.getWidth()/24,enemy1y+Gdx.graphics.getHeight()/24,Gdx.graphics.getWidth()/26);
		enemy2circle.set(enemyx+Gdx.graphics.getWidth()/24,enemy2y+Gdx.graphics.getHeight()/24,Gdx.graphics.getWidth()/26);
		enemy3circle.set(enemyx+Gdx.graphics.getWidth()/24,enemy3y+Gdx.graphics.getHeight()/24,Gdx.graphics.getWidth()/26);
        enemy4circle.set(enemyx+Gdx.graphics.getWidth()/24,enemy4y+Gdx.graphics.getHeight()/24,Gdx.graphics.getWidth()/26);
		enemycollisonplayer();

	}
	@Override
	public void dispose () {

	}
	private void backgroundandlevelchange(){
		if (score<500){
			batch.draw(background1,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
				if (gamestart==1) {
					enemyx = enemyx - 6;
				}

		}
		if (score>500 && score<1000){
			batch.draw(background2,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

				enemyx = enemyx- 7;


		}
		if (score>1000 && score<2000){
			batch.draw(background3,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

				enemyx = enemyx- 9;


		}
		if (score>2000&& score<2500){
			batch.draw(background1,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

				enemyx = enemyx- 12;


		}
		if (score>2500){
			batch.draw(background1,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

				enemyx= enemyx - 15;


		}

	}
	private void playermove(){
		if (Gdx.input.justTouched()){
			velocity = -7;
			gamestart=1;
		}
	}
	private  void playergameover(){
		if (playery<0 || playery>Gdx.graphics.getHeight()){
			if (maxscore<score){
				preferences.putInteger("maxscore",score).flush();
			}
			gamestart=0;
			score= 0;
			playery=(float) Gdx.graphics.getHeight()/2;
			playerx= (float) Gdx.graphics.getWidth()/5;
			enemy1y = enemy1yrandom.nextInt(Gdx.graphics.getHeight()/2);
			enemy2y = enemy2yrandom.nextInt(Gdx.graphics.getHeight());
			enemy3y= enemy3yrandom.nextInt(Gdx.graphics.getHeight()/2)+ Gdx.graphics.getHeight()/2;
			enemy4y = enemy4random.nextInt(Gdx.graphics.getHeight()/2)+25;
			enemyx = Gdx.graphics.getWidth()-15;

		}

	}
	private void enemydraw(){
		batch.draw(coronavirus1,enemyx,enemy1y,Gdx.graphics.getWidth()/12,Gdx.graphics.getHeight()/12);
		batch.draw(coronavirus2,enemyx,enemy2y,Gdx.graphics.getWidth()/12,Gdx.graphics.getHeight()/12);
		batch.draw(coronavirus3,enemyx,enemy3y,Gdx.graphics.getWidth()/12,Gdx.graphics.getHeight()/12);
		batch.draw(coronavirus4,enemyx,enemy4y,Gdx.graphics.getWidth()/12,Gdx.graphics.getHeight()/12);
	}
	private void enemycollisonplayer(){
		if (Intersector.overlaps(playercircle,enemy1circle)||Intersector.overlaps(playercircle,enemy2circle)||Intersector.overlaps(playercircle,enemy3circle) || Intersector.overlaps(playercircle,enemy4circle)){
			playery= -5;
		}

	}
}
