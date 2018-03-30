package memory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Main {
	private static Shell shell;
	private static int h = 4;
	private static int w = 4;
	
	public static void run(Display display) {
		Game Game = new Game(shell);
    	Game.init(4,2);
		shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
            	
                display.sleep();
        }
        display.dispose();
	}
	
	public static void menu(Display display) {
		Button play = new Button(shell, SWT.PUSH);
		Button heighScore = new Button(shell, SWT.PUSH);
		//liste déroulente pour thème
		Text height = new Text(shell, SWT.BORDER);
		height.setSize(80, 20);
		height.setLocation(10, 80);
		height.setText("4");
		height.addModifyListener(listenerH);
		Text weight = new Text(shell, SWT.BORDER);
		weight.setSize(80, 20);
		weight.setLocation(100, 80);
		weight.setText("4");
		weight.addModifyListener(listenerW);
		play.setText("Play");
		heighScore.setText("Heigh Score");
		play.setLocation(10, 10);
		heighScore.setLocation(10, 50);
		play.setSize(80, 20);
		heighScore.setSize(80, 20);
		play.addSelectionListener(handlerPlay);
		heighScore.addSelectionListener(handlerHS);
		
		shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
	}
	
	static ModifyListener listenerH = new ModifyListener() {
	    /** {@inheritDoc} */
	    public void modifyText(ModifyEvent e) {
	        h = Integer.parseInt(((Text) e.getSource()).getText());
	    }
	};
	
	static ModifyListener listenerW = new ModifyListener() {
	    /** {@inheritDoc} */
	    public void modifyText(ModifyEvent e) {
	        w = Integer.parseInt(((Text) e.getSource()).getText());
	    }
	};
	
	static SelectionAdapter handlerPlay = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			Shell game = new Shell(shell);
			game.setText("game");
			game.open();
			Game Game = new Game(game);
	    	Game.init(w,h);
		}
	};
	
	static SelectionAdapter handlerHS = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			//TODO
		}
	};
	
    public static void main(String[] args) {
    	Display display = new Display();
        shell = new Shell(display);
        menu(display);
    	run(display);
    }
}
