package pt.ipbeja.po2.connectfour.model;

import pt.ipbeja.po2.connectfour.View;

/**
 * @author André Dâmaso
 * @number 15307
 */

public class ConnectFourModel {

    public final int LINES = 6;
    public final int COLS = 7;

    private final View VIEW;

    private final Cell[][] boardData;
    private int turnCounter;


    public ConnectFourModel(View view){
        this.VIEW = view;
        this.boardData = new Cell[this.LINES][this.COLS];
        this.fillBoard();
    }

    private void fillBoard() {
        System.out.format("Creating a %dx%d board\n", this.LINES, this.COLS);

        for (int line = 0; line < this.LINES; line++) {
            for (int col = 0; col < this.COLS; col++) {
                this.boardData[line][col] = Cell.EMPTY;
            }
        }
    }


    public int cellSelected(int col) {

        int line = this.dropPiece(col); //a linha selecionada pelo botao toma um novo valor
        if (isFull(col)) { // Verifica se a coluna esta cheia, se tiver vazia preenche
            this.updateBoardState(line, col); //se nao tiver faz o procedimento de jogar
            Cell cell = this.getCell(line, col); // Obtemos o valor do Place nessa posição (ver enum Place)
            this.VIEW.update(cell, line, col);
            this.turnCounter++;
            //this.updateBoardState(line, col);

            this.checkBoardState(line, col);

        }

        return line;



    }


    private boolean isFree(int line, int col) {

        return boardData[line][col] == Cell.EMPTY;

    }

    public Cell getCell(int line, int col) {

        return boardData[line][col];

    }

    private void updateBoardState(int line, int col) {
        if (this.turnCounter % 2 == 0) {
            this.boardData[line][col] = Cell.PLAYER1;
        } else {
            this.boardData[line][col] = Cell.PLAYER2;
        }
        // ou boardData[line][col] = (turnCounter % 2 == 0) ? Place.X : Place.O;
    }

    /**
     * metedo que retorna um inteiro neste caso a linha
     * @param col
     * @returna linha que deixa cair a peça
     */
    public int dropPiece(int col) {

        int line = 0; //criação da variavel linha para ser devolvida ao Board
        int lineDrop = LINES-1; //ultima linha do tabuleiro

        while (lineDrop >= 0)
        {
            if (isFree(lineDrop, col)) { //se a cell estiver vazia
                line += lineDrop; // a linha toma o valor da linha da celula vazia
                break;
            }
            lineDrop--; //se nao a linha decrementa para fazer uma gravidade
        }
        return line;

        // ou boardData[line][col] = (turnCounter % 2 == 0) ? Place.X : Place.O;
    }

    /**
     * metodo isFull retorna a condição se a coluna está cheia
     * @param col
     * @return
     */
    public boolean isFull(int col){

        if(boardData[0][col] != Cell.EMPTY) { // se na primeira linha da coluna selecionada for diferente de vazia retorna falso
            return false;
        }
        return true;

    }

    private void checkBoardState(int line, int col) {
        if (inWinnableTurn(this.turnCounter)) {
            // Só vale a pena começar a verificar condições de vitória a partir do número mínimo de jogadas para algum dos jogadores vencer
            // Ex. para um tabuleiro 3x3, é necessário haver pelo menos 5 jogadas para vencer
            //if (hasWinningLinePlayer1()){
                 // 0 ou 1
            //}

            if (isWinPosition(line, col)) {
                //this.VIEW.playerWins((this.turnCounter - 1) % 2, line, col); // 0 ou 1
                this.VIEW.playerWin((this.turnCounter - 1) % 2); // 0 ou 1
            } else if (isDrawPosition()) {
                this.VIEW.draw();
            }
        }
    }

    /**
     * Checks if it is possible to win in this turn
     * @param turn the turn to check
     * @return true if can win false otherwise
     */
    public boolean inWinnableTurn(int turn)
    {
        return turn >= 7; //necessarios 7 jogadas
    }


