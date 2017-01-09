package com.demo.sample

import org.junit._
import Assert._

import java.util.Arrays
import scala.collection.JavaConverters._

class CustomerScalaTest {

  @Before
  def setUp: Unit = {
  }

  @After
  def tearDown: Unit = {
  }

  @Test
  def testGetCustomerId = {
    System.out.println("getCustomerId")
    /*val instance = new Customer()
    val expResult: Integer = null
    val result: Integer = instance.getCustomerId()
    assertEquals(expResult, result)*/
  }

  @Test
  def scalaJavaCollections = {

    val javaList = Arrays.asList(1,2,3,4)

    val scalaList = javaList.asScala

    val javaListAgain = scalaList.asJava

    assert( javaList eq javaListAgain)

  }


}
