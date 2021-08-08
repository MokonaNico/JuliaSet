package JuliaSet;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.RadialGradient;
import javafx.stage.Stage;

import java.util.Random;

public class JuliaSetApp extends Application {

    static final int WIDTH = 1500;
    static final int HEIGHT = 1000;

    double zoom = 1;
    double moveX = 0;
    double moveY = 0;

    double moveSpeed = 0.1;
    double zoomSpeed = 0.1;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("JuliaSet");
        Pane root = new Pane();
        root.setStyle("-fx-background-color: #555555");
        Scene scene = new Scene(root,WIDTH,HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        PixelWriter pw = gc.getPixelWriter();


        generateJuliaSet(pw);

        scene.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.Q) {
                moveX -= moveSpeed;
                generateJuliaSet(pw);
            }
            else if (ke.getCode() == KeyCode.D) {
                moveX += moveSpeed;
                generateJuliaSet(pw);
            }
            else if (ke.getCode() == KeyCode.Z) {
                moveY -= moveSpeed;
                generateJuliaSet(pw);
            }
            else if (ke.getCode() == KeyCode.S) {
                moveY += moveSpeed;
                generateJuliaSet(pw);
            }
            else if (ke.getCode() == KeyCode.A) {
                zoom -= zoomSpeed;
                generateJuliaSet(pw);
            }
            else if (ke.getCode() == KeyCode.E){
                zoom += zoomSpeed;
                generateJuliaSet(pw);
            }
        });


    }

    private void generateJuliaSet(PixelWriter pw){
        Complex c = new Complex(0.285,0.01);
        //Complex c = new Complex(-0.4, 0.6);
        //Complex c = new Complex(-0.8,0.156);
        //Complex c = new Complex(0,0);
        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                Complex zold = new Complex(1.5 * (x - WIDTH / 2.) / (0.5 * zoom * WIDTH) + moveX,
                        (y - HEIGHT / 2.) / (0.5 * zoom * HEIGHT) + moveY);
                Complex znew;
                int count;
                for(count = 0; count < 100000000; count++ ){
                    znew = zold.Mult(zold);
                    znew = znew.Add(c);
                    zold = znew;
                    if(znew.Mod() >= 2) break;
                }

                pw.setColor(x,y, Color.rgb(count%255,0,0));
            }
        }
    }


    private double mapping(double x, double abegin, double aend, double bbegin, double bend){
        return (x-abegin)/(aend-abegin) * (bend-bbegin) + bbegin;
    }
}