    /**
     * Check if the game ended in a draw
     * @return true if the draw condition was found
     */
    public boolean isDrawPosition() {
        return turnCounter == this.LINES * this.COLS;
    }

    /**
     * Check for winning condition
     * @param line
     * @param col
     * @return true if a winning condition was found
     */
    public boolean isWinPosition(int line, int col) {
        // Verificar se a jogada resultou numa sequência vencedora
        // Os métodos são invocados sequencialmente e quando um deles devolve 'true' os seguintes já não são invocados
        return (hasWinningLinePlayer1() ||
                hasWinningLinePlayer2() ||
                hasWinningColumnPlayer1() ||
                hasWinningColumnPlayer2() ||
                hasWinningMainDiagonalPlayer1() ||
                hasWinningMainDiagonalPlayer2() ||
                hasWinnningAntiDiagonalPlayer1() ||
                hasWinnningAntiDiagonalPlayer2());
        // Por exemplo:: Se hasWinningLine -> false ...continua... se hasWinningColumn -> true,
        // termina aqui e devolve true.
    }

    /**
     * Verifica se a existe condição de ganhar por linha por parte do Player1
     * @return true se existir a condição
     */
    private boolean hasWinningLinePlayer1() {

        int count = 0;

        for (int i = 0; i < LINES; i++) {
            for (int j = 0; j < COLS; j++) {
                if (boardData[i][j] == Cell.PLAYER1){ //cada vez que for selecionado uma celula de seguida em linha conta uma vez
                    count++;

                    if (count >= 4){ //se forem contadas 4 celulas iguais seguidas numa linha retorna verdade
                        return true;
                    }
                }else {
                    count = 0; //caso contrário o contador fica a 0
                }
            }

        }
        return false;
    }

    /**
     * Verifica se a existe condição de ganhar por linha por parte do Player2
     * @return true se existir a condição
     */
    public boolean hasWinningLinePlayer2() {

        int count = 0;

        for (int i = 0; i < LINES; i++) {
            for (int j = 0; j < COLS; j++) {
                if (boardData[i][j] == Cell.PLAYER2){ //cada vez que for selecionado uma celula de seguida em linha conta uma vez
                    count++;

                    if (count >= 4){ //se forem contadas 4 celulas iguais seguidas numa linha retorna verdade
                        return true;
                    }
                }else {
                    count = 0; //caso contrário o contador fica a 0
                }
            }

        }
        return false;
    }


