package com.example.tetris;

import javafx.scene.shape.Rectangle;

//Cette class permettant de placer les formes et gerer le deplacment
public class Controller {
    //getting numbers and Mesh (cadrillage) from Tetris class
    //on recupere les valeur et la cadriallage
    public static final int MOVE = Tetris.MOVE;
    public static final int SIZE = Tetris.SIZE;
    public static final int XMAX = Tetris.XMAX;
    public static final int YMAX = Tetris.YMAX;
    public static int [][] MESH = Tetris.MESH;

    //moving the bloks
    //deplacement et controlle des blocs vers la droite
    public static void MoveRight (Form form){
        //getX() = Java Component getX() Returns the current x coordinate of the components origin.
        //setX() = on modifie la coordonnée X du composant
        if (form.a.getX() + MOVE <= XMAX - SIZE && form.b.getX() + MOVE<= XMAX - SIZE
            && form.c.getX() + MOVE <= XMAX - SIZE && form.d.getX() + MOVE <= XMAX-SIZE){

            // on fait bouger en meme temps l'ensemble les 4 blocs unitaires
            int movea = MESH[((int) form.a.getX()/SIZE) +1][((int) form.a.getY()/SIZE)];
            int moveb = MESH[((int) form.b.getX()/SIZE) +1][((int) form.b.getY()/SIZE)];
            int movec = MESH[((int) form.c.getX()/SIZE) +1][((int) form.c.getY()/SIZE)];
            int moved = MESH[((int) form.d.getX()/SIZE) +1][((int) form.d.getY()/SIZE)];

            if (movea == 0 && movea == moveb && moveb == movec && movec == moved){
                form.a.setX(form.a.getX() + MOVE);
                form.b.setX(form.b.getX() + MOVE);
                form.c.setX(form.c.getX() + MOVE);
                form.d.setX(form.d.getX() + MOVE);
            }

        }
    }

    //The same for moving left
    //on le fait uniquement le deplacement a gauche et a droite parce que les formes descendes toute seul
    public static void MoveLeft (Form form){
        //getX() = Java Component getX() Returns the current x coordinate of the components origin.
        //setX() = on definit la coordonnée X du composant
        if (form.a.getX() - MOVE >= 0 && form.b.getX() - MOVE>= 0 && form.c.getX() - MOVE >= 0 && form.d.getX() - MOVE >=0){

            // on fait bouger en meme temps l'ensemble les 4 blocs unitaires
            int movea = MESH[((int) form.a.getX()/SIZE) -1][((int) form.a.getY()/SIZE)];
            int moveb = MESH[((int) form.b.getX()/SIZE) -1][((int) form.b.getY()/SIZE)];
            int movec = MESH[((int) form.c.getX()/SIZE) -1][((int) form.c.getY()/SIZE)];
            int moved = MESH[((int) form.d.getX()/SIZE) -1][((int) form.d.getY()/SIZE)];

            if (movea == 0 && movea == moveb && moveb == movec && movec == moved){
                form.a.setX(form.a.getX() - MOVE);
                form.b.setX(form.b.getX() - MOVE);
                form.c.setX(form.c.getX() - MOVE);
                form.d.setX(form.d.getX() - MOVE);
            }
        }
    }

    //Create the stones
    //Creation des formes de maniere aleatoire quand elle apparaissent en haut de la fenettre
    public static Form makeRect() {
        //random color for the stones
        int block = (int) (Math.random()*100); // (int) permet le cast, car random , renvoi une valeur decimal (double)
        String name;

        Rectangle a = new Rectangle(SIZE-1,SIZE-1), //le -1 permet de creer un carrer de bordur blanche (car 25-1 = 24 )
                b = new Rectangle(SIZE-1,SIZE-1),
                c = new Rectangle(SIZE-1,SIZE-1),
                d = new Rectangle(SIZE-1,SIZE-1);

        //si la methode random nous renvoit une valeur >15 alors on place les blocs unitaire (carré) pour creer la forme "J"
        if (block<15){
            a.setX(XMAX / 2 - SIZE);
            //a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX/2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            d.setX(SIZE);
            name = "j";
        }
        //Creation du "L"
        else if (block < 30){
            a.setX(XMAX / 2 + SIZE);
            //a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX/2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            d.setX(SIZE);
            name = "l";
        }
        //Creation du "O"
        else if (block < 45){
            a.setX(XMAX / 2 - SIZE);
            //a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            b.setX(XMAX / 2 - SIZE);
            //c.setX(XMAX/2);
            c.setY(SIZE);
            d.setX(XMAX / 2);
            d.setY(SIZE);
            name = "l";
        }
        //Creation de la forme s
        else if (block < 60) {
            a.setX(XMAX / 2 + SIZE);
            //a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            b.setX(XMAX / 2 );
            //c.setX(XMAX/2);
            c.setY(SIZE);
            d.setX(XMAX / 2 - SIZE);
            d.setY(SIZE);
            name = "l";
        }
        //Creation de la forme t (à l'envers)
        else if (block < 75) {
            a.setX(XMAX / 2 - SIZE);
            //a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            b.setX(XMAX / 2 );
            //c.setX(XMAX/2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            name = "t";
        }
        //Creation de la forme z
        else if (block < 90) {
            a.setX(XMAX / 2 + SIZE);
            //a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            b.setX(XMAX / 2 + SIZE);
            //c.setX(XMAX/2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE + SIZE);
            d.setY(SIZE);
            name = "z";
        }
        //Creation de la forme i
        else {
            a.setX(XMAX / 2 - SIZE - SIZE);
            //a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            //c.setX(XMAX/2);
            d.setX(XMAX / 2 + SIZE);
            name = "i";
        }

        return new Form(a,b,c,d,name); // on affiche les blocs
    }

}
