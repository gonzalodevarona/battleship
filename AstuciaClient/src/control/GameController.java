package control;

import comm.Receptor.OnMessageListener;

import java.util.Calendar;
import java.util.UUID;

import com.google.gson.Gson;
import comm.TCPConnection;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.Message;
import view.GameWindow;

public class GameController implements OnMessageListener {

	private GameWindow view;
	private TCPConnection connection;

	public GameController(GameWindow view) {
		this.view = view;
		init();
	}

	public void init() {
		connection = TCPConnection.getInstance();
		connection.setListenerOfMessages(this);

		int fil = (int) (3 * Math.random());
		int col = (int) (3 * Math.random());
		view.drawWeakPointInRadar(fil, col);
		
		// SEND NAME FUNCTION
		
		view.getSendNameBtn().setOnAction(
				event ->{
					String name = view.getNameTF().getText();
					if (!(name.equalsIgnoreCase(""))) {
						
						sendMessage("Name_"+view.getNameTF().getText());
					}
					
				});
		
		// SURRENDER FUNCTION
		
		view.getSurrenderBtn().setOnAction(
				event ->{
					
						sendMessage("");
						iLost();
						
				});
		
		// START ATTACK BUTTONS FUNCTION
		
		Button[][] attackBtns = view.getAtaque();
		
		for (int i = 0; i < attackBtns.length; i++) {
			for (int j = 0; j < attackBtns.length; j++) {
				attackButtonAction(attackBtns[i][j], i , j);
			}
		}
		
		

	}

	

	@Override
	public void OnMessage(String msg) {
		Platform.runLater(
				() -> {
					
					Gson gson = new Gson();
					Message msjObj = gson.fromJson(msg, Message.class);
					
					if (msjObj.getBody().equalsIgnoreCase("")) {
						surrender();
						
						
					} else if(msjObj.getBody().contains("Name_")){
						view.getOpponentLabel().setText(msjObj.getBody());
						
						
					} else if(msjObj.getBody().contains("Attack_")){
						String[] coord = msjObj.getBody().split("_");
						receiveAttack(coord[1]);
						
						
					} else if(msjObj.getBody().equalsIgnoreCase("Status_YouWin")){
						iLost();
						view.getStatusLabel().setText("GANASTE");
					}
					
					
					
				}
		);

	}
	
	private void sendMessage(String msg) {
		

		
		Gson gson = new Gson();
		String json = gson.toJson(new Message(msg));
		TCPConnection.getInstance().getEmisor().sendMessage(json);
	}
	
	private void receiveAttack(String coord) {
		disableAttacks(false);
		
		String[] realCoord = coord.split(",");
		
		int row =  Integer.parseInt(realCoord[0]);
		int col=  Integer.parseInt(realCoord[1]);
		
		Label tile = view.getRadar()[row][col];
		
		if (tile.getStyle().contains("yellow")) {
			view.drawMate(row, col);
			
		
			sendMessage("Status_YouWin");
			iLost();
			
			
		} else {
			view.drawAttackInRadar(row, col);
		}
		
		
				
		
		
	}

	private void attackButtonAction(Button btn, int row, int col) {
		btn.setOnAction(
				event ->{
						btn.setVisible(false);
						
						sendMessage("Attack_"+row+","+col);
						
						disableAttacks(true);
					
				});
		
	}
	
	
	
	
	private void iLost() {
		view.getStatusLabel().setText("Perdiste");
		view.getSendNameBtn().setDisable(true);
		view.getSurrenderBtn().setDisable(true);
		disableAttacks(true);
	}
	
	private void disableAttacks(boolean able) {
		Button[][] layout = view.getAtaque();
		
		for (int i = 0; i < layout.length; i++) {
			for (int j = 0; j < layout[0].length; j++) {
				layout[i][j].setDisable(able);
			}
		}
	}
	

	
	
	private void surrender() {
		view.getStatusLabel().setText("Ganador, el enemigo se rindio");
		view.getSendNameBtn().setDisable(true);
		view.getSurrenderBtn().setDisable(true);
		disableAttacks(true);
	}

}
