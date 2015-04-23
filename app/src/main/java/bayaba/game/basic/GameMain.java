package bayaba.game.basic;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import bayaba.engine.lib.Font;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

public class GameMain
{
	public GL10 mGL = null; // OpenGL 객체
	public Context MainContext;
	public Random MyRand = new Random(); // 랜덤 발생기
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;

    Font font = new Font();

    Sprite backSpr = new Sprite();
    Sprite clothSpr = new Sprite();

    Sprite ratSpr = new Sprite();

    ArrayList<GameObject>Game = new ArrayList<GameObject>();

    GameObject Stage = new GameObject();
    GameObject Hero = new GameObject();
    GameObject Monster = new GameObject();


    public void DrawFont(){
        font.LoadFont(MainContext, "Hoon.ttf");
        font.BeginFont(gInfo);
        font.DrawColorFont(mGL, 30, 30, 255, 0, 0, 10, "Hero Life : "+String.valueOf(Hero.energy));
        font.DrawColorFont(mGL, 30, 60, 0, 0, 255, 10, "Hero Damage : "+String.valueOf(Hero.damage));
        font.DrawColorFont(mGL, 350, 30, 255, 0, 0, 10, "Hero Life : "+String.valueOf(Monster.energy));
        font.DrawColorFont(mGL, 350, 60, 0, 0, 255, 10, "Hero Damage : "+String.valueOf(Monster.damage));
        font.EndFont(gInfo);
    }

    public void Crash(){

        if(gInfo.CrashCheck(Hero, Monster, 0, 0)){
            Hero.speed = -5;
            Monster.speed = 5;
            LifeCheck();
        }

        if(Hero.speed < 0) Hero.speed = Hero.speed * 0.95f;
        if(Monster.speed > 0) Monster.speed -= Math.random()/5;

    }

    public void LifeCheck(){
        Hero.energy -= Monster.damage;
        Monster.energy -= Hero.damage;

        if(Hero.energy < 0){
            // 패배처리
        }
        if(Monster.energy < 0){
            Log.d("현재 Stage : ", String.valueOf(Stage.state));
            Stage.state++;
        }
    }

	public GameMain( Context context, GameInfo info ) // 클래스 생성자 (메인 액티비티에서 호출)
	{
		MainContext = context; // 메인 컨텍스트를 변수에 보관한다.
		gInfo = info; // 메인 액티비티에서 생성된 클래스를 가져온다.
	}

	public void LoadGameData() // SurfaceClass에서 OpenGL이 초기화되면 최초로 호출되는 함수
	{
		// 게임 데이터를 로드합니다.
        backSpr.LoadSprite(mGL, MainContext, "back.spr");
        clothSpr.LoadSprite(mGL, MainContext, "clotharmor.spr");

        ratSpr.LoadSprite(mGL, MainContext, "rat.spr");

        Hero.SetObject(clothSpr, 0, 0, 40, 400, 0, 0);
        Monster.SetObject(ratSpr, 0, 0, 440, 400, 0, 0);

        // 초기 데이터 세팅
        Stage.state = 1;
        Hero.mx1 = 1;
        Monster.mx1= -1;

        Hero.energy = 100;
        Monster.energy = 100;
        Hero.damage = 20;
        Monster.damage = 10;
    }
	
	public void PushButton( boolean push ) // OpenGL 화면에 터치가 발생하면 GLView에서 호출된다.
	{
		// 터치를 처리합니다.
	}
	
	public void DoGame() // 1/60초에 한번씩 SurfaceClass에서 호출된다. 게임의 코어 부분을 넣는다.
	{
		synchronized ( mGL )
		{
			// 게임의 코어 부분을 코딩합니다.
            backSpr.PutAni(gInfo, 240, 400, 0, 0);

            Hero.x += Hero.speed + Hero.mx1;
            Hero.DrawSprite(gInfo);
            Hero.AddFrameLoop(0.2f);

            Monster.x += Monster.speed + Monster.mx1;
            Monster.DrawSprite(gInfo);
            Monster.AddFrameLoop(0.2f);



            Crash();
            DrawFont();
		}
	}
}
