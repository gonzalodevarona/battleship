package view;

import java.io.IOException;

import control.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameWindow extends Stage{
	
	
	//UI Elements
	private Scene scene;
	private GameController contol;
	private Label[][] radar;
	private Button[][] ataque;
	private TextField nameTF;
	private Button sendNameBtn;
	private Button surrenderBtn;
	private Label opponentLabel;
	private Label statusLabel;	
	
	
	public GameWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
			Parent parent = loader.load();
			scene = new Scene(parent, 487,421);
			this.setScene(scene);
			
			//Referencias
			radar = new Label[3][3];
			radar[0][0] = (Label) loader.getNamespace().get("radar00");
			radar[0][1] = (Label) loader.getNamespace().get("radar01");
			radar[0][2] = (Label) loader.getNamespace().get("radar02");
			radar[1][0] = (Label) loader.getNamespace().get("radar10");
			radar[1][1] = (Label) loader.getNamespace().get("radar11");
			radar[1][2] = (Label) loader.getNamespace().get("radar12");
			radar[2][0] = (Label) loader.getNamespace().get("radar20");
			radar[2][1] = (Label) loader.getNamespace().get("radar21");
			radar[2][2] = (Label) loader.getNamespace().get("radar22");
			
			ataque = new Button[3][3];
			ataque[0][0] = (Button) loader.getNamespace().get("ataque00");
			ataque[0][1] = (Button) loader.getNamespace().get("ataque01");
			ataque[0][2] = (Button) loader.getNamespace().get("ataque02");
			ataque[1][0] = (Button) loader.getNamespace().get("ataque10");
			ataque[1][1] = (Button) loader.getNamespace().get("ataque11");
			ataque[1][2] = (Button) loader.getNamespace().get("ataque12");
			ataque[2][0] = (Button) loader.getNamespace().get("ataque20");
			ataque[2][1] = (Button) loader.getNamespace().get("ataque21");
			ataque[2][2] = (Button) loader.getNamespace().get("ataque22");
			
			nameTF = (TextField) loader.getNamespace().get("nameTF");
			sendNameBtn = (Button) loader.getNamespace().get("sendNameBtn");
			surrenderBtn = (Button) loader.getNamespace().get("surrenderBtn");
			opponentLabel = (Label) loader.getNamespace().get("opponentLabel");
			statusLabel = (Label) loader.getNamespace().get("statusLabel");
			
			
			contol = new GameController(this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public Label[][] getRadar() {
		return radar;
	}


	public Button[][] getAtaque() {
		return ataque;
	}


	public TextField getNameTF() {
		return nameTF;
	}


	public Button getSendNameBtn() {
		return sendNameBtn;
	}


	public Label getOpponentLabel() {
		return opponentLabel;
	}

	public Button getSurrenderBtn() {
		return surrenderBtn;
	}


	public Label getStatusLabel() {
		return statusLabel;
	}	
	
	//UI Actions
	public void drawAttackInRadar(int fil, int col) {
		radar[fil][col].setStyle("-fx-background-color: red;");
	}
	
	public void drawWeakPointInRadar(int fil, int col) {
		radar[fil][col].setStyle("-fx-background-color: yellow;");
	}
	
	public void drawMate(int fil, int col) {
		radar[fil][col].setStyle("-fx-background-color: orange;");
	}
}
