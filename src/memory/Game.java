package memory;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import org.omg.CORBA.Context;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Game {
	public String player = new String("Temp");
	public ArrayList<Card> cardList = new ArrayList<Card>();
	public ArrayList<Integer> topList = new ArrayList<Integer>();
	public Shell shell;
	public Card previousCard;
	public Card oldestCard;
	public int w;
	public int h;
	public Chrono chrono = new Chrono();

	public Game(Shell shell) {
		this.shell = shell;
	}

	public void init(int w, int h) {
		chrono.start();
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
				try {
					turn(card);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	};
	
	
	public void turn(Card card) throws SQLException{
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

	public void updateCards() throws SQLException {
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
			chrono.stop();
			String name = JOptionPane.showInputDialog(null, "You Win, Enter your name");
			int score = (int) ((w*h)/chrono.getDureeSec());
			if(name != "") {
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}  
				Connection con=(Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/memory","root","root");  
				con.setVerifyServerCertificate(false);
				Statement stmt=con.createStatement(); 
				stmt.execute("INSERT INTO score (score, nom) VALUES ("+score+","+ "\"" +name+ "\"" +")");
				stmt.close();
				con.close();
			}
			System.out.println("You Win");
		}
	}
}
