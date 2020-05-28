package com.example.package2

import com.example.common.Main.common

object Main extends App {
  def package2(): Unit = {
   println("I'm package2")
  }
  package2()
  common()
}