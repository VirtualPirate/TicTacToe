package phantom.terminal.com.tictactoe;

public interface Player {
	public PlayerType getPlayerType();
	public boolean mark(int x, int y);
	public boolean mark(int index);
	public int getBackgroundColor();
	public char getChar();
}
