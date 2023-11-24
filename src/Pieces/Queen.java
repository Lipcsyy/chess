package Pieces;
import Main.ChessBoard;
import Main.ChessModel;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Queen extends Piece {

    public Queen( ChessModel _model, int row, int col, boolean isWhite ) {
        this( _model, row, col, isWhite, col * _model.getTileSize(), row * _model.getTileSize() );
    }

    public Queen( ChessModel _model, int row, int col, boolean isWhite, int xPos, int yPos ) {

        super(_model, isWhite, row, col, xPos, yPos);

        this.name = isWhite ? 'Q' : 'q';

        String fileName = (isWhite ? "w_" : "b_") + Character.toLowerCase( name );
        String path = "./src/Resources/" + fileName + ".png";

        try {
            this.image = ImageIO.read( new File(path) );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }

    }

    @Override
    public boolean isValidMove( int row, int col ) {
        return this.row == row || this.col == col || Math.abs( this.col - col ) == Math.abs( this.row - row );
    }

    @Override
    public boolean collidesWithPiece( int row, int col ) {

        if ( this.row == row || this.col == col  ) {

            if ( this.col > col ) {
                for ( int c = this.col - 1 ; c > col; c-- ) {
                    if( model.getPiece( this.row, c ) != null ) {
                        return true;
                    }
                }
            }

            //right
            if ( this.col < col ) {
                for ( int c = this.col + 1 ; c < col; c++ ) {
                    if( model.getPiece( this.row, c ) != null ) {
                        return true;
                    }
                }
            }

            //up
            if ( this.row > row ) {
                for ( int r = this.row - 1 ; r > row; r-- ) {
                    if( model.getPiece( r , this.col ) != null ) {
                        return true;
                    }
                }
            }

            //down
            if ( this.row < row ) {
                for ( int r = this.row + 1 ; r < row; r++ ) {
                    if( model.getPiece( r, this.col ) != null ) {
                        return true;
                    }
                }
            }
        }
        else {
            //up left = row greater col less
            if ( this.col > col && this.row > row ) {
                for ( int i = 1; i < Math.abs( this.col - col ) ; i++ ) {
                    if ( model.getPiece( this.row - i, this.col - i ) != null )
                        return true;
                }
            }

            //up right row greater col greater
            if ( this.col < col && this.row > row ) {
                for ( int i = 1; i < Math.abs( this.col - col ); i++ ) {
                    if ( model.getPiece( this.row - i, this.col + i ) != null )
                        return true;
                }
            }

            //down left
            if ( this.col > col && this.row < row ) {
                for ( int i = 1; i < Math.abs( this.col - col ) ; i++ ) {
                    if ( model.getPiece( this.row + i, this.col - i ) != null )
                        return true;
                }
            }

            //down right row greater col greater
            if ( this.col < col && this.row < row ) {
                for ( int i = 1; i < Math.abs( this.col - col ) ; i++ ) {
                    if ( model.getPiece( this.row + i, this.col + i ) != null )
                        return true;
                }
            }
        }


        return false;
    }

    @Override
    public Piece copy() {
        return new Queen( model, row, col, isWhite, xPos, yPos );
    }
}
