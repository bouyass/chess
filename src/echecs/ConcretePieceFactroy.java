/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import echecs.Pieces.Cavalier;
import echecs.Pieces.Dame;
import echecs.Pieces.Fou;
import echecs.Pieces.Piece;
import echecs.Pieces.Pion;
import echecs.Pieces.Roi;
import echecs.Pieces.Tour;

/**
 *
 * @author lyes
 */
public class ConcretePieceFactroy implements PieceFactory {

    @Override
    public Piece createPawn(int x, int y, Type type, String icone, Couleur couleur, int id) {

        Piece pion = new Pion(x, y, type, icone, couleur, id);
        return pion;
    }

    @Override
    public Piece createKing(int x, int y, Type type, String icone, Couleur couleur) {
        Piece roi = new Roi(x, y, type, icone, couleur);
        return roi;
    }

    @Override
    public Piece createKnight(int x, int y, Type type, String icone, Couleur couleur) {
        Piece cavalier = new Cavalier(x, y, type, icone, couleur);
        return cavalier;
    }

    @Override
    public Piece createRook(int x, int y, Type type, String icone, Couleur couleur) {
        Piece tour = new Tour(x, y, type, icone, couleur);
        return tour;
    }

    @Override
    public Piece createBishop(int x, int y, Type type, String icone, Couleur couleur) {
        Piece fou = new Fou(x, y, type, icone, couleur);
        return fou;
    }

    @Override
    public Piece createQueen(int x, int y, Type type, String icone, Couleur couleur) {
        Piece dame = new Dame(x, y, type, icone, couleur);
        return dame;
    }

}
