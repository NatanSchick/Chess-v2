 import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;

public class ChessFrame extends JFrame{
    public Board chess = new Board();
    JButton[][] board = new JButton[8][8];
    public ChessFrame(){
        setVisible(true);
        setSize(800,800);
        setResizable(false);
        setExtendedState(JFrame. MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBoard(0,0);
        setIcons(0,0);
        setLayout(null);
    }
    public void setBoard(int x,int y){
        board[x][y] = new JButton();
        setAction(x,y);
        board[x][y].setBounds((x *100),(y*100),100,100);
        board[x][y].setLayout(null);
        board[x][y].setFocusable(false);
        board[x][y].setBorderPainted(true);
        add(board[x][y]);
        if((x + y)%2==0){
            board[x][y].setBackground(new Color(235, 236, 208));
        }
        if((x + y)%2==1){
            board[x][y].setBackground(new Color(119, 149, 86));
        }
        if(x == 7 && y < 7){
            setBoard(0,y+1);
        }
        if(x != 7){
            setBoard(x+1,y);
        }
    }
    public void setIcons(int x,int y){
        if(chess.getBoard()[x][y] == 7){
            board[x][y].setIcon(new ImageIcon("Black_Pawn.png"));
        }
        if(chess.getBoard()[x][y] == 10){
            board[x][y].setIcon(new ImageIcon("Black_Rook.png"));
        }
        if(chess.getBoard()[x][y] == 8){
            board[x][y].setIcon(new ImageIcon("Black_Knight.png"));
        }
        if(chess.getBoard()[x][y] == 9){
            board[x][y].setIcon(new ImageIcon("Black_Bishop.png"));
        }
        if(chess.getBoard()[x][y] == 11){
            board[x][y].setIcon(new ImageIcon("Black_Queen.png"));
        }
        if(chess.getBoard()[x][y] == 12){
            board[x][y].setIcon(new ImageIcon("Black_King.png"));
        }
        if(chess.getBoard()[x][y] == 1){
            board[x][y].setIcon(new ImageIcon("White_Pawn.png"));
        }
        if(chess.getBoard()[x][y] == 4){
            board[x][y].setIcon(new ImageIcon("White_Rook.png"));
        }
        if(chess.getBoard()[x][y] == 2){
            board[x][y].setIcon(new ImageIcon("White_Knight.png"));
        }
        if(chess.getBoard()[x][y] == 3){
            board[x][y].setIcon(new ImageIcon("White_Bishop.png"));
        }
        if(chess.getBoard()[x][y] == 5){
            board[x][y].setIcon(new ImageIcon("White_Queen.png"));
        }
        if(chess.getBoard()[x][y] == 6){
            board[x][y].setIcon(new ImageIcon("White_King.png"));
        }
        if(chess.getBoard()[x][y] == 0){
            board[x][y].setIcon(null);
        }
        if(x == 7 && y < 7){
            setIcons(0,y + 1);
        }
        if(x != 7){
            setIcons(x+1,y);
        }
    }
    boolean Clicked;
    int preX,preY = 0;
    public void setAction(int x,int y){
        board[x][y].addActionListener(new ActionListener(){
            int newX = x;
            int newY = y;
            @Override
            public void actionPerformed(ActionEvent e){
                if(Clicked && chess.Move(preX,preY,newX,newY)){
                    Clicked = false;
                    chess.Play(4,false);
                    System.out.println("Evaluation is: "+chess.Evaluation(0));
                    setIcons(0,0);
                    return;
                }
                if(Clicked){
                    Clicked = false;
                }
                if(!Clicked && chess.getBoard()[newX][newY] != 0){
                    Clicked = true;
                    preX = newX;
                    preY = newY;
                }
            }
        });
    }
    
            
    
    public static void main(String [] args){
        ChessFrame f = new ChessFrame();
    }
}