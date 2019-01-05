/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package echecs;

import Vue.FenetreJeu;
import echecs.Pieces.Dame;
import echecs.Pieces.Fou;
import echecs.Pieces.Piece;
import echecs.Pieces.Tour;
import java.awt.Color;
import java.util.ArrayList;
import java.util.stream.IntStream;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

/**
 *
 * @author lyes
 * cette classe joue également le role du contrleur avec la classe partie qui
 * elle intercepte les clicks des joueur et les envoie a la classe mediateur
 * pour leur associer le traitemet correspondant 
 * 
 */
public class Mediateur {
    // declarations 
    // variable permettant de savoir si c'est le premier click su une case ou pas 
    boolean firstClick = true;
    int x, y, newx, newy, pos;
    FenetreJeu fj;
    final Border border = BorderFactory.createLineBorder(Color.RED, 3);
    Couleur tour = Couleur.BLANC;
    Deplacements dep = new Deplacements();
    AI ai;
    // une arraylist stockant les pièces capturées de chaque joueur
    ArrayList<Piece> CapturedBlackPiece;
    ArrayList<Piece> CapturedWhitePiece;
    boolean moved =false;
    UpdateVue uv;
    Test test;
    int compteurBlanc=0,compteurNoir=0;
    boolean threat = false;
    boolean mat = false;
    
// constructeur mediateur
    public Mediateur(FenetreJeu fj) {
        this.fj = fj;
        test =  new Test();
        pos = fj.ta.getCaretPosition();
        CapturedBlackPiece = new ArrayList<Piece>();
        CapturedWhitePiece = new ArrayList<Piece>();
        uv = new UpdateVue(fj);
        ai = new AI();
    }
    
    

