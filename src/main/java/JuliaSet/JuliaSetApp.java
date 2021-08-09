package JuliaSet;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class JuliaSetApp extends Application {

    static final int WIDTH = 1080;//540;
    static final int HEIGHT = 720;//360;

    static final int maxIterations = 128;

    static final Color[] colors = {
            Color.rgb(66,30,15),
            Color.rgb(25,7,26),
            Color.rgb(9,1,47),
            Color.rgb(4,4,73),
            Color.rgb(0,7,100),
            Color.rgb(12,44,138),
            Color.rgb(24, 82, 177),
            Color.rgb(57, 125, 209),
            Color.rgb(134, 181, 229),
            Color.rgb(211, 236, 248),
            Color.rgb(241, 233, 191),
            Color.rgb(248, 201, 95),
            Color.rgb(255, 170, 0),
            Color.rgb(204, 128, 0),
            Color.rgb(153, 87, 0),
            Color.rgb(106, 52, 3)
    };

    double zoom = 0.8;
    double moveX = 0;
    double moveY = 0;
    double moveSpeed = 0.1;
    double zoomSpeed = 0.1;

    Complex c = new Complex(0,0);
    PixelWriter pw;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //set up the scene
        primaryStage.setTitle("JuliaSet");
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,WIDTH,HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        //set up the canvas
        Pane wrapperPane = new Pane();
        root.setCenter(wrapperPane);
        root.setStyle("-fx-background-color: #000000");
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        wrapperPane.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        pw = gc.getPixelWriter();

        //set up the bottom text input and button
        HBox generationBox = new HBox();
        root.setBottom(generationBox);
        TextField realTF = new TextField();
        generationBox.getChildren().add(realTF);
        TextField imaginaryTF = new TextField();
        generationBox.getChildren().add(imaginaryTF);
        Button generationButton = new Button("Generate");
        generationBox.getChildren().add(generationButton);

        generationButton.setOnAction(event -> {
            try{
                double real = Double.parseDouble(realTF.getText());
                double imaginary = Double.parseDouble(imaginaryTF.getText());
                c = new Complex(real, imaginary);
                generateJuliaSet();
            } catch (NumberFormatException ignored){}
        });

        scene.setOnKeyPressed(ke -> {
            if (ke.getCode() == KeyCode.Q) {
                moveX -= moveSpeed;
                generateJuliaSet();
            } else if (ke.getCode() == KeyCode.D) {
                moveX += moveSpeed;
                generateJuliaSet();
            } else if (ke.getCode() == KeyCode.Z) {
                moveY -= moveSpeed;
                generateJuliaSet();
            } else if (ke.getCode() == KeyCode.S) {
                moveY += moveSpeed;
                generateJuliaSet();
            } else if (ke.getCode() == KeyCode.A) {
                zoom -= zoomSpeed;
                generateJuliaSet();
            } else if (ke.getCode() == KeyCode.E){
                zoom += zoomSpeed;
                generateJuliaSet();
            }
        });

        AnimationTimer animationTimer = new AnimationTimer() {
            double r = 0;
            int count = 0;

            @Override
            public void handle(long now) {
                if(r <= Math.PI*2){
                    c = new Complex(Math.sin(r),0);
                    generateJuliaSet();
                    r+=0.005;
                    count+=1;

                    File file = new File("img/"+count+".png");

                    try {
                        WritableImage writableImage = scene.snapshot(null);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                    } catch (IOException ex) { ex.printStackTrace(); }
                }
            }
        };

        //animationTimer.start();

    }

    private void generateJuliaSet(){
        Complex znew;
        Complex zold;

        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                double newRe = 1.5 * (x - WIDTH / 2.) / (0.5 * zoom * WIDTH) + moveX;
                double newIm = (y - HEIGHT / 2.) / (0.5 * zoom * HEIGHT) + moveY;
                znew = new Complex(newRe, newIm);

                int n;
                for(n = 0; n < maxIterations; n++ ){
                    zold = znew;
                    znew = zold.square().add(c);
                    if(znew.squaredMod() >= 4) break;
                }

                if(n < maxIterations && n > 0){
                    pw.setColor(x,y, colors[n % colors.length]);
                } else {
                    pw.setColor(x,y, Color.BLACK);
                }
            }
        }
    }
}
