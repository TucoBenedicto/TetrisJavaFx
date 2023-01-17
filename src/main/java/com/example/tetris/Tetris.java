package com.example.tetris;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Tetris extends Application {

    //Variables
    public static final int MOVE = 25; //deplacement de case par case de 25px en x et y
    public static final int SIZE = 25; //Taille de chaque carré  pour les formes
    public static final int XMAX = SIZE * 12; //largeur de la zone de jeux de 12 carre
    public static final int YMAX = SIZE * 24; // hauteur de la zone de jeux de 24 carre
    public static int [][] MESH = new int[XMAX/SIZE][YMAX/SIZE]; //tableau bidimentionelle ,le 1er index indique le nombre de ligne (12) et le 2eme index indique le nombre de colonne (24).
    private static Pane groupe = new Pane();
    private static Form object;
    private static Scene scene = new Scene(groupe,XMAX +150 ,YMAX);
    public static int score = 0 ; //Score du jeu (en haut a gauche)
    public static int top = 0 ; //Top = si les blocs atteignent le sommet
    private static boolean game = true;
    private static Form nextObj = Controller.makeRect();
    private static int linesNo = 0; //Score du nombre de ligne

    //creating scene and start the game
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        for (int [] a : MESH){
            Arrays.fill(a,0);
        }
        //Creating score and level text
        Line line = new Line(XMAX,0,XMAX,YMAX);//Creation d'une ligne qui va separer la fenetre de jeu de la fenetre de score
        //Creation du Text pour le score
        Text scoretext = new Text("Score :");
        scoretext.setStyle("-fx-font: 20 arials;");
        scoretext.setY(50);//positionement de scoretext dans la fenetre
        scoretext.setX(XMAX + 5);
        //Creation du texte pour le nombre de ligne empilé
        Text level = new Text("Lines: ");
        level.setStyle("-fx-font: 20 arials;");
        level.setY(100);
        level.setX(XMAX + 5);
        level.setFill(Color.GREEN);
        groupe.getChildren().addAll(scoretext,line,level); //creation d'un container "groupe", qui va recuper les varaibles (scoretex,line, leve) a l'aide de getchildren
        //Creating first block ans the stage
        Form a = nextObj;
        groupe.getChildren().addAll(a.a,a.b,a.c,a.d);
        moveOnKeyPress(a);
        object = a;
        nextObj = Controller.makeRect();
        stage.setScene(scene);//Creation de la fenetre de jeux
        stage.setTitle("TETRIS");// on place tetris comme nom de l'onglet de la fenetre
        stage.show();//rend la fenetre visible //
        //Timer
        Timer fall = new Timer(); //La classe Timer est un scheduler basique qui permet la planification de l'exécution d'une tâche pour une seule exécution ou pour une exécution répétée à intervalles réguliers. Elle utilise pour cela un thread dédié.

        /*
        TimerTask encapsule les traitements d'une tâche qui sera exécutée par un Timer.
        TimerTask permet la planification d'une seule exécution ou de plusieurs.
         */
        TimerTask task = new TimerTask() {
            @Override
            //New Java Thread
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0 || object.d.getY() == 0  )
                        top++;
                        else
                            top = 0;
                        if (top == 2){ //si les blocs atteigne 2 fois le sommet on affiche un game over
                            //Game OVER
                            Text over = new Text("GAME OVER");
                            over.setFill(Color.RED);
                            over.setStyle("-fx-font: 70 arials;");
                            over.setY(250);
                            over.setX(10);
                            groupe.getChildren().add(over);
                            game = false;
                        }
                        //Exit
                        if (top == 15){ //si les blocs atteignent 15 fois le sommet on quitte le jeu
                            System.exit(0);
                        }
                        //Incrementation des scores + nb de ligne
                        if (game){
                            MoveDown(object);
                            scoretext.setText("Score :" + Integer.toString(score));
                            level.setText("Lines :" + Integer.toString(linesNo));
                        }
                    }
                });
            }
        };
        // Execution du Timer "fall"
        // Planifier l'exécution répétée de la tâche : la première exécution intervient après avoir attendu delay millisecondes et le délai entre chaque exécution est précisé par le paramètre period en millisecondes
        fall.schedule(task,0,300);
    }

    //Create the Control System
    //gestion des touche du clavier
    private void moveOnKeyPress(Form form){
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case RIGHT :
                        Controller.MoveRight(form);
                        break;
                    case DOWN:
                        MoveDown(form);
                        score++; //Move down = higher score
                        break;
                    case LEFT:
                        Controller.MoveLeft(form);
                        break;
                    case UP :
                        MoveTurn(form);
                        break;
                }
            }
        });
    }
    //Move the stones into different shapes(forms)
    //rotation des formes des l'appui sur une touche
    private void MoveTurn(Form form){
    int f = form.form;
    //Moving every single rectangle of each stone
    Rectangle a = form.a;
    Rectangle b = form.b;
    Rectangle c = form.c;
    Rectangle d = form.d;

    //Chaque form va prendre 4 formes differentes du fait de la rotation
    switch (form.getName()){
        //Lettre J
        case "j":
            //form: 1 (form initial)
            if (f==1 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
            MoveRight(form.a);
            MoveDown(form.a);
            MoveDown(form.c);
            MoveLeft(form.c);
            MoveDown(form.d);
            MoveDown(form.d);
            MoveLeft(form.d);
            MoveLeft(form.d);
            form.changeForm();
            break;
            }
            //We repeat the procedure for every single form
            //form : 2 (1ere rotation)
            if (f == 2 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 3 (2eme rotation)
            if (f==3 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 4 (3eme rotation)
            if (f == 4 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
        //Lettre L
        case "l":
            //form: 1 (form initial)
            if (f==1 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //We repeat the procedure for every single form
            //form : 2 (1ere rotation)
            if (f == 2 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 3 (2eme rotation)
            if (f==3 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 4 (3eme rotation)
            if (f == 4 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //lettre O
        case "o":
            //form: 1 (form initial)
            if (f==1 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //We repeat the procedure for every single form
            //form : 2 (1ere rotation)
            if (f == 2 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 3 (2eme rotation)
            if (f==3 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 4 (3eme rotation)
            if (f == 4 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //Lettre S
        case "s":
            //form: 1 (form initial)
            if (f==1 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //We repeat the procedure for every single form
            //form : 2 (1ere rotation)
            if (f == 2 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 3 (2eme rotation)
            if (f==3 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 4 (3eme rotation)
            if (f == 4 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //Lettre T
        case "t":
            //form: 1 (form initial)
            if (f==1 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //We repeat the procedure for every single form
            //form : 2 (1ere rotation)
            if (f == 2 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 3 (2eme rotation)
            if (f==3 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 4 (3eme rotation)
            if (f == 4 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //Lettre Z
        case "z":
            //form: 1 (form initial)
            if (f==1 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //We repeat the procedure for every single form
            //form : 2 (1ere rotation)
            if (f == 2 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 3 (2eme rotation)
            if (f==3 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 4 (3eme rotation)
            if (f == 4 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //Lettre I
        case "i":
            //form: 1 (form initial)
            if (f==1 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //We repeat the procedure for every single form
            //form : 2 (1ere rotation)
            if (f == 2 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 3 (2eme rotation)
            if (f==3 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
            //form : 4 (3eme rotation)
            if (f == 4 && cB(a,1,-1) && cB(c,-1,-1) && cB(d,-2,-2) ){
                //We need to move each rectangle in different directions
                MoveRight(form.a);
                MoveDown(form.a);
                MoveDown(form.c);
                MoveLeft(form.c);
                MoveDown(form.d);
                MoveDown(form.d);
                MoveLeft(form.d);
                MoveLeft(form.d);
                form.changeForm();
                break;
            }
    }
    }

    //on supprime la ligne une fois qu'elle est complete
    private void RemoveRows(Pane pane){
       ArrayList<Node> rects = new ArrayList<Node>();
       ArrayList<Integer> lines =new ArrayList<Integer>();
       ArrayList<Node> newrects = new ArrayList<Node>();
       int full = 0 ;
       //Check wich line is full
        for (int i = 0; i< MESH[0].length;i++){
            for (int j = 0 ; i< MESH.length; j++){
                if (MESH[j][i] == 1)
                    full++;
            }
            if (full == MESH.length)
                lines.add(i+lines.size());
            full = 0 ;
        }

        //Deleting the row
        if (lines.size()>0)
            do { //La boucle do-while en Java est utilisée pour exécuter un bloc d’instructions en continu jusqu’à ce que la condition donnée soit vraie
                for (Node node : pane.getChildren()){
                    if (node instanceof Rectangle)
                        rects.add(node);
                }
                score+=50; //si une ligne est faite on augment le score a 50
                linesNo++;// et on ajoute +1

                //Deleting block on row
                //Remove the rectangles from the mesh
                for (Node node :rects){
                    Rectangle a =(Rectangle) node;
                    if(a.getY() == lines.get(0)*SIZE){
                        MESH[(int)a.getX()/SIZE][(int)a.getY()/SIZE ] = 0;
                        pane.getChildren().remove(node);
                    }
                    else
                        newrects.add(node);
                }
                for (Node node : newrects) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY() < lines.get(0) * SIZE) {
                        MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                        a.setY(a.getY() + SIZE);
                    }
                }
                    lines.remove(0);
                    rects.clear();
                    newrects.clear();

                    for (Node node : pane.getChildren()) {
                        if (node instanceof Rectangle)
                            rects.add(node);
                    }
                    for (Node node : rects) {
                        Rectangle a = (Rectangle) node;
                        try {
                            MESH[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
                        } catch (ArrayIndexOutOfBoundsException e) {

                        }
                    }
                    rects.clear();

                }while (lines.size()>0);

            }

private void MoveDown(Rectangle rect){
    //Methode permettant de descendre la forme
        if (rect.getY() + MOVE<YMAX)
            rect.setY(rect.getY()+ MOVE);
}

    private void MoveRight(Rectangle rect){
        //Methode permettant d'aller a droite la forme
        if (rect.getX() + MOVE<YMAX-SIZE)
            rect.setX(rect.getX()+ MOVE);
    }

    private void MoveLeft(Rectangle rect){
        //Methode permettant d'aller a gauche la forme
        if (rect.getX() - MOVE >= 0)
            rect.setX(rect.getY() - MOVE);
    }

    private void MoveUP(Rectangle rect){
        //Methode permettant de monter la forme
        if (rect.getY() + MOVE > 0)
            rect.setY(rect.getY() - MOVE);
    }

    public void MoveDown(Form form){
        //Moving if down is full
        if (form.a.getY() == YMAX - SIZE || form.b.getY() == YMAX-SIZE||
        form.c.getY() == YMAX-SIZE|| form.d.getY() == YMAX-SIZE||moveA(form)||
        moveB(form)||moveC(form)|| moveD(form)){
            //pour les 4 blocs unitaire composant la forme
            MESH[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
            MESH[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
            MESH[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
            MESH[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;
            RemoveRows(groupe);

            //Creating new bloc and adding to the scene
            Form a = nextObj;
            nextObj = Controller.makeRect();
            object = a;
            groupe.getChildren().addAll(a.a,a.b,a.c,a.d);
            moveOnKeyPress(a);
        }

        //Moving one block down if down is not full
        if (form.a.getY()+MOVE < YMAX && form.b.getY()+MOVE<YMAX&& form.c.getY()+
          +MOVE<YMAX&&form.d.getX()+MOVE>YMAX ){
            //Everything must be done for each individual rectangle
            //pour les 4 blocs unitaire composant la forme
            int movea = MESH[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
            int moveb = MESH[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
            int movec = MESH[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
            int moved = MESH[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;

            if (movea == 0 && movea == moveb && moveb == movec && movec == moved){
                form.a.setY(form.a.getY()+MOVE);
                form.b.setY(form.b.getY()+MOVE);
                form.c.setY(form.c.getY()+MOVE);
                form.d.setY(form.d.getY()+MOVE);
            }
        }
    }

    //Move Block A
private boolean moveA(Form form){
    return (MESH[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE+1] == 1);
}

    //Move Block B
    private boolean moveB(Form form){
        return (MESH[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] == 1);
    }


    //Move Block C
    private boolean moveC(Form form){
        return (MESH[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] == 1);
    }


    //Move Block D
    private boolean moveD(Form form){
        return (MESH[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE]== 1);
    }


//First, we write a boolean function to check the rectangles
    private boolean cB(Rectangle rect, int x , int y){
    boolean yb = false;
    boolean xb = false;
    if (x >= 0)
        xb = rect.getX() + x*MOVE <= XMAX - SIZE;
    if (x < 0)
        xb = rect.getX() + x*MOVE >= 0;
    if (y >= 0)
        yb = rect.getY() + y*MOVE > 0;
    if (x >= 0)
        yb = rect.getY() + y*MOVE < YMAX;
    //Returns the mesh with the rectangles
    return xb && yb && MESH[((int)rect.getX()/SIZE)+ x][((int)rect.getY()/SIZE)-y] == 0;
    }
}