    // methode recevant les clicks des joueurs 
    public void getClick(int p, int o) {
  
          if(firstClick){
            
              // tester si la case contient une piece 
               if (fj.echiquier.cases[p][o].getPiece() != null){
                        // vérifier si la culeur de la pièce correspond au joueur qui doit jouer 
                   if (tour == fj.echiquier.cases[p][o].getPiece().getCouleur()){
                       // mettre la bordure de la case sélectionnée en rouge 
                          fj.labels[p][o].setBorder(border);
                          // si la pièce est pion 
                          if (fj.echiquier.cases[p][o].getPiece().getType() == Type.PION)
                          { System.out.println(fj.echiquier.cases[p][o].getPiece().getId());
                          // on affiche les possibilités de mouvement possible 
                          uv.showPossibilities(dep.moves(fj.echiquier.cases[p][o].getPiece().getType(),p,o,tour,fj,fj.echiquier.cases[p][o].getPiece().getId()));
       
                          }
                          // si la piece n'est pas pion 
                          //la séparation due a l'id que prend la piece pion et n'en prennent les autre
                          else{uv.showPossibilities(dep.moves(fj.echiquier.cases[p][o].getPiece().getType(),p,o,tour,fj,8));}
                          x=p; y=o;
                        
                          firstClick=false;
                           if (test.isKingThreatened(tour,fj) == true){
                              
                                  threat = true;   
                          }
                           
                          }else{
                          fj.labels[x][y].setBorder(null);
                          x = p; y = o;
                          fj.labels[x][y].setBorder(border);
                          
                         
              }
              }else{
                   
                   fj.labels[x][y].setBorder(null);
                   x = p; y = o;
                   fj.labels[x][y].setBorder(border);
               }
       
          }else{
                // au cas ou si c'est le deuxième click (pour passer ici il faut forcément que la case du premier
                // ait contenue une piece )
              System.out.println("second");
              newx = p;newy=o;
              
                // si le roi est menacé 
              if (threat == true){
                 // on vérifie si le mouvement est sûr 
                  if (test.isMoveSafe(tour, x,y, newx,newy,fj)){
                      // on vérifie si la case de deuxième click se trouve bien dans la liste des possibilités 
                      if (contains(dep.listOfPossibilities,newx,newy)){
                      // teste si y a capture ou pas 
                       if (contains(dep.listOfCaptureMoves,newx,newy)){
                           // enregistrer les pièces capturée par les deux joueurs 
                       if (fj.echiquier.cases[newx][newy].getPiece().getCouleur() == Couleur.BLANC){
                           CapturedWhitePiece.add(fj.echiquier.cases[newx][newy].getPiece());
                       }else{
                             CapturedBlackPiece.add(fj.echiquier.cases[newx][newy].getPiece());
                       }  }
                        // limiter le mouvement en avant en une seule case après son premier mouvement
                         if(fj.echiquier.cases[x][y].getPiece() != null){
                         
                      if(fj.echiquier.cases[x][y].getPiece().getType() == Type.PION){
                         
                          if (fj.echiquier.cases[x][y].getPiece().getCouleur() == Couleur.BLANC){
                              int id =  fj.echiquier.cases[x][y].getPiece().getId();
                              
                               if (!IntStream.of(dep.whitePawnsIds).anyMatch(k -> k == id)){
                                   
                              dep.whitePawnsIds[compteurBlanc] = fj.echiquier.cases[x][y].getPiece().getId();
                              compteurBlanc++;
                               }
                          }else{
                              int id =  fj.echiquier.cases[x][y].getPiece().getId();
                               if (!IntStream.of(dep.blackPawnsIds).anyMatch(k -> k == id)){
                              dep.blackPawnsIds[compteurNoir] = fj.echiquier.cases[x][y].getPiece().getId();
                              compteurNoir++;
                               }
                          }
                            }
                      }
                      // effectuer le mouvement 
                      uv.executeMove(newx, newy, x, y,false);
                        // teste si echec et mat
                      
                   
                      // promotion d'un pion 
                       if (fj.echiquier.cases[newx][newy].getPiece() != null){
                            
                            
                          if (fj.echiquier.cases[newx][newy].getPiece().getType() == Type.PION){
                              if (newx == 0 && newy >=0 && newy <= 7){
                                  JOptionPane jop = new JOptionPane(), jop1 = new JOptionPane();
                                  String[] option = {"Dame","Tour","FOU"};
                                  int rang = jop.showOptionDialog(null,"choisi une piece","changement de piece",
                                  JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[1]);
                       
                              if (option[rang] == "Dame"){
                                  Dame dame = new Dame(newx,newy,Type.DAME,"/home/lyes/NetBeansProjects/Chess/Icone/DB.gif",tour);
                                  fj.echiquier.cases[newx][newy].setPiece(dame);
                                  uv.promotion(newx,newy,"/home/lyes/NetBeansProjects/Chess/Icone/DB.gif");
                              }
                              if (option[rang] == "FOU"){
                                  Fou fou = new Fou(newx,newy,Type.FOU,"/home/lyes/NetBeansProjects/Chess/Icone/FB.gif",tour);
                                  fj.echiquier.cases[newx][newy].setPiece(fou);
                                  uv.promotion(newx,newy,"/home/lyes/NetBeansProjects/Chess/Icone/FB.gif");
                              }
                              if(option[rang]== "Tour"){
                                  Tour tourP = new Tour(newx,newy,Type.TOUR,"/home/lyes/NetBeansProjects/Chess/Icone/TB.gif",tour);
                                  fj.echiquier.cases[newx][newy].setPiece(tourP);
                                  uv.promotion(newx,newy,"/home/lyes/NetBeansProjects/Chess/Icone/TB.gif");
                              }
                           
                              
                             
                              }else if(newx == 7 && newy <= 7){
                                  JOptionPane jop = new JOptionPane(), jop1 = new JOptionPane();
                                  String[] option = {"Dame","Tour","FOU"};
                                  int rang = jop.showOptionDialog(null,"choisi une piece","changement de piece",
                                  JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[1]);
                       
                                     if (option[rang] == "Dame"){
                                  Dame dame = new Dame(newx,newy,Type.DAME,"/home/lyes/NetBeansProjects/Chess/Icone/DN.gif",tour);
                                  fj.echiquier.cases[newx][newy].setPiece(dame);
                                  uv.promotion(newx,newy,"/home/lyes/NetBeansProjects/Chess/Icone/DN.gif");
                              }
                              if (option[rang] == "FOU"){
                                  Fou fou = new Fou(newx,newy,Type.FOU,"/home/lyes/NetBeansProjects/Chess/Icone/FN.gif",tour);
                                  fj.echiquier.cases[newx][newy].setPiece(fou);
                                  uv.promotion(newx,newy,"/home/lyes/NetBeansProjects/Chess/Icone/FN.gif");
                              }
                              if(option[rang]== "Tour"){
                                  Tour tourP = new Tour(newx,newy,Type.TOUR,"/home/lyes/NetBeansProjects/Chess/Icone/TN.gif",tour);
                                  fj.echiquier.cases[newx][newy].setPiece(tourP);
                                  uv.promotion(newx,newy,"/home/lyes/NetBeansProjects/Chess/Icone/TNdd.gif");
                              }
                              }
                          }
                      }
                    
                      
                      // mettre a jour le plateau
                      firstClick = true;
                      //enlever la bordure rouge
                      fj.labels[x][y].setBorder(null);
                      // cacher les possibilités affichées 
                      uv.hidePossibilities(dep.listOfPossibilities);
                      // changement de tour 
                       if (tour == Couleur.BLANC){
                           tour = Couleur.NOIR;
                           fj.ta.insert("aux noirs de jouer\n\n",pos);
                           
                       }else{
                           tour = Couleur.BLANC;
                           fj.ta.insert("aux blancs de jouer\n\n",pos);
                       }
                       // réitialiser les arraylist de la classe déplacements 
                      if(dep.listOfFeasiblePossibilities != null){dep.listOfCaptureMoves.clear();} 
                      if(dep.listOfPossibilities !=  null){dep.listOfPossibilities.clear();}
                      if(dep.listOfCaptureMoves != null){dep.listOfCaptureMoves.clear();}
                      
                      moved = true;
                      // remettre threat (menace a false)
                      threat = false;   
                  }
                      
                       if(moved){
                  moved = false;     
                 }else{
                  fj.labels[x][y].setBorder(null);
                  x = newx; y = newy;
                  fj.labels[x][y].setBorder(border);
                  uv.hidePossibilities(dep.listOfPossibilities);
                  if(dep.listOfPossibilities !=  null){dep.listOfPossibilities.clear();}
                  if(dep.listOfCaptureMoves != null){dep.listOfCaptureMoves.clear();}
                   if(dep.listOfFeasiblePossibilities != null){dep.listOfCaptureMoves.clear();} 
                  firstClick = true;
                  
                  getClick(x,y);
                 }
                 
                  }else{
                    // tester si y a echec et mat après le mouvement 
                      if (test.checkMate(tour, fj)){
                          JOptionPane jop = new JOptionPane(), jop1 = new JOptionPane();
                          String[] option = {"Sortir", "Rejouer"};
                          int rang = jop.showOptionDialog(null, "Fin de la partie, echec et mate \n le joueur " + this.tour + " a gagné, bravo!!", "Fin de partie",
                          JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
                          jop1.showMessageDialog(null, "Vous allez jouer " + option[rang], "validation choix", JOptionPane.INFORMATION_MESSAGE);
                          if (option[rang] == "Rejouer") {
                            // lancer une nouvelle partie
                             Echecs.main(option);
                          } else {
                             // quitter le jeu
                             System.exit(0);
                          }

                     
                  }
                     
                  // en cas ou le roi est menacé
                    JOptionPane jop2 = new JOptionPane();
                    jop2.showMessageDialog(null, "Ton roi est menacé" , "Alerte", JOptionPane.INFORMATION_MESSAGE); 
                     fj.labels[x][y].setBorder(null);
                     x = newx; y = newy;
                     fj.labels[x][y].setBorder(border);
                     uv.hidePossibilities(dep.listOfPossibilities);
                     if(dep.listOfPossibilities !=  null){dep.listOfPossibilities.clear();}
                     if(dep.listOfCaptureMoves != null){dep.listOfCaptureMoves.clear();}
                     firstClick = true;
                  }
                    // teste si echec et mat
                     
              }else{
                  
                   // si la piece cliquée c'est le roi on teste le roque sinon déplacement normal 
                  if (fj.echiquier.cases[x][y].getPiece().getType() == Type.ROI){
              
                      uv.hidePossibilities(dep.listOfPossibilities);
                      if(dep.listOfFeasiblePossibilities != null){dep.listOfCaptureMoves.clear();} 
                      if(dep.listOfPossibilities !=  null){dep.listOfPossibilities.clear();}
                      if(dep.listOfCaptureMoves != null){dep.listOfCaptureMoves.clear();}
                      if (dep.isRoque(x,y,newx,newy,tour,fj,test)){
                          
                
                       // si roque
                       if (dep.RoqueType == "Roque1"){
                           // petit Roque
      
                     
                       uv.executePetitRoque(tour);
                   
                         // mettre a jour le plateau
                      firstClick = true;
                      fj.labels[x][y].setBorder(null);
                      
                     
                      // changement de tour 
                       if (tour == Couleur.BLANC){
                           tour = Couleur.NOIR;
                           dep.whiteKingFirstMove = false;
                           fj.ta.insert("aux noirs de jouer\n\n",pos);
                
                       }else{
                           tour = Couleur.BLANC;
                           fj.ta.insert("aux blancs de jouer\n\n",pos);
                           dep.blackKingFirstMove = false;
                       }
                    
                      
                      moved = true;
                       }else{
             
                           // grand "Roque
                           uv.executeGrandRoque(tour);
                             // mettre a jour le plateau
                      firstClick = true;
                      fj.labels[x][y].setBorder(null);
    
                      // changement de tour 
                       if (tour == Couleur.BLANC){
                           tour = Couleur.NOIR;
                           fj.ta.insert("aux noirs de jouer\n\n",pos);
                           dep.whiteKingFirstMove = false;
                       }else{
                           tour = Couleur.BLANC;
                           fj.ta.insert("aux blancs de jouer\n\n",pos);
                           dep.blackKingFirstMove = false;
                       }
                     
                      moved = true;
                       }
                  }else{
                    
                       // deplacement normal d'un roi    
                      uv.executeMove(newx, newy, x, y, false);
                        // mettre a jour le plateau
                      firstClick = true;
                      fj.labels[x][y].setBorder(null);
                     
                      // changement de tour 
                       if (tour == Couleur.BLANC){
                           tour = Couleur.NOIR;
                           dep.whiteKingFirstMove = false;
                           fj.ta.insert("aux noirs de jouer\n\n",pos);
                       }else{
                           tour = Couleur.BLANC;
                           fj.ta.insert("aux blancs de jouer\n\n",pos);
                           dep.blackKingFirstMove = false;
                       }
                      if(dep.listOfFeasiblePossibilities != null){dep.listOfCaptureMoves.clear();} 
                      if(dep.listOfPossibilities !=  null){dep.listOfPossibilities.clear();}
                      if(dep.listOfCaptureMoves != null){dep.listOfCaptureMoves.clear();}
                      moved = true;
                  }
                  }else{
 
                 
              
                  // test si le mouvement est sûr
              if (test.isMoveSafe(tour, x, y, newx, newy, fj)){
                
                  if (fj.echiquier.cases[x][y].getPiece() != null){
                          
                  //int[] xp = {newx,newy};
                  
                  if (contains(dep.listOfPossibilities,newx,newy)){
                      
                    
                       if (contains(dep.listOfCaptureMoves,newx,newy)){
                           // enregistrer les pièces capturée par les deux joueurs 
                       if (fj.echiquier.cases[newx][newy].getPiece().getCouleur() == Couleur.BLANC){
                           CapturedWhitePiece.add(fj.echiquier.cases[newx][newy].getPiece());
                       }else{
                             CapturedBlackPiece.add(fj.echiquier.cases[newx][newy].getPiece());
                       }  }
                        // limiter le mouvement d'un pion en avant en une seule case après son premier mouvement
                         if(fj.echiquier.cases[x][y].getPiece() != null){
                         
                         if(fj.echiquier.cases[x][y].getPiece().getType() == Type.PION){
                         
                          if (fj.echiquier.cases[x][y].getPiece().getCouleur() == Couleur.BLANC){
                              int id =  fj.echiquier.cases[x][y].getPiece().getId();
                              
                               if (!IntStream.of(dep.whitePawnsIds).anyMatch(k -> k == id)){
                                   
                              dep.whitePawnsIds[compteurBlanc] = fj.echiquier.cases[x][y].getPiece().getId();
                              compteurBlanc++;
                               }
                          }else{
                              int id =  fj.echiquier.cases[x][y].getPiece().getId();
                               if (!IntStream.of(dep.blackPawnsIds).anyMatch(k -> k == id)){
                              dep.blackPawnsIds[compteurNoir] = fj.echiquier.cases[x][y].getPiece().getId();
                              compteurNoir++;
                               }
                          }
                            }
                      }
                      // effectuer le mouvement 
                      uv.executeMove(newx, newy, x, y,false);
                      
                      
                      
                        if (fj.echiquier.cases[newx][newy].getPiece() != null){
                            
                            // promotion d'un pion
                          if (fj.echiquier.cases[newx][newy].getPiece().getType() == Type.PION){
                              if (newx == 0 && newy >=0 && newy <= 7){
                                  JOptionPane jop = new JOptionPane(), jop1 = new JOptionPane();
                                  String[] option = {"Dame","Tour","FOU"};
                                  int rang = jop.showOptionDialog(null,"choisi une piece","changement de piece",
                                  JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[1]);
                       
                              if (option[rang] == "Dame"){
                                  Dame dame = new Dame(newx,newy,Type.DAME,"/home/lyes/NetBeansProjects/Chess/Icone/DB.gif",tour);
                                  fj.echiquier.cases[newx][newy].setPiece(dame);
                                  uv.promotion(newx,newy,"/home/lyes/NetBeansProjects/Chess/Icone/DB.gif");
                              }
                              if (option[rang] == "FOU"){
                                  Fou fou = new Fou(newx,newy,Type.FOU,"/home/lyes/NetBeansProjects/Chess/Icone/FB.gif",tour);
                                  fj.echiquier.cases[newx][newy].setPiece(fou);
                                  uv.promotion(newx,newy,"/home/lyes/NetBeansProjects/Chess/Icone/FB.gif");
                              }
                              if(option[rang]== "Tour"){
                                  Tour tourP = new Tour(newx,newy,Type.TOUR,"/home/lyes/NetBeansProjects/Chess/Icone/TB.gif",tour);
                                  fj.echiquier.cases[newx][newy].setPiece(tourP);
                                  uv.promotion(newx,newy,"/home/lyes/NetBeansProjects/Chess/Icone/TB.gif");
                              }
                           
                              
                             
                              }else if(newx == 7 && newy <= 7){
                                  JOptionPane jop = new JOptionPane(), jop1 = new JOptionPane();
                                  String[] option = {"Dame","Tour","FOU"};
                                  int rang = jop.showOptionDialog(null,"choisi une piece","changement de piece",
                                  JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,option,option[1]);
                       
                                     if (option[rang] == "Dame"){
                                  Dame dame = new Dame(newx,newy,Type.DAME,"/home/lyes/NetBeansProjects/Chess/Icone/DN.gif",tour);
                                  fj.echiquier.cases[newx][newy].setPiece(dame);
                                  uv.promotion(newx,newy,"/home/lyes/NetBeansProjects/Chess/Icone/DN.gif");
                              }
                              if (option[rang] == "FOU"){
                                  Fou fou = new Fou(newx,newy,Type.FOU,"/home/lyes/NetBeansProjects/Chess/Icone/FN.gif",tour);
                                  fj.echiquier.cases[newx][newy].setPiece(fou);
                                  uv.promotion(newx,newy,"/home/lyes/NetBeansProjects/Chess/Icone/FN.gif");
                              }
                              if(option[rang]== "Tour"){
                                  Tour tourP = new Tour(newx,newy,Type.TOUR,"/home/lyes/NetBeansProjects/Chess/Icone/TN.gif",tour);
                                  fj.echiquier.cases[newx][newy].setPiece(tourP);
                                  uv.promotion(newx,newy,"/home/lyes/NetBeansProjects/Chess/Icone/TNdd.gif");
                              }
                              }
                          }
                      }
                    
                      // mettre a jour le plateau
                      firstClick = true;
                      fj.labels[x][y].setBorder(null);
                      uv.hidePossibilities(dep.listOfPossibilities);
                      // changement de tour 
                       if (tour == Couleur.BLANC){
                           tour = Couleur.NOIR;
                           fj.ta.insert("aux noirs de jouer\n\n",pos);
                       }else{
                           tour = Couleur.BLANC;
                           fj.ta.insert("aux blancs de jouer\n\n",pos);
                       }
                      if(dep.listOfFeasiblePossibilities != null){dep.listOfCaptureMoves.clear();} 
                      if(dep.listOfPossibilities !=  null){dep.listOfPossibilities.clear();}
                      if(dep.listOfCaptureMoves != null){dep.listOfCaptureMoves.clear();}
                      moved = true;
                  }
                  // dans le cas ou le joueur clicke sur 2 piece differente ui lui apartiennent donc on rappele la fonction getClick pour afficher les 
                  // les nouvelles positions possible
                 if(moved){
                  moved = false;     
                 }else{
                      
                  fj.labels[x][y].setBorder(null);
                  x = newx; y = newy;
                  fj.labels[x][y].setBorder(border);
                  uv.hidePossibilities(dep.listOfPossibilities);
                  if(dep.listOfPossibilities !=  null){dep.listOfPossibilities.clear();}
                  if(dep.listOfCaptureMoves != null){dep.listOfCaptureMoves.clear();}
                  firstClick = true;
                  getClick(x,y);
                 }
              }else{
                  fj.labels[x][y].setBorder(null);
                  x = newx; y = newy;
                  fj.labels[x][y].setBorder(border);
                  uv.hidePossibilities(dep.listOfPossibilities);
                  if(dep.listOfPossibilities !=  null){dep.listOfPossibilities.clear();}
                  if(dep.listOfCaptureMoves != null){dep.listOfCaptureMoves.clear();}
              }
              
             }else{
                    if (test.checkMate(tour, fj)){
                        System.out.println("je suis la 1");
                           JOptionPane jop = new JOptionPane(), jop1 = new JOptionPane();
                String[] option = {"Sortir", "Rejouer"};
                int rang = jop.showOptionDialog(null, "Fin de la partie, echec et mate \n le joueur " + tour + " a gagné, bravo!!", "Fin de partie",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
                jop1.showMessageDialog(null, "Vous allez jouer " + option[rang], "validation choix", JOptionPane.INFORMATION_MESSAGE);
                if (option[rang] == "Rejouer") {
                    // lancer une nouvelle partie
                    Echecs.main(option);
                } else {
                    // quitter le jeu
                    System.exit(0);
                }

                       
                    }
                  fj.labels[x][y].setBorder(null);
                  x = newx; y = newy;
                  fj.labels[x][y].setBorder(border);
                  uv.hidePossibilities(dep.listOfPossibilities);
                  if(dep.listOfPossibilities !=  null){dep.listOfPossibilities.clear();}
                  if(dep.listOfCaptureMoves != null){dep.listOfCaptureMoves.clear();}
          }}
              
          }
       
      }
    }  
      
  
    
    
  // contains vérifie si un mouvement existe dans une liste donnée 
  public boolean contains( ArrayList<int[]> tab, int v,int w){
    
       if(tab != null){
            
           int size = tab.size();
          
           int[] possibilitie;
           for (int k = 0;k<size;k++){
           possibilitie = tab.get(k);
      
            System.out.println(possibilitie[0]+" , "+possibilitie[1]+" et "+v+" et "+w);
           if (possibilitie[0] == v && possibilitie[1] == w){
                System.out.println("taille true"+tab.size() );
              return true;
                
           }
          }
       }
          System.out.println("taille false"+tab.size() );
      return false;
  }
}
