package com.example.tetris;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//Class permettant de colorer les formes géometriques
//rectangle a , b, c,d correspond a une unité/partie avec lesquelle on pourra creer une forme plus complex
public class Form {
    Rectangle a;//Bloc unitaire a
    Rectangle b;
    Rectangle c;
    Rectangle d;
    Color color;
    private String name; // nom de la forme (J , L , O , S , T , Z , I)
    public int form = 1; //form n°1 , lors de la rotation de la forme

    //Constructeur 1
    public Form(Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    //Construceteur 2
    //Une classe peut avoir autant de constructeurs que l'on a le courage de lui en créer, dès l'instant qu'ils ont des signatures différentes, c'est-à-dire des paramètres différents.
    public Form(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.name = name;


        //set color of the stones
        switch (name) {
            case "j":
                color = Color.SLATEGREY; //forme J en gris
                break;
            case "l":
                color = Color.DARKGOLDENROD; //forme L en jaune
                break;
            case "o":
                color = Color.INDIANRED; //forme carré en rouge
                break;
            case "s":
                color = Color.FORESTGREEN; //forme S en vert
                break;
            case "t":
                color = Color.CADETBLUE; //forme T à l'envers en bleu
                break;
            case "z":
                color = Color.HOTPINK; //forme Z en rose
                break;
            case "i":
                color = Color.SLATEGREY; //forme I en marron
                break;
        }
        //Chaque forme sera composé de 4 blocs unitaire(a,b,c,d) pour creer les formes complexe (J,L,O,S,T,Z et I)
        this.a.setFill(color);
        this.b.setFill(color);
        this.c.setFill(color);
        this.d.setFill(color);
    }

    //getter
    //pour rappel on utilise un getter quand la declaration d'une variable est private
    public String getName() {
        return name;
    }
    //Rotation de chaque forme à l'aide du clavier , 4 rotations possitbles
    public void changeForm(){
        if(form !=4){ //a revoir avec form >4
            form++;
        }else {
            form=1;
        }
    }


}