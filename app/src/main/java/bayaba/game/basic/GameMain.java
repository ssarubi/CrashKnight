package bayaba.game.basic;

import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import bayaba.engine.lib.GameInfo;

public class GameMain
{
	public GL10 mGL = null; // OpenGL ��ü
	public Context MainContext;
	public Random MyRand = new Random(); // ���� �߻���
	public GameInfo gInfo; // ���� ȯ�� ������ Ŭ���� : MainActivity�� ����� ���� ���� �޴´�.
	public float TouchX, TouchY;
	
	public GameMain( Context context, GameInfo info ) // Ŭ���� ������ (���� ��Ƽ��Ƽ���� ȣ��)
	{
		MainContext = context; // ���� ���ؽ�Ʈ�� ������ �����Ѵ�.
		gInfo = info; // ���� ��Ƽ��Ƽ���� ������ Ŭ������ �����´�.
	}

	public void LoadGameData() // SurfaceClass���� OpenGL�� �ʱ�ȭ�Ǹ� ���ʷ� ȣ��Ǵ� �Լ�
	{
		// ���� �����͸� �ε��մϴ�.
	}
	
	public void PushButton( boolean push ) // OpenGL ȭ�鿡 ��ġ�� �߻��ϸ� GLView���� ȣ��ȴ�.
	{
		// ��ġ�� ó���մϴ�.
	}
	
	public void DoGame() // 1/60�ʿ� �ѹ��� SurfaceClass���� ȣ��ȴ�. ������ �ھ� �κ��� �ִ´�.
	{
		synchronized ( mGL )
		{
			// ������ �ھ� �κ��� �ڵ��մϴ�.
		}
	}
}
