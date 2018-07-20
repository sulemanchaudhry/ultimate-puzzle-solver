package uk.org.chaudhry.suleman.model

object Edges {
  sealed trait Shape
  sealed trait Orientation
  sealed trait JoinDirection
  case object IN extends Orientation
  case object OUT extends Orientation
  case object CROSS extends Shape
  case object HEX extends Shape
  case object ARROW1 extends Shape
  case object ARROW2 extends Shape
  case object LEFT_TO_RIGHT extends JoinDirection
  case object TOP_TO_BOTTOM extends JoinDirection

  case class EdgeShape(shape : Shape, orientation: Orientation)

//  sealed trait EdgeShape
//  case object CROSS_IN extends EdgeShape
//  case object CROSS_OUT extends EdgeShape
//  case object HEX_IN extends EdgeShape
//  case object HEX_OUT extends EdgeShape
//  case object UP_ARROW_IN extends EdgeShape
//  case object UP_ARROW_OUT extends EdgeShape
//  case object DOWN_ARROW_IN extends EdgeShape
//  case object DOWN_ARROW_OUT extends EdgeShape

}
