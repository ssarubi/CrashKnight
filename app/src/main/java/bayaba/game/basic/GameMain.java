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

    public int DPTimer = 0;

    Font font = new Font();
    Font DP = new Font();

    Sprite backSpr = new Sprite();
    Sprite clothSpr = new Sprite();


    Sprite ratSpr = new Sprite();
    Sprite crabSpr = new Sprite();

    GameObject Stage = new GameObject();
    GameObject Hero = new GameObject();
    ArrayList<GameObject>Monster = new ArrayList<GameObject>();

    public void Stageinit(){
        GameObject temp = new GameObject();

        if(Stage.state >= 1){
            temp.SetObject(ratSpr, 0, 0, 440, 400, 0, 0);
            temp.mx1 = -1;
            temp.energy = 100;
            temp.damage = 5;
            temp.AddFrameLoop(0.2f);
            Monster.add(temp);
        }
        if(Stage.state >= 2){
            temp.SetObject(crabSpr, 0, 0, 450, 400, 0, 0);
            temp.mx1 = -1;
            temp.energy = 100;
            temp.damage = 5;
            temp.AddFrameLoop(0.2f);
            Monster.add(temp);

        }
    }



    public void DrawFont(){
        font.BeginFont(gInfo);
        font.LoadFont(MainContext, "HMKLP.TTF");
        font.DrawFontCenter(mGL, gInfo, 240, 170, 250, 250, 250, 30, "Stage " + String.valueOf(Stage.state));
        font.DrawFont(mGL, 30, 30, 15, "Hero Life : " + String.valueOf(Hero.energy));
        font.DrawFont(mGL, 30, 60, 15, "Hero Damage : " + String.valueOf(Hero.damage));
        font.EndFont(gInfo);
    }

    public void DamagePanel(){
        if(DPTimer > 0){
            DP.BeginFont(gInfo);
            DP.DrawFontCenter(mGL, gInfo, Stage.x, Stage.y, 0, 0, 0, 10, String.valueOf(Hero.damage));
            DP.EndFont(gInfo);
            Stage.y--;
        }
    }

    public void Crash(){

        for(int i = 0; i < Monster.size(); i++){
            if(gInfo.CrashCheck(Hero, Monster.get(i), 0, 0)){
                Hero.speed = -5;
                Monster.get(i).speed = 5;

                LifeCheck(Monster.get(i));
            }
        }

        for(int i = 0; i < Monster.size(); i++){
            if(Monster.get(i).speed > 0) Monster.get(i).speed -= Math.random()/5;
        }
        if(Hero.speed < 0) Hero.speed = Hero.speed * 0.95f;
        if(DPTimer > 0) DPTimer--;
    }

    public void LifeCheck(GameObject Monster){
        Hero.energy -= Monster.damage;
        Monster.energy -= Hero.damage;

        if(Hero.energy <= 0){
            // 패배처리
        }
        if(Monster.energy <= 0){
            Monster.dead = true;
        }

        DPTimer = 50;
        Stage.x = Monster.x;
        Stage.y = Monster.y-25;

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
        crabSpr.LoadSprite(mGL, MainContext, "crab.spr");

        Hero.SetObject(clothSpr, 0, 0, 40, 400, 0, 0);

        // 초기 데이터 세팅
        Stage.state = 1;

        Hero.mx1 = 1;
        Hero.energy = 1000;
        Hero.damage = 20;

        Stageinit();

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


            for(int i = 0; i < Monster.size(); i++){
                Monster.get(i).x += Monster.get(i).speed + Monster.get(i).mx1;
                Monster.get(i).DrawSprite(gInfo);
                if(Monster.get(i).dead) Monster.remove(i);
            }

            if(Stage.timer > 60){
                Log.d("Monster Size : ", String.valueOf(Monster.size()));
                Stage.timer = 0;
            }
                Stage.timer++;

            Crash();
            DrawFont();
            DamagePanel();

            if(Monster.size() == 0){
                Stage.state++;
                Stageinit();
            }
		}
	}
}
