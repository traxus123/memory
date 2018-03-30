package memory;

import java.util.ArrayList;
import java.util.Collections;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Game {
	public String player = new String("Temp");
	public ArrayList<Card> cardList = new ArrayList<Card>();
	public ArrayList<Integer> topList = new ArrayList<Integer>();
	public Shell shell;
	public Card previousCard;
	public Card oldestCard;
	public int w;
	public int h;

	public Game(Shell shell) {
		this.shell = shell;
	}

	public void init(int w, int h) {
		this.h = h;
		this.w = w;
		this.shell.setBounds(100, 100, (w*45), (h*95));
		for(int i = 0 ; i < w*h ; i++) {
			topList.add(i%(w*h/2));
		}
		Collections.shuffle(topList);

		for(int i = 0 ; i < w*h ; i++) {
			cardList.add(new Card(this.shell, SWT.PUSH, topList.get(i), i, topList.get(i), this));
			cardList.get(i).setImage(shell.getDisplay(), false);
			cardList.get(i).setSize(30, 60);
			cardList.get(i).setLocation((i%w)*35+10, (i/w)*65+10);
			System.out.println(i + "|" + i%(w) + "|" + i/(w));
			cardList.get(i).addSelectionListener(handler);
		}
	}
	
	SelectionAdapter handler = new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			Card card = (Card) e.getSource();
			if(card.getState() == Card.hidden) {
				System.out.println(card.index);
				System.out.println(card.shuffleindex);
				System.out.println("-------");
				turn(card);
			}
		}
	};
	
	
	public void turn(Card card){
		if(oldestCard != null) {
			if(oldestCard.getState() == 1) {
			oldestCard.setState(Card.hidden);
			previousCard.setState(Card.hidden);
			}
			this.previousCard = null;
			this.oldestCard = null;
		}
		if(previousCard == null) {
			card.setState(Card.tryed);
			this.previousCard = card;
		}
		else {
			card.setState(Card.tryed);
			if (card.shuffleindex == previousCard.shuffleindex) {
				previousCard.setState(Card.win);
				card.setState(Card.win);
				oldestCard = previousCard;
				previousCard = card;
				System.out.println("ok");
			}
			else {
				previousCard.setState(Card.tryed);
				card.setState(Card.tryed);
				oldestCard = previousCard;
				previousCard = card;
				System.out.println("ko");
			}
		}

		this.updateCards();
	}

	public void updateCards() {
		Boolean win = true;
		
		for(int i = 0 ; i < h*w ; i++) {
			if(cardList.get(i).getState() == Card.hidden) {
				win = false;
				cardList.get(i).setImage(shell.getDisplay(), false);
			}
			else if(cardList.get(i).getState() == Card.tryed) {
				win = false;
				cardList.get(i).setImage(shell.getDisplay(), true);
			}
			else {
				cardList.get(i).setImage(shell.getDisplay(), true);
			}
		}
		if(win) {
			System.out.println("You Win");
		}
	}
}
