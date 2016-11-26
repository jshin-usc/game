package fxgame;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class InteractionBox {

	private Rectangle2D box;
	private KeyCode direction;
	private Pane modalPane = new StackPane();
	private List<TypewriterAnimation> typewriters = new ArrayList<TypewriterAnimation>();
	private Timeline typewriterTimeline = null;
	private Text[] texts = null;

	InteractionBox(Rectangle2D box, KeyCode direction) {
		this.box = box;
		this.direction = direction;
		modalPane.setPrefSize(560, 160);

		Rectangle bg = new Rectangle(560, 160);
		bg.setFill(Color.BLACK);
		bg.setStroke(Color.WHITE);
		bg.setStrokeWidth(6);

		modalPane.getChildren().add(bg);
		modalPane.setLayoutX(40);
		modalPane.setLayoutY(300);
	}

	InteractionBox(Rectangle2D box, KeyCode direction, String message) {
		this(box, direction);
		Text text = new Text();
		text.setFill(Color.WHITE);
		text.setFont(Font.loadFont(getClass().getResourceAsStream("fonts/DTM-Mono.otf"), 26));
		text.setWrappingWidth(500);
		text.setLineSpacing(6);
		typewriters.add(new TypewriterAnimation(message, text));
		modalPane.getChildren().add(text);
		StackPane.setAlignment(text, Pos.TOP_CENTER);
		StackPane.setMargin(text, new Insets(26, 26, 26, 26));
	}

	InteractionBox(Rectangle2D box, KeyCode direction, Text text) {
		this(box, direction);
		text.setFill(Color.WHITE);
		text.setWrappingWidth(500);
		text.setLineSpacing(6);
		typewriters.add(new TypewriterAnimation(text.getText(), text));
		modalPane.getChildren().add(text);
		StackPane.setAlignment(text, Pos.TOP_CENTER);
		StackPane.setMargin(text, new Insets(26, 26, 26, 26));
	}

	InteractionBox(Rectangle2D box, KeyCode direction, Text... texts) {
		this(box, direction);
		VBox vbox = new VBox();
		vbox.setSpacing(10);
		this.texts = texts;
		typewriterTimeline = new Timeline();

		for (int i = 0; i < texts.length; i++) {
			texts[i].setFill(Color.WHITE);
			texts[i].setWrappingWidth(500);
			vbox.getChildren().add(texts[i]);

			TypewriterAnimation animation = new TypewriterAnimation(texts[i].getText(), texts[i]);
			typewriters.add(animation);
			typewriterTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(2200*i), e -> animation.play()));
			texts[i].setText("");
		}

		modalPane.getChildren().add(vbox);
		StackPane.setAlignment(vbox, Pos.TOP_CENTER);
		StackPane.setMargin(vbox, new Insets(26, 26, 26, 26));
	}

	public Rectangle2D getBox() {
		return box;
	}

	public KeyCode getDirection() {
		return direction;
	}

	public List<TypewriterAnimation> getTextAnimations() {
		return typewriters;
	}

	public Pane getModalPaneAndPlay() {
		if (typewriterTimeline == null) {
			typewriters.get(0).play();
		}
		else {
			for (Text text : texts) {
				text.setText("");
			}
			typewriterTimeline.play();
		}
		return modalPane;
	}

	public Pane getModalPane() {
		return modalPane;
	}

}