package phantom.terminal.com.tictactoe;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

class Position{
	public static MainActivity main_activity;
	public static Player NONE = new None();
	public int value;
	public Player player_id;
	public Button button;
	Position(int value, Player player_id, Button button){
		this.value = value;
		this.player_id = player_id;
		this.button = button;
	}

	Position(int value, Button button){
		this.value = value;
		this.player_id = NONE;
		this.button = button;
	}
	public boolean equals(Position other){
		return value == other.value;
	}
	public boolean equals(int button_id){
		if(getButtonId() == button_id)
			return true;
		return false;
	}
	public boolean equal_mark(Player other){
		return player_id == other;
	}
	public boolean equal_mark(PlayerType other){
		return player_id.getPlayerType() == other;
	}
	public boolean equal_mark(Position other) {
		return player_id == other.player_id;
	}

	public boolean is_marked(){
		if(player_id == null)
			return false;
		return player_id.getPlayerType() != PlayerType.None;
	}

	public boolean mark(Player player_id){
		if(!is_marked()) {
			this.player_id = player_id;
			return true;
		}
		return false;
	}

	public void reset(){
		button.getBackground().setColorFilter(main_activity.getColor(R.color.button_color), PorterDuff.Mode.SRC_ATOP);
//		button.setBackground(ContextCompat.getDrawable(activity_main, R.drawable.mid_button));
		button.setTextColor(Color.rgb(0,0,0));
		button.setText(" ");
		button.setEnabled(true);
		player_id = NONE;
	}

	private void setChar(Character ch){
		button.setText(ch.toString());
	}
	public int getButtonId(){
		return ((View)button).getId();
	}

	public void markWinButton(){
		button.setTextColor(main_activity.getColor(R.color.win_foreground));
		button.getBackground().setColorFilter(main_activity.getColor(R.color.win_background), PorterDuff.Mode.SRC_ATOP);
	}

}
public class GameBoard implements Parcelable {
//	public static int default_color = Color.rgb(102, 102, 102);
	public MainActivity main_activity;
	public Position positions[];
	public Position board[][];
	public Position check_arr[];

	GameBoard(MainActivity activity, Position[] positions){
		this.main_activity = activity;
		this.positions = positions;
		this.board = new Position[3][3];
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++)
				board[i][j] = positions[i*3+j];
		}
		this.check_arr = new Position[3];
	}

	protected GameBoard(Parcel in) {
	}

	public static final Creator<GameBoard> CREATOR = new Creator<GameBoard>() {
		@Override
		public GameBoard createFromParcel(Parcel in) {
			return new GameBoard(in);
		}

		@Override
		public GameBoard[] newArray(int size) {
			return new GameBoard[size];
		}
	};

	public static boolean winPattern(Position a, Position b, Position c){
		return a.equal_mark(b) && b.equal_mark(c) && !a.equal_mark(PlayerType.None);
	}

	public PlayerType checkWinner(){
		PlayerType winner = PlayerType.None;
		//horizontal
//		Position check_arr[] = new Position[3];
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++)
				check_arr[j] = board[i][j];
			if(winPattern(check_arr[0], check_arr[1], check_arr[2])) {
				winner = board[i][0].player_id.getPlayerType();
				if(winner != PlayerType.None){
					for(Position each: check_arr){
						each.markWinButton();
					}
					return winner;
				}
			}
		}
		//vertical
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++)
				check_arr[j] = board[j][i];
			if(winPattern(check_arr[0], check_arr[1], check_arr[2])){
				winner = board[0][i].player_id.getPlayerType();
				if(winner != PlayerType.None){
					for(Position each: check_arr){
						each.markWinButton();
					}
					return winner;
				}
			}
		}

		//diagonal
		for(int j=0;j<3;j++)
			check_arr[j] = board[j][j];
		if(winPattern(board[0][0], board[1][1], board[2][2])) {
			winner = board[0][0].player_id.getPlayerType();
			if(winner != PlayerType.None){
				for(Position each: check_arr){
					each.markWinButton();
				}
				return winner;
			}
		}
		check_arr[0] = board[0][2];
		check_arr[1] = board[1][1];
		check_arr[2] = board[2][0];
		if(winPattern(board[0][2], board[1][1], board[2][0])) {
			winner = board[0][2].player_id.getPlayerType();
			if(winner != PlayerType.None){
				for(Position each: check_arr){
					each.markWinButton();
				}
				return winner;
			}
		}

		boolean is_draw = true;
		for(int x=0;x<3;x++){
			for(int y=0;y<3;y++){
				if(!board[x][y].is_marked())
					return PlayerType.None;
			}
		}

		Toast.makeText(main_activity, "Game is Draw", Toast.LENGTH_LONG).show();
		return PlayerType.None;
	}

	public void updateGameBoard(){

	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeValue(main_activity);
		parcel.writeValue(positions);
		parcel.writeValue(board);
		parcel.writeValue(check_arr);
	}
}
