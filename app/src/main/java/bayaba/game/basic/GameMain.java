package bayaba.game.basic;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import bayaba.engine.lib.ButtonObject;
import bayaba.engine.lib.ButtonType;
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

    String msg = "";

    Font font = new Font();
    Font DP = new Font();
    Font Message = new Font();

    Sprite backSpr = new Sprite();
    Sprite buttonSpr = new Sprite();
    Sprite LifeSpr = new Sprite();

    Sprite clothSpr = new Sprite();

    Sprite ratSpr = new Sprite();
    Sprite crabSpr = new Sprite();
    Sprite snakeSpr = new Sprite();
    Sprite goblinSpr = new Sprite();

    GameObject Stage = new GameObject();
    GameObject Hero = new GameObject();
    ArrayList<GameObject>Monster = new ArrayList<GameObject>();
    ArrayList<ButtonObject>Lifebar = new ArrayList<ButtonObject>();
    ArrayList<ButtonObject>Button = new ArrayList<ButtonObject>();

    public void Stageinit(){
        GameObject temp;

        Monster.clear();
        Lifebar.clear();

        Log.d("Stage Clear !! Stage ", String.valueOf(Stage.state));

        // 히어로 세팅





        // 몬스터 세팅
        if(Stage.state >= 1){
            temp = new GameObject();
            temp.SetObject(ratSpr, 0, 0, 400, 400, 0, 0);
            temp.subfr = -0.9f;
            temp.energy = 50;
            temp.state = 50;
            temp.damage = 1;
            temp.layer = 0.2f;
            Monster.add(temp);

            MakeLifeBar();
        }

        if(Stage.state >= 2){
            temp = new GameObject();
            temp.SetObject(crabSpr, 0, 0, 405, 400, 0, 0);
            temp.subfr = -0.7f;
            temp.energy = 100;
            temp.state = 100;
            temp.damage = 2;
            temp.layer = 0.3f;
            temp.flip = true;
            Monster.add(temp);

            MakeLifeBar();
        }

        if(Stage.state >= 3){
            temp = new GameObject();
            temp.SetObject(snakeSpr, 0, 0, 410, 400, 0, 0);
            temp.subfr = -1.2f;
            temp.energy = 250;
            temp.state = 250;
            temp.damage = 3;
            temp.layer = 0.2f;
            temp.flip = true;
            Monster.add(temp);

            MakeLifeBar();
        }

        if(Stage.state >= 4){
            temp = new GameObject();
            temp.SetObject(goblinSpr, 0, 0, 415, 400, 0, 0);
            temp.subfr = -1.0f;
            temp.energy = 500;
            temp.state = 500;
            temp.damage = 5;
            temp.layer = 0.2f;
            temp.flip = true;
            Monster.add(temp);

            MakeLifeBar();
        }

        if(Stage.state >= 5){
            temp = new GameObject();
            temp.SetObject(goblinSpr, 0, 0, 420, 400, 0, 0);
            temp.subfr = -1.0f;
            temp.energy = 500;
            temp.state = 500;
            temp.damage = 5;
            temp.layer = 0.2f;
            temp.flip = true;
            Monster.add(temp);

            MakeLifeBar();
        }

    }

    public void DrawButton(){
        for(int i = 0; i < Button.size(); i++){

            if ( Button.get(i).type == ButtonType.TYPE_ONE_CLICK ) // 버튼 타입인지 체크한다.
            {
                if ( Button.get(i).click == ButtonType.STATE_CLK_BUTTON ) // 버튼이 1회 눌렸는지 체크한다. 눌렀다가 떼졌을때 STATE_CLK_BUTTON이 된다.
                {
                    Button.get(i).ResetButton(); // 버튼 상태를 리셋해서 STATE_CLK_NORMAL로 변경한다. 다시 버튼을 누를 수 있는 상태가 된다.
                }
            }

            Button.get(i).DrawSprite(mGL, 0, gInfo, font);

        }
    }

    public void MakeLifeBar(){
        ButtonObject Lifetemp = new ButtonObject();
        Lifetemp.SetButton(LifeSpr, ButtonType.TYPE_PROGRESS, 0, 0, 0, 0);
        Lifebar.add(Lifetemp);
    }

    public void DrawFont(){
        font.BeginFont(gInfo);
        font.LoadFont(MainContext, "HMKLP.TTF");
        font.DrawFontCenter(mGL, gInfo, 240, 170, 250, 250, 250, 30, "Stage " + String.valueOf(Stage.state));
        font.DrawFont(mGL, 30, 25, 15, "체력 : " + String.valueOf(Math.round(Hero.energy)));
        font.DrawFont(mGL, 30, 50, 15, "공격력 : " + String.valueOf(Hero.damage));
        font.DrawFont(mGL, 30, 75, 15, "크리티컬 확률 : " + String.valueOf(Hero.direct));
        font.DrawFont(mGL, 30, 100, 15, "크리티컬 배수 : " + String.valueOf(Hero.current));
        font.DrawFont(mGL, 370, 25, 15, "Gold : " + String.valueOf(Stage.mx1));
        font.DrawFont(mGL, 370, 50, 15, "Income : " + String.valueOf(Stage.mx2));
        font.EndFont(gInfo);
    }

    public void MakeDamagePanel(){
        DP.BeginFont(gInfo);

        for(int i = 0; i < Monster.size(); i++) {

            if (Monster.get(i).jump == 'Y') {
                if (Monster.get(i).atimer < 50) {
                    DP.DrawFontCenter(mGL, gInfo, Monster.get(i).mx1, Monster.get(i).my1, 100, 0, 200, 9, "Cri "+String.valueOf(Hero.damage * Hero.current));
                    Monster.get(i).my1--;
                    Monster.get(i).atimer++;
                }
            } else {
                if (Monster.get(i).atimer < 50) {
                    DP.DrawFontCenter(mGL, gInfo, Monster.get(i).mx1, Monster.get(i).my1, 0, 0, 255, 9, String.valueOf(Hero.damage));
                    Monster.get(i).my1--;
                    Monster.get(i).atimer++;
                }
            }
        }

        if(Hero.atimer < 50){
            DP.DrawFontCenter(mGL, gInfo, Hero.mx1, Hero.my1, 255, 0, 0, 9, String.valueOf(Hero.crash));
            Hero.atimer++;
        }

        DP.EndFont(gInfo);
    }


    public void CrashCheck(){

        for(int i = 0; i < Monster.size(); i++){
            if(gInfo.CrashCheck(Hero, Monster.get(i), 0, 0)){
                Hero.speed = -5;
                Monster.get(i).speed = 5;

                Monster.get(i).atimer = 0;
                Monster.get(i).mx1 = Math.round(Monster.get(i).x)+5;
                Monster.get(i).my1 = Math.round(Monster.get(i).y)-20;

                Hero.atimer = 0;
                Hero.mx1 = Math.round(Hero.x)-5;
                Hero.my1 = Math.round(Hero.y)-25;
                Hero.crash = Monster.get(i).damage;

                if(Math.random() * 100 < Hero.direct) Monster.get(i).jump = 'Y';
                else Monster.get(i).jump = 'N';

                LifeCheck(Monster.get(i));
            }
        }

        for(int i = 0; i < Monster.size(); i++){
            if(Monster.get(i).speed > 0) Monster.get(i).speed -= Math.random()/5;
            if(Monster.get(i).x > 450) Monster.get(i).speed = 0;
        }
        if(Hero.speed < 0) Hero.speed = Hero.speed * 0.95f;
        if(Hero.x < 30) Hero.speed = 0;
    }

    public void LifeCheck(GameObject Monster){

        if(Monster.jump == 'Y'){
            Hero.energy -= Monster.damage;
            Monster.energy -= Hero.damage * Hero.current;
        } else {
            Hero.energy -= Monster.damage;
            Monster.energy -= Hero.damage;
        }

        if(Hero.energy <= 0){
            // 패배처리 처음부터 시작
            Stage.state = 1;
        }
        if(Monster.energy <= 0) Monster.dead = true;
    }

    public void MakeMonster(){
        for(int i = 0; i < Monster.size(); i++){
            Monster.get(i).x += Monster.get(i).speed + Monster.get(i).subfr;
            Monster.get(i).DrawSprite(gInfo);
            Monster.get(i).AddFrameLoop(Monster.get(i).layer);
        }
        for(int i = 0; i < Monster.size(); i++){
            if(Monster.get(i).dead){
                Monster.remove(i);
                Lifebar.remove(i);
            }
        }
    }

    public void DrawLifebar(){
        for(int i = 0; i < Lifebar.size(); i++){
            Lifebar.get(i).x = Monster.get(i).x;
            Lifebar.get(i).y = Monster.get(i).y-25;
            Lifebar.get(i).DrawSprite(mGL, 0, gInfo, font);
            Lifebar.get(i).energy = (Monster.get(i).energy / Monster.get(i).state) * 100;
        }
    }

    public void GoldIncrease(){
        if(Stage.timer > 60){
            Stage.mx1 += Stage.mx2;
            Stage.timer = 0;
        }
        Stage.timer++;
    }

    public void DrawMessage(float x, float y, float size, String text, long timer){
        if(timer > 0){
            Message.BeginFont(gInfo);
            Message.DrawFontCenter(mGL, gInfo, x, y, 5, 5, 5, size, text);
            Message.EndFont(gInfo);
            Stage.atimer--;
        }
    }

	public GameMain( Context context, GameInfo info ) // 클래스 생성자 (메인 액티비티에서 호출)
	{
		MainContext = context; // 메인 컨텍스트를 변수에 보관한다.
		gInfo = info; // 메인 액티비티에서 생성된 클래스를 가져온다.
	}

	public void LoadGameData() // SurfaceClass에서 OpenGL이 초기화되면 최초로 호출되는 함수
	{
		// 데이터를 로드합니다.
        backSpr.LoadSprite(mGL, MainContext, "back.spr");
        buttonSpr.LoadSprite(mGL, MainContext, "button.spr");
        LifeSpr.LoadSprite(mGL, MainContext, "Lifebar.spr");

        clothSpr.LoadSprite(mGL, MainContext, "clotharmor.spr");

        ratSpr.LoadSprite(mGL, MainContext, "rat.spr");
        crabSpr.LoadSprite(mGL, MainContext, "crab.spr");
        snakeSpr.LoadSprite(mGL, MainContext, "snake.spr");
        goblinSpr.LoadSprite(mGL, MainContext, "goblin.spr");

        Hero.SetObject(clothSpr, 0, 0, 40, 400, 0, 0);

        // 초기 데이터 세팅
        Stage.state = 1;
        Stage.mx1 = 1;
        Stage.mx2 = 1;

        Hero.subfr = 1;
        Hero.energy = 1000;
        Hero.damage = 25;
        Hero.current = 2;
        Hero.direct = 50;

        // 버튼 세팅
        ButtonObject temp;
        temp = new ButtonObject();
        temp.SetButton(buttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 30, 400, 0);

        Button.add(temp);

        Stageinit();

    }
	
	public void PushButton( boolean push ) // OpenGL 화면에 터치가 발생하면 GLView에서 호출된다.
	{
		// 터치를 처리합니다.
        for ( int i = 0; i < Button.size(); i++ ) Button.get(i).CheckButton( gInfo, push, TouchX, TouchY );
    }
	
	public void DoGame() // 1/60초에 한번씩 SurfaceClass에서 호출된다. 게임의 코어 부분을 넣는다.
	{
		synchronized ( mGL )
		{
			// 게임의 코어 부분을 코딩합니다.
            if(Monster.size() == 0){
                Stage.mx2 += Stage.state;
                Stage.x = 240;
                Stage.y = 250;
                Stage.atimer = 120;
                msg = "Stage Clear";
                Stage.state++;
                Stageinit();
            }

            backSpr.PutAni(gInfo, 240, 400, 0, 0);

            Hero.x += Hero.speed + Hero.subfr;
            Hero.DrawSprite(gInfo);
            Hero.AddFrameLoop(0.2f);

            DrawButton();
            GoldIncrease();
            CrashCheck();
            MakeMonster();
            DrawLifebar();
            MakeDamagePanel();
            DrawFont();
            DrawMessage(Stage.x, Stage.y, 20, msg, Stage.atimer);


		}
	}
}
