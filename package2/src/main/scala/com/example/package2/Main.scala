package com.example.package2

import com.example.package1.Main.package1

object Main extends App {
  def package2(): Unit = {
   println("I'm package2")
  }
  package2()
  package1()
}