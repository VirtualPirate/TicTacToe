package phantom.terminal.com.tictactoe;
import android.graphics.Color;

public class Human implements Player {
	char player_char;
	int player_background;
	public PlayerType player_type;
	public GameBoard board;
	Human(GameBoard board, PlayerType player_type){
		this.board = board;
		this.player_type = player_type;
		if(player_type == PlayerType.Human_1)
			player_char = 'X';
		else
			player_char = 'O';
		this.player_background = Color.rgb(102, 102, 102);
	}
	@Override
	public PlayerType getPlayerType() {
		if(player_type == null)
			return PlayerType.None;
		return player_type;
	}

	@Override
	public boolean mark(int x, int y) {
		return board.board[x][y].mark(this);
	}

	@Override
	public boolean mark(int index) {
		return board.positions[index].mark(this);
	}

	public void setBackgroundColor(int color){
		player_background = color;
	}

	public void setChar(char c){
		player_char = c;
	}

	@Override
	public int getBackgroundColor(){
		return player_background;
	}

	@Override
	public char getChar(){
		return player_char;
	}
}

class None implements Player{

	@Override
	public PlayerType getPlayerType() {
		return PlayerType.None;
	}

	@Override
	public boolean mark(int x, int y) {
		return false;
	}

	@Override
	public boolean mark(int index) {
		return false;
	}

	@Override
	public int getBackgroundColor() {
		return Color.rgb(102, 102, 102);
	}

	@Override
	public char getChar() {
		return ' ';
	}
}
