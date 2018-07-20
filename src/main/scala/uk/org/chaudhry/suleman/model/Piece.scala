package uk.org.chaudhry.suleman.model

import uk.org.chaudhry.suleman.model.Edges.EdgeShape

case class Piece(name : String, top : EdgeShape, right : EdgeShape, bottom : EdgeShape, left: EdgeShape) {
  def rotateRight(model : Piece) : Piece = new Piece(model.name, model.left, model.top, model.right, model.bottom)
  def flipHorizontal(model : Piece) : Piece = new Piece(model.name, model.top, model.left, model.bottom, model.right)
  def permutationsOfPiece : List[Piece] = {
    val rotated = List(this,
                        rotateRight(this),
                        rotateRight(rotateRight(this)),
                        rotateRight(rotateRight(rotateRight(this))) )
    val flipped  = rotated map {piece => flipHorizontal(piece)}
    rotated ++ flipped
  }
  override def toString = s"Model(name=$name top=$top right=$right bottom=$bottom left=$left)"
}
