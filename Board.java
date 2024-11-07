import java.lang.Math;
public class Board{
    /*
    0 = air
    1 = pawn
    2 = knight
    3 = bishop
    4 = rook
    5 = queen
    6 = king
    */
    int WKingX = 4;
    int BKingX = 4;
    int WKingY = 7;
    int BKingY = 0;
    int lastOX,lastOY,lastDX,lastDY;
    private int[][] board = new int[8][8];
    private boolean[][] Moved = new boolean[8][8];
    
    public Board(){
        setBoard("rnbqkbnr/pppppp2/8/1B1N4/4P1Q1/6P1/PPPP1P1P/R1B1K2R",0,0);
    }
    public Board(Board other){
        copyBoard(other);
    }
    
    public void copyBoard(Board other){
        for(int i = 0;i < 64;i++){
            board[i%8][i/8] = other.board[i%8][i/8];
            Moved[i%8][i/8] = other.Moved[i%8][i/8];
        }
        lastOX = other.lastOX;
        lastOY = other.lastOY;
        lastDX = other.lastDX;
        lastDY = other.lastDY;
        WKingX = other.WKingX;
        BKingX = other.BKingX;
        WKingY = other.WKingY;
        BKingY = other.BKingY;
    }
    public void setBoard(String s,int x,int y){  
        while(!(x == 8 && y == 7)){
            String f = String.valueOf(s.charAt(0));
            s = s.substring(1);
            if(f.equals("P")){
                board[x][y] = 1;
            }
            if(f.equals("N")){
                board[x][y] = 2;
            }
            if(f.equals("B")){
                board[x][y] = 3;
            }
            if(f.equals("R")){
                board[x][y] = 4;
            }
            if(f.equals("Q")){
                board[x][y] = 5;
            }
            if(f.equals("K")){
                board[x][y] = 6;
            }
            if(f.equals("p")){
                board[x][y] = 7;
            }
            if(f.equals("n")){
                board[x][y] = 8;
            }
            if(f.equals("b")){
                board[x][y] = 9;
            }
            if(f.equals("r")){
                board[x][y] = 10;
            }
            if(f.equals("q")){
                board[x][y] = 11;
            }
            if(f.equals("k")){
                board[x][y] = 12;
            }
            if(f.equals("/")){
                x = -1;
                y++;
            }
            else{
                try{
                    for(int i =1;i < Integer.valueOf(f);i++){
                        board[x][y] = 0;
                        x++;
                    } 
                }
                catch(Exception e){

                }
            }
            x++;
        }
    }
    
