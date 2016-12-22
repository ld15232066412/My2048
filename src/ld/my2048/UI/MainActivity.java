package ld.my2048.UI;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import ld.my2048.console.Maps;
import ld.my2048.inter.Direction;

public class MainActivity extends Activity implements OnClickListener{
	private GridLayout gridrLayout;
	private float startX = 0, startY = 0,endX, endY;
	private Maps maps = new Maps();
	private TextView score,best;
	private Button BtnReSet;
	
	int width;
	int height;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏  
		setContentView(R.layout.mian);
		getWidth();
		initUI();
	}

	private void getWidth(){
		WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		width = wm.getDefaultDisplay().getWidth();
		height = wm.getDefaultDisplay().getHeight();
	}

	//初始化页面
	private void initUI() {
		gridrLayout = (GridLayout) findViewById(R.id.root);
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				Button btn = new Button(this);
				btn.setClickable(false);
				btn.setText("");
				btn.setTextSize(30);
				btn.setWidth(width/4);
				btn.setHeight(width/4);

				GridLayout.Spec rowSpec = GridLayout.spec(i+2);
				GridLayout.Spec columSpec = GridLayout.spec(j);
				//				String msg = "rowSpec"+(i+2)+"-columSpec"+j;
				GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec,columSpec);
				gridrLayout.addView(btn, params);
				maps.addButton(i, j, btn);
			}
		}
		score = (TextView) findViewById(R.id.score);
		score.setText("0");
		best = (TextView) findViewById(R.id.best);
		BtnReSet = (Button) findViewById(R.id.reset);
		BtnReSet.setOnClickListener(this);
		maps.setScore(score);
		maps.setBest(best);
		maps.init();
	}

	//滑动监听
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		if(action == MotionEvent.ACTION_DOWN){
			startX = ev.getX();
			startY = ev.getY();
		}else if(action == MotionEvent.ACTION_UP){
			endX = ev.getX();
			endY = ev.getY();
			int direction = GetSlideDirection(startX, startY, endX, endY);
			boolean gameOver = maps.slide(direction);
			if(gameOver){
				if(maps.getScore()>maps.getBestScore()){
					Toast.makeText(MainActivity.this, "最佳记录", 0).show();
					maps.setBestScore(maps.getScore());
					best.setText(maps.getScore()+"");
				}else {
					Toast.makeText(MainActivity.this, "游戏结束", 0).show();
				}
			}
		}
		return super.dispatchTouchEvent(ev);
	}

	//根据起点和终点返回方向，1：向上，2向下，3向左，4向右，5未滑动
	private int GetSlideDirection(float startX, float startY,float endX,float endY){
		float dy = startY - endY;
		float dx = endX - startX;
		int result = Direction.NOLL;
		//滑动距离过短
		if(Math.abs(dx)<2&&Math.abs(dy)<2){
			return result;
		}
		double angle = GetSlideAngle(dx, dy);
		if(angle >= -45 && angle < 45){
			return Direction.RIGHT;
		}else if(angle >= 45 && angle < 135){
			return Direction.UP;
		}else if(angle >= -135 && angle < -45){
			return Direction.DOWN;
		}else if((angle >= 135 && angle <= 180)||(angle >= -180 && angle < -135)){
			return Direction.LEFT;
		}
		return result;
	}
	//
	private double GetSlideAngle(float dx, float dy){
		return Math.atan2(dy, dx)*180/Math.PI;
	}

	@Override
	public void onClick(View v) {
		maps.init();
	}
}
