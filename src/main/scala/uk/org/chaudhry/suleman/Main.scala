package uk.org.chaudhry.suleman


import java.io.{File, PrintWriter}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import uk.org.chaudhry.suleman.model.Edges.{EdgeShape, JoinDirection, LEFT_TO_RIGHT, TOP_TO_BOTTOM}
import uk.org.chaudhry.suleman.model.{Edges, Piece}

object Main {



  def filter2x2(inputList : List[List[Piece]]) : List[List[Piece]] = {
    inputList
      .filter(pieceList => doPiecesJoin(pieceList, 0, 1, LEFT_TO_RIGHT))
      .filter(pieceList => doPiecesJoin(pieceList, 2, 3, LEFT_TO_RIGHT))
      .filter(pieceList => doPiecesJoin(pieceList, 0, 2, TOP_TO_BOTTOM))
      .filter(pieceList => doPiecesJoin(pieceList, 1, 3, TOP_TO_BOTTOM))
  }




  def filter3x3(inputList : List[List[Piece]]) : List[List[Piece]] = {
    inputList
      .filter(pieceList => doPiecesJoin(pieceList, 0, 1, LEFT_TO_RIGHT))
      .filter(pieceList => doPiecesJoin(pieceList, 1, 2, LEFT_TO_RIGHT))

      .filter(pieceList => doPiecesJoin(pieceList, 3, 4, LEFT_TO_RIGHT))
      .filter(pieceList => doPiecesJoin(pieceList, 4, 5, LEFT_TO_RIGHT))

      .filter(pieceList => doPiecesJoin(pieceList, 6, 7, LEFT_TO_RIGHT))
      .filter(pieceList => doPiecesJoin(pieceList, 7, 8, LEFT_TO_RIGHT))

      .filter(pieceList => doPiecesJoin(pieceList, 0, 3, TOP_TO_BOTTOM))
      .filter(pieceList => doPiecesJoin(pieceList, 1, 4, TOP_TO_BOTTOM))
      .filter(pieceList => doPiecesJoin(pieceList, 2, 5, TOP_TO_BOTTOM))

      .filter(pieceList => doPiecesJoin(pieceList, 3, 6, TOP_TO_BOTTOM))
      .filter(pieceList => doPiecesJoin(pieceList, 4, 7, TOP_TO_BOTTOM))
      .filter(pieceList => doPiecesJoin(pieceList, 5, 8, TOP_TO_BOTTOM))

    // this is only a partial filter, and is done for speed. still needs to be filtered top to bottom
  }


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

    val piece6 = new Piece("piece6", new EdgeShape(Edges.HEX, Edges.OUT),
      new EdgeShape(Edges.CROSS, Edges.OUT),
      new EdgeShape(Edges.CROSS, Edges.IN),
      new EdgeShape(Edges.HEX, Edges.IN))


    val piece5 = new Piece("piece5", new EdgeShape(Edges.CROSS, Edges.OUT),
      new EdgeShape(Edges.ARROW1, Edges.IN),
      new EdgeShape(Edges.ARROW1, Edges.IN),
      new EdgeShape(Edges.HEX, Edges.OUT))


    val pieceE = new Piece("pieceE", new EdgeShape(Edges.ARROW1, Edges.OUT),
      new EdgeShape(Edges.ARROW1, Edges.IN),
      new EdgeShape(Edges.HEX, Edges.IN),
      new EdgeShape(Edges.CROSS, Edges.OUT))


    val pieceC = new Piece("pieceC", new EdgeShape(Edges.HEX, Edges.IN),
      new EdgeShape(Edges.CROSS, Edges.IN),
      new EdgeShape(Edges.ARROW1, Edges.OUT),
      new EdgeShape(Edges.HEX, Edges.OUT))


    val piece7 = new Piece("piece7", new EdgeShape(Edges.ARROW2, Edges.OUT),
      new EdgeShape(Edges.HEX, Edges.IN),
      new EdgeShape(Edges.CROSS, Edges.IN),
      new EdgeShape(Edges.ARROW2, Edges.OUT))


    val grid2 : List[List[Piece]] = List(piece8, piece2, pieceF, piece4).permutations.toList
    var filterFunction : List[List[Piece]] => List[List[Piece]] = filter2x2
//     a list of all pieces available for the puzzle
    var piecesList: List[List[Piece]] = grid2

    // 3x3 is much slower; although solutiuons are generated
    val grid3 : List[List[Piece]] = List(piece8, piece2, piece6, pieceF, piece4, piece5, piece7, pieceC, pieceE).permutations.toList
    filterFunction = filter3x3
    piecesList = grid3

    // rotate each piece, which can be rotated three times
    // very naive approach; generate all possible combinations for checking
    val pieceOptionList : List[List[List[Piece]]] = piecesList map { pieceList => pieceList map {element => element.permutationsOfPiece }}

    // ref https://stackoverflow.com/a/14740340
    implicit class Crossable[X](xs: Traversable[X]) {
      def cross[Y](ys: Traversable[Y]) = xs.flatMap(x=>ys.map(y=>(x,y)))
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

    val tempFi = File.createTempFile("ultimate-puzzle-solver",".txt")
    println(s"Refer to ${tempFi.getAbsoluteFile} for results")

    val pw = new PrintWriter(tempFi)

    val matches : List[List[List[Piece]]]  = pieceOptionList.zipWithIndex.map(pieceOption => {
      val allElements = crossFn(pieceOption._1.head, pieceOption._1.tail)
      val allPieces : List[List[Piece]] = (allElements map (element => flattenTuple2(element.asInstanceOf[Tuple2[Any, Any]]))).toList
      val filtered : List[List[Piece]] = filterFunction.apply(allPieces)
      if (pieceOption._2%1000==0 || filtered.nonEmpty) println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"))+" processed (%07d".format(1+pieceOption._2)+"/%07d".format(pieceOptionList.size)+"):"+s"${allPieces.length} to ${filtered.length}")
      if (filtered.nonEmpty) {
        pw.write("List{\n")
        filtered.foreach(pieceList1 => pieceList1.foreach(piece=>pw.write(piece+"\n")))
        pw.write("}\n")
        pw.flush
      }
      filtered
    })


    pw.close
  }

  def doPiecesJoin(pieces : List[Piece], position1 : Int, position2 : Int, direction : JoinDirection) : Boolean = {
    val piece1 = pieces.lift(position1).get
    val piece2 = pieces.lift(position2).get
    direction match {
      case LEFT_TO_RIGHT =>
        val rightEdge = piece1.right
        val leftEdge = piece2.left
        rightEdge.shape==leftEdge.shape && rightEdge.orientation!=leftEdge.orientation
      case TOP_TO_BOTTOM =>
        val bottomEdge = piece1.bottom
        val topEdge = piece2.top
        bottomEdge.shape==topEdge.shape && bottomEdge.orientation != topEdge.orientation
    }
  }
}