    public boolean CanMove(int OrX,int OrY,int DeX,int DeY){
        if((board[OrX][OrY] < 7 && board[DeX][DeY] < 7 && board[DeX][DeY] != 0) || (board[OrX][OrY] > 6 && board[DeX][DeY] > 6) || board[OrX][OrY] == 0){
            return false;
        }
        if(board[OrX][OrY] == 1){
            if(OrY - 1 == DeY){
                if(CanEnPassent(OrX,OrY,DeX,DeY)){
                    return true;
                }
                if(OrX == DeX && board[DeX][DeY] == 0){
                    return true;
                }
                if(Math.abs(OrX - DeX) == 1 && board[DeX][DeY] > 6){
                    return true;
                }
            }
            if(OrY - 2 == DeY && OrX == DeX && !Moved[OrX][OrY] && board[OrX][OrY - 1] == 0 && board[DeX][DeY] == 0){
                return true;
            }
        }            
        if(board[OrX][OrY] == 7){
            if(OrY + 1 == DeY){
                if(OrX == DeX && board[DeX][DeY] == 0){
                    return true;                
                }
                if(Math.abs(OrX - DeX) == 1 && board[DeX][DeY] > 0){
                    return true;
                }
            }
            if(OrY + 2 == DeY && OrX == DeX && !Moved[OrX][OrY] && board[OrX][OrY + 1] == 0 && board[DeX][DeY] == 0){
                return true;
            }
        }
        if(board[OrX][OrY] == 2 || board[OrX][OrY] == 8){
            if(Math.abs(OrX - DeX) == 2 && Math.abs(OrY - DeY) == 1){
                return true;
            }
            if(Math.abs(OrX - DeX) == 1 && Math.abs(OrY - DeY) == 2){
                return true;
            }
            return false;
        }
        if(board[OrX][OrY] == 3 || board[OrX][OrY] == 9){
            return CanMoveBishop(OrX,OrY,DeX,DeY);
        }
        if(board[OrX][OrY] == 4 || board[OrX][OrY] == 10){
            return CanMoveRook(OrX,OrY,DeX,DeY);
        }
        if(board[OrX][OrY] == 5 || board[OrX][OrY] == 11){
            return (CanMoveBishop(OrX,OrY,DeX,DeY) || CanMoveRook(OrX,OrY,DeX,DeY));
        }
        if(board[OrX][OrY] == 6 || board[OrX][OrY] == 12){
            if(Math.abs(OrX - DeX) < 2 && Math.abs(OrY - DeY) < 2){
                return true;
            }
            if(CanCastle(OrX,OrY,DeX,DeY)){
                return true;
            }
        }
        return false;
    }
    public boolean CanCastle(int OrX,int OrY,int DeX,int DeY){
        if(OrY == DeY && !Moved[OrX][OrY]){
            if(DeX == 6 && !Moved[DeX + 1][DeY] && board[DeX - 1][DeY] == 0 && board[DeX][DeY] == 0){
                if(!OccupiedBy(5,DeY,DeY / 7) && !OccupiedBy(6,DeY,DeY / 7)){
                    return true;
                }
            }
            if(DeX == 2 && !Moved[0][DeY] && board[1][DeY] == 0 && board[2][DeY] == 0 && board[3][DeY] == 0){
                if(!OccupiedBy(2,DeY,DeY / 7) && !OccupiedBy(3,DeY,DeY / 7)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean CanMoveBishop(int OrX,int OrY,int DeX,int DeY){
        if(OrX == DeX || OrY == DeY || Math.abs(OrX - DeX) != Math.abs(OrY - DeY)){
            return false;
        }
        for(int i = 0;i < Math.abs(DeY - OrY) -1;i++){
            if(OrX > DeX && OrY > DeY && board[DeX +i+1][DeY+i+1] > 0){
                return false;
            }
            if(OrX > DeX && OrY < DeY && board[DeX +i+1][DeY-i-1] > 0){
                return false;
            }
            if(OrX < DeX && OrY > DeY && board[DeX -i-1][DeY+i+1] > 0){
                return false;
            }
            if(OrX < DeX && OrY < DeY && board[DeX -i-1][DeY-i-1] > 0){
                return false;
            }
        }
        return true;
    }
    
    public boolean CanMoveRook(int OrX,int OrY,int DeX,int DeY){
        if(OrX != DeX && OrY != DeY){
                return false;
            }
        for(int i = 0;i <Math.abs(OrX + OrY - DeX - DeY) -1;i++){
            if(OrX > DeX && board[OrX - (i+1)][OrY] > 0){
                return false;                
            }
            if(OrX < DeX && board[OrX + i+1][OrY] > 0){
                return false;
            }
            if(OrY > DeY && board[OrX][OrY-(i+1)] > 0){    
                return false;
            }
            if(OrY < DeY && board[OrX][OrY + i+1] > 0){
                return false;
            }
        }
        return true;
    }
    
    public boolean CanEnPassent(int OrX,int OrY,int DeX,int DeY){
        if(Math.abs(lastDX - OrX) == 1 && lastDY == OrY && (board[lastDX][lastDY] == 1 || board[lastDX][lastDY] == 7) && Math.abs(lastDY - lastOY) == 2){
            return true;        
        }
        return false;
    }
    
    public boolean OccupiedBy(int x ,int y,int color){
        // 1 = black
        // 0 = white
        for(int i = 0;i < 64;i++){
            if(board[i % 8][i / 8] == 0){
                continue;
            }
            if(color == board[i % 8][i / 8] /7 && CanMove(i % 8,i / 8,x,y)){
                return true;
            }
        }
        return false;
    }
    
    public boolean BoardEqual(Board other){
        for(int i = 0;i<64;i++){
            if(board[i%8][i/8] != other.board[i%8][i/8]){
                return false;
            }
        }
        return  true;
    }
    
    static int AllPosition = 0;
    double Evaluation = 0;
    public Board Play(int depth,boolean Color){
        double bestEval = -300;
        Board best = this;
        boolean HasMoved = false;
        int nox = 0,noy = 0,ndx = 0,ndy = 0;
        if(Color == false){
            bestEval = 300;
        }
        if(depth == 0){ 
            AllPosition++;
            Evaluation = Evaluation(0);
            retur
            return this;
        }
        for(int i =0;i < 64;i++){
            if((board[i%8][i/8] == 0) ||board[i%8][i/8] / 7 == 0 != Color){
                continue;
            }
            for(int k = 0;k <64;k++){
                Board Test = new Board(this);
                int NextDepth = depth-1;
                if(Test.Move(i%8,i/8,k%8,k/8)){
                    HasMoved = true;
                    Board Test2 = Test.Play(NextDepth,!Color); 
                    double testEval = Test2.Evaluation;
                    if(((Color && testEval > bestEval)||(!Color && testEval < bestEval) ) ||(testEval == bestEval)){
                        nox = i%8;
                        noy = i/8;
                        ndx = k%8;
                        ndy = k/8;
                        bestEval = testEval;
                        best = Test2;
                    }
                }
            }   
        }
        if(!HasMoved){
            if(isChecked(BKingX,BKingY) || isChecked(WKingX,WKingY)){
                return Play(1,!Color);
            }
        }
        if(depth == 4){
            Move(nox,noy,ndx,ndy);
            return this;
        }
        return best;
    }
    
    public boolean Move(int OrX,int OrY,int DeX,int DeY){
        if(CanMove(OrX,OrY,DeX,DeY)){
            Board Copy = new Board(this);
            if(board[OrX][OrY] % 6 == 0){
                if(DeX == 6 && OrX == 4){
                    Move(DeX + 1,DeY,DeX - 1,DeY);
                }
                if(DeX == 2 && OrX == 4){
                    Move(DeX -2,DeY,DeX + 1,DeY);
                }
            }
            if((board[OrX][OrY] == 1 || board[OrX][OrY] == 7) && OrX != DeX && board[DeX][DeY] == 0){
                board[DeX][OrY] = 0;
            }
            board[DeX][DeY] = board[OrX][OrY];
            board[OrX][OrY] = 0;
            
            if(board[DeX][DeY] == 1 && DeY == 0){
                board[DeX][DeY] = 5;
            }
            if(board[DeX][DeY] == 7 && DeY == 7){
                board[DeX][DeY] = 11;
            }
            if(board[DeX][DeY] == 6){
                WKingX = DeX;
                WKingY = DeY;
            }
            if(board[DeX][DeY] == 12){
                BKingX = DeX;
                BKingY = DeY;
            }
            Moved[DeX][DeY] = true;
            lastOX = OrX;
            lastOY = OrY;
            lastDX = DeX;
            lastDY = DeY;
            if(isChecked(BKingX,BKingY) && board[DeX][DeY] >6){
                copyBoard(Copy);
                return false;
            }
            if(isChecked(WKingX,WKingY) && board[DeX][DeY] < 7){
                copyBoard(Copy);
                return false;
            }
            return true;
        }
        return false;
    }
    public boolean isChecked(int x,int y){
        for(int i = 0;i < 64;i++){
            if(CanMove(i%8,i/8,x,y)){
                return true;
            }
        }
        return false;
    }
    
    public double Evaluation(int i){
        double sul = 0;
        if(board[i % 8][i / 8] == 1){
            sul += 1;
        }
        if(board[i % 8][i / 8] == 2 || board[i % 8][i / 8] == 3){
            sul += 3;
        }
        if(board[i % 8][i / 8] == 4){
            sul += 5;
        }
        if(board[i % 8][i / 8] == 5){
            sul += 9;
        }
        if(board[i % 8][i / 8] == 6){
            sul += 200;
        }
        if(board[i % 8][i / 8] == 7){
            sul -= 1;
        }
        if(board[i % 8][i / 8] == 8 || board[i % 8][i / 8] == 9){
            sul -= 3;
        }
        if(board[i % 8][i / 8] == 10){
            sul -= 5;
        }
        if(board[i % 8][i / 8] == 11){
            sul -= 9;
        }
        if(board[i % 8][i / 8] == 12){
            sul -= 200;
        }
        if(i < 63){
            return sul + Evaluation(i +1);
        }
        return sul;
    }
    public int[][] getBoard(){
        return board;
    }
    public String toString(){
        String str = "";
        for(int i = 0;i < 64;i++){
            if(i%8 == 7){
                str += board[i%8][i/8]+"\n------------------------\n";
                continue;
            }
            str+= board[i%8][i/8]+" | ";
        }
        
        return str;
    }
    public static void main(String [] args){
    }
}