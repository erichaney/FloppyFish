import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class FloppyFishApp extends Application
{
    Game game;
    GraphicsContext gc;

    Image fishImage;
    Image sharkImage;

    AudioClip splash;
    AudioClip bloop;
    AudioClip uhoh;

    boolean gameOverSoundPlayed;

    public void start(Stage stage)
    {
        game = new Game();

        Canvas canvas = new Canvas(game.width, game.height);
        gc = canvas.getGraphicsContext2D();

        VBox vbox = new VBox(canvas);

        Scene scene = new Scene(vbox);
        scene.setOnKeyPressed(this::handleKeyPress);

        stage.setScene(scene);
        stage.show();

        fishImage = new Image("fish.png");
        sharkImage = new Image("shark.png");

        gameOverSoundPlayed = false;

        
        String pathToSoundsFolder = getClass().getResource("sounds/").toExternalForm();
        splash = new AudioClip(pathToSoundsFolder + "splash.wav");
        bloop = new AudioClip(pathToSoundsFolder + "bloop.wav");
        uhoh = new AudioClip(pathToSoundsFolder + "uhoh.wav");

        GameTimer timer = new GameTimer();
        timer.start();
    }

    void handleKeyPress(KeyEvent e)
    {
        if (e.getCode() == KeyCode.SPACE)
        {
            game.player.jump();
            bloop.play();
        }

        if (e.getCode() == KeyCode.ENTER && game.isGameOver)
        {
            game = new Game();
            gameOverSoundPlayed = false;
        }
    }

    void drawObstacles()
    {
        //gc.setFill(Color.GRAY);
        for (Obstacle o : game.obstacles)
        {
            //gc.fillOval(o.x, o.y, o.width, o.height);

            if (o.getClass() == Shark.class)
            {
                gc.drawImage(sharkImage, o.x, o.y);
            }
        }

    }

    void drawInfo()
    {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font(14));
        gc.fillText("Points: " + game.points, 30, 30);
    }

    void drawFish()
    {
        //gc.setFill(Color.SALMON);
        //gc.fillOval(game.player.x, game.player.y, game.player.width, game.player.height);

        gc.drawImage(fishImage, game.player.x, game.player.y);
    }

    void drawBackground()
    {
        gc.setFill(Color.LIGHTSEAGREEN);
        gc.fillRect(0, 0, game.width, game.height);

        //sand
        gc.setFill(Color.WHEAT);
        gc.fillRect(0, game.height - 40, game.width, 40);
    }

    void drawGame()
    {
        drawBackground();
        drawObstacles();
        drawFish();
        drawInfo();

        if (game.isGameOver == true)
        {
            drawGameOver();
            if (gameOverSoundPlayed == false)
            {
                uhoh.play();
                gameOverSoundPlayed = true;
            }
        }
    }

    void drawGameOver()
    {
        gc.setFill(Color.FIREBRICK);
        gc.fillRect(game.width/3, game.height/3, game.width/3, game.height/3);

        gc.setFill(Color.WHITE);
        gc.setFont(new Font(34));
        gc.fillText("GAME OVER", game.width/3 + 80, game.height/3 + 110);
        gc.setFont(new Font(14));
        gc.fillText("Press ENTER to play again.", game.width/3 + 90, game.height/3 + 160);
    }

    class GameTimer extends AnimationTimer
    {
        public void handle(long t)
        {
            // put the game code here that runs every frame
            game.update();
            drawGame();
        }
    }
}
