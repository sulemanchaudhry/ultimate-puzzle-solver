package uk.org.chaudhry.suleman

import uk.org.chaudhry.suleman.model.Edges.{EdgeShape, JoinDirection, LEFT_TO_RIGHT, TOP_TO_BOTTOM}
import uk.org.chaudhry.suleman.model.{Edges, Piece}

object Main {

  def main(args: Array[String]): Unit = {

    val piece8 = new Piece("piece8", new EdgeShape(Edges.ARROW2, Edges.OUT),
      new EdgeShape(Edges.ARROW2, Edges.OUT),
      new EdgeShape(Edges.ARROW1, Edges.IN),
      new EdgeShape(Edges.CROSS, Edges.IN))

    val piece2 = new Piece("piece2", new EdgeShape(Edges.ARROW1, Edges.IN),
      new EdgeShape(Edges.HEX, Edges.OUT),
      new EdgeShape(Edges.ARROW2, Edges.OUT),
      new EdgeShape(Edges.ARROW2, Edges.IN))

    val pieceF = new Piece("pieceF", new EdgeShape(Edges.ARROW1, Edges.OUT),
      new EdgeShape(Edges.CROSS, Edges.IN),
      new EdgeShape(Edges.ARROW2, Edges.IN),
      new EdgeShape(Edges.CROSS, Edges.OUT))

    val piece4 = new Piece("piece4", new EdgeShape(Edges.ARROW2, Edges.IN),
      new EdgeShape(Edges.HEX, Edges.IN),
      new EdgeShape(Edges.HEX, Edges.OUT),
      new EdgeShape(Edges.CROSS, Edges.OUT))

//    val piece6 = new Piece("piece6", new EdgeShape(Edges.HEX, Edges.OUT),
//      new EdgeShape(Edges.CROSS, Edges.OUT),
//      new EdgeShape(Edges.CROSS, Edges.IN),
//      new EdgeShape(Edges.HEX, Edges.IN))
//
//
//    val piece5 = new Piece("piece5", new EdgeShape(Edges.CROSS, Edges.OUT),
//      new EdgeShape(Edges.ARROW1, Edges.IN),
//      new EdgeShape(Edges.ARROW1, Edges.IN),
//      new EdgeShape(Edges.HEX, Edges.OUT))
//
//
//    val pieceE = new Piece("pieceE", new EdgeShape(Edges.ARROW1, Edges.OUT),
//      new EdgeShape(Edges.ARROW1, Edges.IN),
//      new EdgeShape(Edges.HEX, Edges.IN),
//      new EdgeShape(Edges.CROSS, Edges.OUT))
//
//
//    val pieceC = new Piece("pieceC", new EdgeShape(Edges.HEX, Edges.IN),
//      new EdgeShape(Edges.CROSS, Edges.IN),
//      new EdgeShape(Edges.ARROW1, Edges.OUT),
//      new EdgeShape(Edges.HEX, Edges.OUT))
//
//
//    val piece7 = new Piece("piece7", new EdgeShape(Edges.ARROW2, Edges.OUT),
//      new EdgeShape(Edges.HEX, Edges.IN),
//      new EdgeShape(Edges.CROSS, Edges.IN),
//      new EdgeShape(Edges.ARROW2, Edges.OUT))
//



    val pieces: List[Piece] = List(piece2, piece4, piece8, pieceF)


    val pieceOptions : List[List[Piece]] = pieces map { piece => piece.permutationsOfPiece }

    val piecePermutations : List[List[List[Piece]]]  = pieceOptions.permutations.toList


    // ref https://stackoverflow.com/a/14740340
    implicit class Crossable[X](xs: Traversable[X]) {
      def cross[Y](ys: Traversable[Y]) = for { x <- xs; y <- ys } yield (x, y)
    }

    def flattenTuple2(tuple2 : Tuple2[Any, Any]): List[Piece] = {
      tuple2 match {
        case (element1 : Piece, element2 : Piece) => List(element1, element2)
        case (element1 : Piece, element2 : Tuple2[Any, Any]) => element1 :: flattenTuple2(element2)
      }
    }



    // messy; flattened using flattenTuple2
    def crossFn(head : List[Piece], tail : List[List[Piece]]) : Traversable[Any] = {
      tail match {
        case xs :: Nil => head cross xs
        case xs::ys => head cross crossFn(xs, ys)
      }
    }


    val matches : List[List[List[Piece]]] = piecePermutations.map(pieceOptions => {
      val allElements = crossFn(pieceOptions.head, pieceOptions.tail)

      val allPieces : List[List[Piece]] = (allElements map (element => flattenTuple2(element.asInstanceOf[Tuple2[Any, Any]]))).toList
      allPieces
        .filter(pieceList => doPiecesJoin(pieceList, 0, 1, LEFT_TO_RIGHT))
        .filter(pieceList => doPiecesJoin(pieceList, 2, 3, LEFT_TO_RIGHT))
        .filter(pieceList => doPiecesJoin(pieceList, 0, 2, TOP_TO_BOTTOM))
        .filter(pieceList => doPiecesJoin(pieceList, 1, 3, TOP_TO_BOTTOM))

    }).filter(list1 => list1.nonEmpty)
    println(matches.length)
  }

  def doPiecesJoin(pieces : List[Piece], position1 : Int, position2 : Int, direction : JoinDirection) : Boolean = {
    val piece1 = pieces.lift(position1).get
    val piece2 = pieces.lift(position2).get
    direction match {
      case LEFT_TO_RIGHT =>
        val rightEdge = piece1.right
        val leftEdge = piece2.left
//        println(s"rightShape: $piece1 leftShape: $piece2")
        rightEdge.shape==leftEdge.shape && rightEdge.orientation!=leftEdge.orientation
      case TOP_TO_BOTTOM =>
        val bottomEdge = piece1.bottom
        val topEdge = piece2.top
        bottomEdge.shape==topEdge.shape && bottomEdge.orientation != topEdge.orientation
    }
  }
}
