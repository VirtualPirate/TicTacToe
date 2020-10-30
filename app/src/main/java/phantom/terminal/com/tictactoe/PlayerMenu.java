package phantom.terminal.com.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class PlayerMenu extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_menu);
	}

	public void gotoGameBoard(View v){
		Intent GameBoardIntent = new Intent(this, MainActivity.class);
		startActivity(GameBoardIntent);
	}
}