    /**
     * Verifica se a existe condição de ganhar por coluna por parte do Player1
     * @return true se existir a condição
     */
    private boolean hasWinningColumnPlayer1() {

        int count = 0;

        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i < LINES; i++) {
                if (boardData[i][j] == Cell.PLAYER1){ //cada vez que for selecionado uma celula de seguida em coluna conta uma vez
                    count++;

                    if (count >= 4){ //se forem contadas 4 celulas iguais seguidas numa coluna retorna verdade
                        return true;
                    }
                }else {
                    count = 0; //caso contrário o contador fica a 0
                }
            }

        }
        return false;
    }

    /**
     * Verifica se a existe condição de ganhar por coluna por parte do Player2
     * @return true se existir a condição
     */
    public boolean hasWinningColumnPlayer2() {

        int count = 0;

        for (int j = 0; j < COLS; j++) {
            for (int i = 0; i < LINES; i++) {
                if (boardData[i][j] == Cell.PLAYER2){ //cada vez que for selecionado uma celula de seguida em coluna conta uma vez
                    count++;

                    if (count >= 4){ //se forem contadas 4 celulas iguais seguidas numa coluna retorna verdade
                        return true;
                    }
                }else {
                    count = 0; //caso contrário o contador fica a 0
                }
            }

        }
        return false;
    }

    /**
     * Verifica se a existe condição de ganhar por diagonal por parte do Player1
     * @return true se existir a condição
     */
    public boolean hasWinningMainDiagonalPlayer1() {

        for (int i = 3; i < LINES; i++){ //so pode ganhar a partir da linha 4, pois ante é impossivel formar diagonal de 4 celulas
            for (int j = 0; j < COLS -3; j++){ //não é possivel ganhar em colunas que estejam depois da coluna 4
                if (this.boardData[i][j] == Cell.PLAYER1 &&
                        this.boardData[i-1][j+1] == Cell.PLAYER1 &&
                        this.boardData[i-2][j+2] == Cell.PLAYER1 &&
                        this.boardData[i-3][j+3] == Cell.PLAYER1)
                    return true;
            }
        }

        return false;
    }

    /**
     * Verifica se a existe condição de ganhar por diagonal por parte do Player2
     * @return true se existir a condição
     */
    public boolean hasWinningMainDiagonalPlayer2() {

        for (int i = 3; i < LINES; i++){ //so pode ganhar a partir da linha 4, pois ante é impossivel formar diagonal de 4 celulas
            for (int j = 0; j < COLS -3; j++){ //não é possivel ganhar em colunas que estejam depois da coluna 4
                if (this.boardData[i][j] == Cell.PLAYER2 &&
                        this.boardData[i-1][j+1] == Cell.PLAYER2 &&
                        this.boardData[i-2][j+2] == Cell.PLAYER2 &&
                        this.boardData[i-3][j+3] == Cell.PLAYER2)
                    return true;
            }
        }

        return false;
    }

    /**
     * Verifica se a existe condição de ganhar por antidiagonal por parte do Player1
     * @return true se existir a condição
     */
    public boolean hasWinnningAntiDiagonalPlayer1() { //verifica se os campos na diagonal pedida se são iguais

        for (int i = 3; i < LINES; i++){
            for (int j = 3; j < COLS; j++){
                if (this.boardData[i][j] == Cell.PLAYER1 &&
                        this.boardData[i-1][j-1] == Cell.PLAYER1 &&
                        this.boardData[i-2][j-2] == Cell.PLAYER1 &&
                        this.boardData[i-3][j-3] == Cell.PLAYER1)
                    return true;
            }
        }
        return false;
    }

    /**
     * Verifica se a existe condição de ganhar por antidiagonal por parte do Player2
     * @return true se existir a condição
     */
    public boolean hasWinnningAntiDiagonalPlayer2() { //verifica se os campos na diagonal pedida se são iguais

        for (int i = 3; i < LINES; i++){
            for (int j = 3; j < COLS; j++){
                if (this.boardData[i][j] == Cell.PLAYER2 &&
                        this.boardData[i-1][j-1] == Cell.PLAYER2 &&
                        this.boardData[i-2][j-2] == Cell.PLAYER2 &&
                        this.boardData[i-3][j-3] == Cell.PLAYER2)
                    return true;
            }
        }
        return false;
    }

    /**
     *Verifica a pontuação do vencedor
     * @return o numero de vezes que o Player precisou de jogar para ganhar
     */
    public int Score(){

        int count = 0;
        int count2 = 0;

        for (int i = 0; i < LINES; i++) {
            for (int j = 0; j < COLS; j++) {
                if (boardData[i][j]==Cell.PLAYER1 ){ //contar quantas vezes joga o player 1
                    count++;
                }
                if (boardData[i][j] == Cell.PLAYER2) {//contar quantas vezes joja o player 2
                    count2++;
                }

            }

        }
        if (hasWinningMainDiagonalPlayer1()||hasWinningLinePlayer1()||hasWinningColumnPlayer1()||hasWinnningAntiDiagonalPlayer1()){
            return 42 - (count); //se o player 1 ganhar, retorna a pontuação das vezes que jogou
        }

        if (hasWinningMainDiagonalPlayer2()||hasWinningLinePlayer2()||hasWinningColumnPlayer2()||hasWinnningAntiDiagonalPlayer2()){
            return 42 - (count2); //se o player 2 ganhar, retorna a pontuação das vezes que jogou
        }

        return 0;
    }


}
