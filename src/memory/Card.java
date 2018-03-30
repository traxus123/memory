package memory;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Card extends org.eclipse.swt.widgets.Button{
	public final static int hidden = 0;
	public final static int tryed = 1;
	public final static int win = 2;
	
	private Shell shell; 
	public String back = new String("");
	public String top = new String(); 
	public int state; //0:hidden ; 1:try ; 2:win
	public int index;
	public int shuffleindex;
	public Game game;
	
	public Card(Shell shell, int _act, int _top, int _index, int _shuffleindex, Game _game) {
		super(shell, _act);
		back = "";
		top = gettop(_top);
		state = hidden;
		index = _index;
		shuffleindex = _shuffleindex;
		game = _game;
	}
	
	protected void checkSubclass() {
		
	}
	
	public void setImage(Display display, Boolean side) {
		Image image;
		String Text = "";
		if(side) {
			//image = new Image(display, this.top);
			Text = this.shuffleindex + "";
		}
		else {
			//image = new Image(display, this.back);
			Text = "-";
		}
		//this.setImage(image);
		this.setText(Text);
	}

	private String gettop(int top) {
		return "";
	}
	
	
	// GETTERS & SETTERS
	
	//State
	public void setState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return this.state;
	}

}
