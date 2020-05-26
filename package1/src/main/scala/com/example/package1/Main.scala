package com.example.package1

import com.example.common.Main.common

object Main extends App  {
  def package1(): Unit = {
   println("I'm package 1")
  }
  package1()
  common()
}