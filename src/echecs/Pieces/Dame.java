/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs.Pieces;

import javax.swing.Icon;
import echecs.Couleur;
import echecs.Type;
/**
 *
 * @author lyes
 */
public class Dame extends Piece{
     public Dame(int x, int y, Type type, String icone,Couleur couleur){
        this.x = x;
        this.y = y;
        this.type = type;
        this.icone = icone;
        this.couleur = couleur;
        
    }
}
