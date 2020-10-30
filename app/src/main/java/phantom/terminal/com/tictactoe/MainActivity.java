package phantom.terminal.com.tictactoe;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.view.View;
import android.graphics.Color;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    Position[] buttons;
    GameBoard board;
    Human player_1;
    Human player_2;

    Human current_player;

    Handler handler;
    Runnable win_button_changer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if(savedInstanceState != null){
//            board = (GameBoard)savedInstanceState.getParcelable("GameBoard");
//            buttons = board.positions;
//        }
        Position.main_activity = this;
        buttons = new Position[9];
        buttons[0] = new Position(1, (Button)(findViewById(R.id.button_1)));
        buttons[1] = new Position(2, (Button)(findViewById(R.id.button_2)));
        buttons[2] = new Position(3, (Button)(findViewById(R.id.button_3)));
        buttons[3] = new Position(4, (Button)(findViewById(R.id.button_4)));
        buttons[4] = new Position(5, (Button)(findViewById(R.id.button_5)));
        buttons[5] = new Position(6, (Button)(findViewById(R.id.button_6)));
        buttons[6] = new Position(7, (Button)(findViewById(R.id.button_7)));
        buttons[7] = new Position(8, (Button)(findViewById(R.id.button_8)));
        buttons[8] = new Position(9, (Button)(findViewById(R.id.button_9)));

        board = new GameBoard(this, buttons);
        player_1 = new Human(board, PlayerType.Human_1);
        player_1.setBackgroundColor(this.getColor(R.color.player_1_color));

        player_2 = new Human(board, PlayerType.Human_2);
        player_2.setBackgroundColor(this.getColor(R.color.player_2_color));

        current_player = player_1;
    }

//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState){
//        super.onSaveInstanceState(savedInstanceState);
//        savedInstanceState.putParcelable("GameBoard", board);
//    }

    public void buttonOnClick(View v){
        for(Position each_button: buttons){
            if(each_button.equals(v.getId()) && !each_button.is_marked()) {
                each_button.mark(current_player);
                v.getBackground().setColorFilter(current_player.getBackgroundColor(), PorterDuff.Mode.SRC_ATOP);
                ((Button) v).setText(((Character) current_player.getChar()).toString());
                if(current_player.getPlayerType() == PlayerType.Human_1)
                    current_player = player_2;
                else
                    current_player = player_1;
                break;
                }
            }
        if(board.checkWinner() != PlayerType.None) {
            Toast.makeText(getApplicationContext(), "Player wins the Game", Toast.LENGTH_LONG).show();
            for(Position each_button: buttons){
                each_button.button.setEnabled(false);
            }

            handler = new Handler();
            win_button_changer = new Runnable() {
                int foreground = getColor(R.color.win_foreground);
                int background = getColor(R.color.win_background);
                int swap = foreground;
                @Override
                public void run() {

                    for (Position each : board.check_arr) {
                        each.button.setTextColor(foreground);
                        each.button.getBackground().setColorFilter(background, PorterDuff.Mode.SRC_ATOP);
                    }
                    Log.d("Android: ", "Ran runnable");
                    foreground = background;
                    background = swap;
                    swap = foreground;
                    handler.postDelayed(this, 250);
                }
            };
            handler.post(win_button_changer);
        }

    }

    public void resetBoard(View v){
        for(Position each_button: buttons){
            each_button.reset();
        }
        if(win_button_changer != null)
            handler.removeCallbacks(win_button_changer);
        current_player = player_1;
    }
}
