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

    float damagecost = 100;
    float energycost = 1000;
    float delaycost = 1000;
    float directcost = 1000;
    float currentcost = 100000;

    Font font = new Font();
    Font DP = new Font();
    Font MonLife = new Font();
    Font Message = new Font();

    Sprite backSpr = new Sprite();
    Sprite buttonSpr = new Sprite();
    Sprite LifeSpr = new Sprite();

    Sprite clothSpr = new Sprite();
    Sprite leatherSpr = new Sprite();
    Sprite mailSpr = new Sprite();
    Sprite plateSpr = new Sprite();
    Sprite redSpr = new Sprite();
    Sprite goldSpr = new Sprite();

    Sprite ratSpr = new Sprite();
    Sprite crabSpr = new Sprite();
    Sprite snakeSpr = new Sprite();
    Sprite goblinSpr = new Sprite();
    Sprite eyeSpr = new Sprite();
    Sprite firefoxSpr = new Sprite();
    Sprite skeletonSpr = new Sprite();
    Sprite skeleton2Spr = new Sprite();
    Sprite ogreSpr = new Sprite();
    Sprite wizardSpr = new Sprite();
    Sprite spectreSpr = new Sprite();
    Sprite deathSpr = new Sprite();
    Sprite bossSpr = new Sprite();

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


        for(int i = 0; i < Math.round(Stage.state / 5) + 1; i++){
            float seq = Math.round(Math.random() * 13 + 1);
            temp = new GameObject();
            if(seq == 1) temp.SetObject(ratSpr, 0, 0, 450, 475, 0, 0);
            else if(seq == 2) temp.SetObject(crabSpr, 0, 0, 450, 475, 0, 0);
            else if(seq == 3) temp.SetObject(snakeSpr, 0, 0, 450, 475, 0, 0);
            else if(seq == 4) temp.SetObject(goblinSpr, 0, 0, 450, 475, 0, 0);
            else if(seq == 5) temp.SetObject(eyeSpr, 0, 0, 450, 475, 0, 0);
            else if(seq == 6) temp.SetObject(firefoxSpr, 0, 0, 450, 475, 0, 0);
            else if(seq == 7) temp.SetObject(skeletonSpr, 0, 0, 450, 475, 0, 0);
            else if(seq == 8) temp.SetObject(skeleton2Spr, 0, 0, 450, 475, 0, 0);
            else if(seq == 9) temp.SetObject(ogreSpr, 0, 0, 450, 475, 0, 0);
            else if(seq == 10) temp.SetObject(wizardSpr, 0, 0, 450, 475, 0, 0);
            else if(seq == 11) temp.SetObject(spectreSpr, 0, 0, 450, 475, 0, 0);
            else if(seq == 12) temp.SetObject(deathSpr, 0, 0, 450, 475, 0, 0);
            else if(seq == 13) temp.SetObject(bossSpr, 0, 0, 450, 475, 0, 0);
            else temp.SetObject(ratSpr, 0, 0, 450, 475, 0, 0);

            if(seq == 2 || seq == 3 || seq == 4 || seq == 8 || seq == 10 || seq == 11 || seq == 12 || seq == 13) temp.flip = true;

            temp.subfr = -0.2f;
            temp.subfr -= Math.random();
            temp.energy = (Stage.state * Stage.state - 1) * i + i * i + 10;
            temp.state = (Stage.state * Stage.state - 1) * i + i * i + 10;
            temp.damage = Stage.state + i;
            temp.layer = 0.2f;

            Monster.add(temp);

            MakeLifeBar();
        }

    }

    public void DrawButton(){

        // 공격력
        if(Button.get(0).click == ButtonType.STATE_CLK_BUTTON){
            if(Stage.mx1 > damagecost){
                Stage.mx1 -= damagecost;
                damagecost = damagecost * 1.05f;
                Hero.damage++;

                Stage.x = 240;
                Stage.y = 350;
                Stage.atimer = 60;
                msg = "공격력 증가 !!";
            } else{
                Stage.x = 240;
                Stage.y = 350;
                Stage.atimer = 60;
                msg = "경험치가 부족합니다";
            }
        }
        // 체력
        if(Button.get(1).click == ButtonType.STATE_CLK_BUTTON){
            if(Stage.mx1 > energycost){
                Stage.mx1 -= energycost;
                energycost = energycost * 1.05f;
                Hero.energy = Hero.energy + 50;
                Hero.state = Hero.state + 50;

                Stage.x = 240;
                Stage.y = 350;
                Stage.atimer = 60;
                msg = "체력 증가 !!";
            } else{
                Stage.x = 240;
                Stage.y = 350;
                Stage.atimer = 60;
                msg = "경험치가 부족합니다";
            }
        }
        // 체력재생
        if(Button.get(2).click == ButtonType.STATE_CLK_BUTTON){
            if(Stage.mx1 > delaycost){
                Stage.mx1 -= delaycost;
                delaycost = delaycost * 1.05f;
                Hero.delay++;

                Stage.x = 240;
                Stage.y = 350;
                Stage.atimer = 60;
                msg = "회복력 증가 !!";
            } else{
                Stage.x = 240;
                Stage.y = 350;
                Stage.atimer = 60;
                msg = "경험치가 부족합니다";
            }
        }
        // 크리티컬 확률
        if(Button.get(3).click == ButtonType.STATE_CLK_BUTTON){
            if(Stage.mx1 > directcost){
                Stage.mx1 -= directcost;
                directcost = directcost * 1.05f;
                Hero.direct++;

                Stage.x = 240;
                Stage.y = 350;
                Stage.atimer = 60;
                msg = "크리티컬 확률 증가 !!";
            } else{
                Stage.x = 240;
                Stage.y = 350;
                Stage.atimer = 60;
                msg = "경험치가 부족합니다";
            }
        }
        // 크리티컬 배수
        if(Button.get(4).click == ButtonType.STATE_CLK_BUTTON){
            if(Stage.mx1 > currentcost){
                Stage.mx1 -= currentcost;
                currentcost = currentcost * 1.05f;
                Hero.current++;

                Stage.x = 240;
                Stage.y = 350;
                Stage.atimer = 60;
                msg = "크리티컬 배수 증가 !!";
            } else{
                Stage.x = 240;
                Stage.y = 350;
                Stage.atimer = 60;
                msg = "경험치가 부족합니다";
            }
        }

        // Active Skill
        if(Button.get(4).click == ButtonType.STATE_CLK_BUTTON){
            Hero.energy = Hero.energy / 2;

            float Monx = 480;
            int seq = 0;

            for(int i = 0; i < Monster.size(); i++){
                if(Monster.get(i).x < Monx){
                    Monx = Monster.get(i).x;
                    seq = i;
                }
            }


            Stage.atimer = 30;
            msg = "Skill !!";
            DrawMessage(Hero.x, Hero.y - 50, 12, msg, Stage.atimer);
        }

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

    public void LifeRezen(){
        if(Stage.attack > 300){
            Stage.attack = 0;
            Hero.horizon = 0;
            Hero.mx2 = Math.round(Hero.x);
            Hero.my2 = Math.round(Hero.y)-25;
            Hero.energy += Hero.delay;
        }
        Stage.attack++;
        if(Hero.energy > Hero.state) Hero.energy = Hero.state;
    }

    public void DrawFont(){
        font.BeginFont(gInfo);
        font.LoadFont(MainContext, "HMKLP.TTF");
        font.DrawFontCenter(mGL, gInfo, 240, 200, 250, 250, 250, 30, "Stage " + String.valueOf(Stage.state));

        // Hero
        font.DrawFont(mGL, 30, 30, 20, "체력 : " + String.valueOf(Math.round(Hero.energy)) + " / " + String.valueOf(Math.round(Hero.state)));
        font.DrawFont(mGL, 30, 60, 20, "공격력 : " + String.valueOf(Hero.damage));
        font.DrawFont(mGL, 30, 90, 20, "크리티컬 확률 : " + String.valueOf(Hero.direct));
        font.DrawFont(mGL, 30, 120, 20, "크리티컬 배수 : " + String.valueOf(Hero.current));
        font.DrawFont(mGL, 30, 150, 20, "체력 회복량 : " + String.valueOf(Hero.delay));
        font.DrawFont(mGL, 370, 30, 20, "Exp : " + String.valueOf(Stage.mx1));
        font.DrawFont(mGL, 370, 60, 20, "Income : " + String.valueOf(Stage.mx2));

        // Button
        font.DrawFont(mGL, 80, 600, 15, "공격력 증가 : " + String.valueOf(Math.round(damagecost)));
        font.DrawFont(mGL, 80, 660, 15, "체력 증가 : " + String.valueOf(Math.round(energycost)));
        font.DrawFont(mGL, 80, 720, 15, "회복력 증가 : " + String.valueOf(Math.round(delaycost)));
        font.DrawFont(mGL, 330, 600, 15, "크리티컬 확률 증가 : " + String.valueOf(Math.round(directcost)));
        font.DrawFont(mGL, 330, 660, 15, "크리티컬 배수 증가 : " + String.valueOf(Math.round(currentcost)));

        font.DrawFont(mGL, 330, 720, 15, "Skill");
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

        // 공격력 패널
        if(Hero.atimer < 50){
            DP.DrawFontCenter(mGL, gInfo, Hero.mx1, Hero.my1, 255, 0, 0, 9, String.valueOf(Hero.crash));
            Hero.atimer++;
        }

        // 체력회복 패널
        if(Hero.horizon < 50){
            DP.DrawFontCenter(mGL, gInfo, Hero.mx2, Hero.my2, 0, 255, 0, 9, String.valueOf(Hero.delay));
            Hero.horizon++;
        }

        DP.EndFont(gInfo);
    }


    public void CrashCheck(){

        for(int i = 0; i < Monster.size(); i++){
            if(gInfo.CrashCheck(Hero, Monster.get(i), 0, 0)){
                Hero.speed = -5;
                Monster.get(i).speed = 5;

                Monster.get(i).atimer = 0;
                Monster.get(i).mx1 = Math.round(Monster.get(i).x);
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

        if(Hero.energy <= 0) Hero.dead = true;
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

        if(Hero.dead){
            Stage.state = 0;
            Monster.clear();
            Lifebar.clear();

            Hero.energy = Hero.state;
            Hero.dead = false;
            Hero.x = 30;

            Stage.x = 240;
            Stage.y = 280;
            Stage.atimer = 60;
            msg = "Stage Fail !!!!!!";
        }
    }

    public void DrawLifebar(){
        for(int i = 0; i < Lifebar.size(); i++){
            Lifebar.get(i).x = Monster.get(i).x;
            Lifebar.get(i).y = Monster.get(i).y-25;
            Lifebar.get(i).DrawSprite(mGL, 0, gInfo, font);
            Lifebar.get(i).energy = (Monster.get(i).energy / Monster.get(i).state) * 100;
            MonLife.BeginFont(gInfo);
            MonLife.DrawFontCenter(mGL, gInfo, Monster.get(i).x, Monster.get(i).y - 35, 0, 0, 0, 7, String.valueOf(Math.round(Monster.get(i).energy)) + " / " + String.valueOf(Monster.get(i).state));
            MonLife.EndFont(gInfo);
        }
    }

    public void ExpIncrease(){
        if(Stage.timer > 60){
            Stage.mx1 += Stage.mx2;
            Stage.timer = 0;
        }
        Stage.timer++;
    }

    public void DrawMessage(float x, float y, float size, String text, long timer){
        if(timer > 0){
            Message.BeginFont(gInfo);
            Message.DrawFontCenter(mGL, gInfo, x, y, 0, 0, 0, size, text);
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
        leatherSpr.LoadSprite(mGL, MainContext, "leatherarmor.spr");
        mailSpr.LoadSprite(mGL, MainContext, "mailarmor.spr");
        plateSpr.LoadSprite(mGL, MainContext, "platearmor.spr");
        redSpr.LoadSprite(mGL, MainContext, "redarmor.spr");
        goldSpr.LoadSprite(mGL, MainContext, "goldenarmor.spr");

        ratSpr.LoadSprite(mGL, MainContext, "rat.spr");
        crabSpr.LoadSprite(mGL, MainContext, "crab.spr");
        snakeSpr.LoadSprite(mGL, MainContext, "snake.spr");
        goblinSpr.LoadSprite(mGL, MainContext, "goblin.spr");
        eyeSpr.LoadSprite(mGL, MainContext, "eye.spr");
        firefoxSpr.LoadSprite(mGL, MainContext, "firefox.spr");
        skeletonSpr.LoadSprite(mGL, MainContext, "skeleton.spr");
        skeleton2Spr.LoadSprite(mGL, MainContext, "skeleton2.spr");
        ogreSpr.LoadSprite(mGL, MainContext, "ogre.spr");
        wizardSpr.LoadSprite(mGL, MainContext, "wizard.spr");
        spectreSpr.LoadSprite(mGL, MainContext, "spectre.spr");
        deathSpr.LoadSprite(mGL, MainContext, "deathknight.spr");
        bossSpr.LoadSprite(mGL, MainContext, "boss.spr");

        Hero.SetObject(goldSpr, 0, 0, 40, 475, 0, 0);

        // 초기 데이터 세팅
        Stage.state = 1;
        // mx1 Exp mx2 income
        Stage.mx1 = 1;
        Stage.mx2 = 100;

        Hero.subfr = 1;
        Hero.energy = 100;
        Hero.state = 100;
        Hero.damage = 1;
        Hero.current = 2;
        Hero.direct = 1;
        Hero.delay = 1;

        // 버튼 세팅
        ButtonObject temp;
        temp = new ButtonObject();
        temp.SetButton(buttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 40, 610, 0);
        Button.add(temp);
        temp = new ButtonObject();
        temp.SetButton(buttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 40, 670, 0);
        Button.add(temp);
        temp = new ButtonObject();
        temp.SetButton(buttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 40, 730, 0);
        Button.add(temp);
        temp = new ButtonObject();
        temp.SetButton(buttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 270, 610, 0);
        Button.add(temp);
        temp = new ButtonObject();
        temp.SetButton(buttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 270, 670, 0);
        Button.add(temp);
        temp = new ButtonObject();
        temp.SetButton(buttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 270, 730, 0);
        Button.add(temp);



        Stageinit();

    }
	
	public void PushButton( boolean push ) // OpenGL 화면에 터치가 발생하면 GLView에서 호출된다.
	{
		// 터치를 처리합니다.
        for ( int i = 0; i < Button.size(); i++ )Button.get(i).CheckButton( gInfo, push, TouchX, TouchY );
    }
	
	public void DoGame() // 1/60초에 한번씩 SurfaceClass에서 호출된다. 게임의 코어 부분을 넣는다.
	{
		synchronized ( mGL )
		{
			// 게임의 코어 부분을 코딩합니다.
            if(Monster.size() == 0){
                Stage.mx2 += Stage.state;
                Stage.x = 240;
                Stage.y = 280;
                Stage.atimer = 60;
                msg = "Stage " + Stage.state + " Clear";
                Stage.state++;
                Stageinit();
            }

            backSpr.PutAni(gInfo, 240, 400, 0, 0);

            Hero.x += Hero.speed + Hero.subfr;
            Hero.DrawSprite(gInfo);
            Hero.AddFrameLoop(0.2f);

            DrawButton();
            ExpIncrease();
            CrashCheck();
            MakeMonster();
            DrawLifebar();
            MakeDamagePanel();
            DrawFont();
            LifeRezen();


            DrawMessage(Stage.x, Stage.y, 18, msg, Stage.atimer);

		}
	}
}
