package com.example.game

import android.graphics.Paint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.MotionEvent

import android.view.View


 class GameView(var c:Context,var game1:Game1):View(c)
{
   public  var myPaint: Paint? = null
    public var speed = 1
    public var time = 0
    public var score = 0
    public var myCarPosition= 0


    public val othercars=ArrayList<HashMap<String,Any>>()

    var viewWidth=0
    var viewHeight=0
    init{
     myPaint= Paint()
    }


 override fun onDraw(canvas: Canvas) {
  super.onDraw(canvas)
  viewWidth=this.measuredWidth
  viewHeight=this.measuredHeight

  if(time % 700<10+speed)
  {
   val map=HashMap<String,Any>()
   map["lane"]=(0..2).random()
   map["startTime"]=time
   othercars.add(map)

  }
  time=time+10+speed
  val carWidth=viewWidth/5
  val carHeight=carWidth+10
  myPaint!!.style=Paint.Style.FILL
  val d=resources.getDrawable(R.drawable.yelcar,null)

  d.setBounds(
     myCarPosition *viewWidth/3+viewWidth/15+25,
    viewHeight-2-carHeight,
   myCarPosition*viewWidth/3+viewWidth/15+carWidth-25,
   viewHeight-2
  )
  d.draw(canvas!!)
  myPaint!!.color=Color.GREEN
  var highScore= 0
  for (i in othercars.indices)
  {
   try{
    val carX=othercars[i]["lane"]as Int *viewWidth/3+viewWidth/15
    var carY=time-othercars[i]["startTime"] as Int
     val d2=resources.getDrawable(R.drawable.orcar,null)

    d2.setBounds(
     carX+25,carY-carHeight,carX+carWidth-25,carY

    )
    d2.draw(canvas)
    if(othercars[i]["lane"] as Int==myCarPosition)
    {
     if (carY>viewHeight-2-carHeight && carY<viewHeight -2){
      game1.closeGame(score)
     }
    }
    if(carY>viewHeight +carHeight)
    {
     othercars.removeAt(i)
     score++
     speed=1+Math.abs(score/8)
     if(score>highScore)
     {
      highScore=score
     }
    }
   }
   catch (e:Exception){
    e.printStackTrace()
   }
  }
  myPaint!!.color=Color.WHITE
  myPaint!!.textSize=40f
  canvas.drawText("Score:$score",80f,80f,myPaint!!)
  canvas.drawText("Speed:$speed",380f,80f,myPaint!!)
  invalidate()
 }

 override fun onTouchEvent(event: MotionEvent?): Boolean {
  when(event!!.action){
  MotionEvent.ACTION_DOWN->{
   val x1=event.x
   if(x1<viewWidth/2){
    if(myCarPosition>0){
     myCarPosition--
    }
   }
   if (x1>viewWidth/2){
      if (myCarPosition<2){
       myCarPosition++
      }
   }
   invalidate()
  }
   MotionEvent.ACTION_UP->{}
  }
  return true
 }


}
