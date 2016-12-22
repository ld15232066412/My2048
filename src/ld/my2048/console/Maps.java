package ld.my2048.console;

import java.util.Random;

import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;
import ld.my2048.inter.Direction;

public class Maps {

	private TextView score, best;
	private Button[][] maps = new Button[4][4];
	private int MaxScore;

	// 初始化界面
	public void init() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				maps[i][j].setText("");
			}
		}
		MaxScore = 0;
		score.setText(String.valueOf(MaxScore));
		addNumber();
		addNumber();
	}

	private void addNumber() {
		Random random = new Random();
		int x = random.nextInt(4);
		int y = random.nextInt(4);
		int number = random.nextInt(20);
		if (number == 0) {
			number = 4;
		} else {
			number = 2;
		}
		while (maps[x][y].getText().toString().length() != 0) {
			x = random.nextInt(4);
			y = random.nextInt(4);
		}
		maps[x][y].setText(number + "");
	}

	// 判断是否满了
	private boolean isFull() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (maps[i][j].getText().toString().length() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean slide(int direction) {
		if (direction == Direction.UP) {
			up_remore_blank();
			up();
			if (isFull()) {
				return true;
			} else {
				addNumber();
			}
		} else if (direction == Direction.DOWN) {
			down_remore_blank();
			down();
			if (isFull()) {
				return true;
			} else {
				addNumber();
			}
		} else if (direction == Direction.LEFT) {
			left_remore_blank();
			left();
			if (isFull()) {
				return true;
			} else {
				addNumber();
			}
		} else if (direction == Direction.RIGHT) {
			right_remore_blank();
			right();
			if (isFull()) {
				return true;
			} else {
				addNumber();
			}
		}
		return false;
	}

	private void up() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				String s1 = maps[j][i].getText().toString();
				String s2 = maps[j + 1][i].getText().toString();
				if (s1.equals(s2) && !s1.equals("")) {
					Integer sum = Integer.valueOf(maps[j][i].getText().toString());
					sum += Integer.valueOf(maps[j + 1][i].getText().toString());
//					Integer total = Integer.valueOf(score.getText().toString());
					if(MaxScore < sum){
						MaxScore = sum;
						score.setText(String.valueOf(MaxScore));
					}
					maps[j][i].setText(String.valueOf(sum));
					maps[j + 1][i].setText("");
					up_remore_blank();
				}
			}
		}
	}

	private void down() {
		for (int i = 0; i < 4; i++) {
			for (int j = 3; j >= 1; j--) {
				String s1 = maps[j][i].getText().toString();
				String s2 = maps[j - 1][i].getText().toString();
				if (s1.equals(s2) && !s1.equals("")) {
					Integer sum = Integer.valueOf(maps[j][i].getText().toString());
					sum += Integer.valueOf(maps[j - 1][i].getText().toString());
//					Integer total = Integer.valueOf(score.getText().toString());
					if(MaxScore < sum){
						MaxScore = sum;
						score.setText(String.valueOf(MaxScore));
					}
					maps[j][i].setText(sum.toString());
					maps[j - 1][i].setText("");
					down_remore_blank();
				}
			}
		}
	}

	private void left() {
		int i, j;
		for (i = 0; i < 4; i++) {
			for (j = 0; j < 3; j++) {
				String s1 = maps[i][j].getText().toString();
				String s2 = maps[i][j + 1].getText().toString();
				if (s1.equals(s2) && !s1.equals("")) {
					Integer sum = Integer.valueOf(s1);
					sum += Integer.valueOf(s2);
//					Integer total = Integer.valueOf(score.getText().toString());
					if(MaxScore < sum){
						MaxScore = sum;
						score.setText(String.valueOf(MaxScore));
					}
					maps[i][j].setText(sum.toString());
					maps[i][j + 1].setText("");
					left_remore_blank();
				}
			}
		}
	}

	private void right() {
		for (int i = 0; i < 4; i++) {
			for (int j = 3; j >= 1; j--) {
				String s1 = maps[i][j].getText().toString();
				String s2 = maps[i][j - 1].getText().toString();
				if (s1.equals(s2) && !s1.equals("")) {
					Integer sum = Integer.valueOf(maps[i][j].getText().toString());
					sum += Integer.valueOf(maps[i][j - 1].getText().toString());
//					Integer total = Integer.valueOf(score.getText().toString());
					if(MaxScore < sum){
						MaxScore = sum;
						score.setText(String.valueOf(MaxScore));
					}
					maps[i][j].setText(sum.toString());
					maps[i][j - 1].setText("");
					right_remore_blank();
				}
			}
		}
	}

	private void up_remore_blank() {
		int i, j, k;
		for (i = 0; i < 4; i++) {
			for (j = 1; j < 4; j++) {
				k = j;
				while (k - 1 >= 0 && maps[k - 1][i].getText().toString().length() == 0) {
					swapText(maps[k][i], maps[k - 1][i]);
					k--;
				}
			}
		}
	}

	private void down_remore_blank() {
		int i, j, k;
		for (i = 0; i < 4; i++) {
			for (j = 2; j >= 0; j--) {
				k = j;
				while (k + 1 <= 3 && maps[k + 1][i].getText().toString().length() == 0) {
					swapText(maps[k][i], maps[k + 1][i]);
					k++;
				}
			}
		}
	}

	private void left_remore_blank() {
		int i, j, k;
		for (i = 0; i < 4; i++) {
			for (j = 1; j < 4; j++) {
				k = j;
				while (k - 1 >= 0 && maps[i][k - 1].getText().toString().length() == 0) {
					swapText(maps[i][k], maps[i][k - 1]);
					k--;
				}
			}
		}
	}

	private void right_remore_blank() {
		int i, j, k;
		for (i = 0; i < 4; i++) {
			for (j = 2; j >= 0; j--) {
				k = j;
				while (k + 1 <= 3 && maps[i][k + 1].getText().toString().length() == 0) {
					swapText(maps[i][k], maps[i][k + 1]);
					k++;
				}
			}
		}
	}

	private void swapText(Button b1, Button b2) {
		CharSequence text = b1.getText();
		b1.setText(b2.getText());
		b2.setText(text);
	}

	public void addButton(int i, int j, Button btn) {
		maps[i][j] = btn;
	}

	public void setScore(TextView score) {
		this.score = score;
	}

	public void setBest(TextView best) {
		this.best = best;
		best.setText(getBestScore() + "");
	}

	public int getScore() {
		return Integer.valueOf(score.getText().toString());
	}

	public int getBestScore() {
		SharedPreferences sp = best.getContext().getSharedPreferences("bestScore", 0);
		return sp.getInt("bestScore", 0);
	}

	public void setBestScore(int score) {
		SharedPreferences sp = best.getContext().getSharedPreferences("bestScore", 0);
		SharedPreferences.Editor edit = sp.edit();
		edit.putInt("bestScore", score);
		edit.commit();
	}
}
