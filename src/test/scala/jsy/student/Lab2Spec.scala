package jsy.student

import jsy.lab2.Lab2Like
import jsy.lab2.ast._
import jsy.tester.JavascriptyTester
import org.scalatest._

/*
 * This file contains a number of *Spec classes that define a set of
 * tests.
 *
 * All of the tests are gathered together in Lab2Suite.
 */

class Lab2Spec(lab2: Lab2Like) extends FlatSpec {
  import lab2._

  "And" should "return true only if both expressions are true" in {
    val t = B(true)
    val f = B(false)
    assert(eval(Binary(And,t,t)) === t)
    assert(eval(Binary(And,t,f)) === f)
    assert(eval(Binary(And,f,t)) === f)
    assert(eval(Binary(And,f,f)) === f)
  } 
 
  it should "return non-intuitive results from differing types" in {
    val e1 = N(0)
    val e2 = B(true)
    val e3 = eval(Binary(And, e1, e2))
    assert(e3 === N(0))
  }
 
  "Or" should "return true if either or both expressions are true" in {
    val t = B(true)
    val f = B(false)
    assert(eval(Binary(Or,t,t)) === t)
    assert(eval(Binary(Or,f,t)) === t)
    assert(eval(Binary(Or,t,f)) === t)
    assert(eval(Binary(Or,f,f)) === f)
  }

  it should "return non-intuitive results from differing types" in {
    val e1 = N(5)
    val e2 = B(false)
    val e3 = eval(Binary(Or, e1, e2))
    assert(e3 === N(5))
  }
  
  "Plus" should "add two number values and return a number" in {
    val e1 = N(1)
    val e2 = N(2)
    val e3 = eval(Binary(Plus, e1, e2))
    assert(e3 === N(3))
  }

  it should "append two string values and return a string" in {
    val e1 = S("a")
    val e2 = S("b")
    val e3 = eval(Binary(Plus, e1, e2))
    assert(e3 === S("ab"))
  }

  it should "add two binary values and return a number" in {
    val e1 = B(true)
    val e2 = B(false)
    val e3 = eval(Binary(Plus, e1, e2))
    assert(e3 === N(1))

    val e4 = B(true)
    val e5 = B(true)
    val e6 = eval(Binary(Plus, e4, e5))
    assert(e6 === N(2))

    val e7 = B(false)
    val e8 = B(false)
    val e9 = eval(Binary(Plus, e7, e8))
    assert(e9 === N(0))
  }

  it should "append a string and an undefined values and return a string" in {
    val e1 = S("a")
    val e2 = Undefined
    val e3 = eval(Binary(Plus, e1, e2))
    assert(e3 === S("aundefined"))
  }

  "Minus" should "subtract two number values and return a number" in {
    val e1 = N(3)
    val e2 = N(1)
    val e3 = eval(Binary(Minus, e1, e2))
    assert(e3 === N(2))

    val e4 = B(true)
    val e5 = N(2)
    val e6 = eval(Binary(Minus, e4, e5))
    assert(e6 === N(-1))
  }

  it should "return NaN for all types that are not a number" in {
    val e4 = S("test")
    val e5 = N(2)
    val e6 = eval(Binary(Minus, e4, e5))
    assert(toNumber(e6).equals(Double.NaN))

    val e7 = Undefined
    val e8 = N(2)
    val e9 = eval(Binary(Minus, e7, e8))
    assert(toNumber(e9).equals(Double.NaN))
  }

  "Times" should "multiply two number values and return a number" in {
    val e1 = N(3)
    val e2 = N(2)
    val e3 = eval(Binary(Times, e1, e2))
    assert(e3 === N(6))

    val e4 = B(true)
    val e5 = N(2)
    val e6 = eval(Binary(Times, e4, e5))
    assert(e6 === N(2))
  }

  it should "return NaN for all types that are not a number" in {
    val e4 = S("test")
    val e5 = N(2)
    val e6 = eval(Binary(Times, e4, e5))
    assert(toNumber(e6).equals(Double.NaN))

    val e7 = Undefined
    val e8 = N(2)
    val e9 = eval(Binary(Times, e7, e8))
    assert(toNumber(e9).equals(Double.NaN))
  }

  "Div" should "Divide two number values and return a number" in {
    val e1 = N(10)
    val e2 = N(2)
    val e3 = eval(Binary(Div, e1, e2))
    assert(e3 === N(5))

    val e4 = B(true)
    val e5 = N(8)
    val e6 = eval(Binary(Div, e5, e4))
    assert(e6 === N(8))

    val e7 = N(10)
    val e8 = N(0)
    val e9 = eval(Binary(Div, e7, e8))
    assert(toNumber(e9).equals(Double.PositiveInfinity))
  }

  it should "return NaN for all types that are not a number" in {
    val e4 = S("test")
    val e5 = N(2)
    val e6 = eval(Binary(Div, e4, e5))
    assert(toNumber(e6).equals(Double.NaN))

    val e7 = Undefined
    val e8 = N(2)
    val e9 = eval(Binary(Div, e7, e8))
    assert(toNumber(e9).equals(Double.NaN))
  }
  it should "divide two number values and return a number" in {
    val e1 = N(8)
    val e2 = N(5)
    val e3 = eval(Binary(Div, e1, e2))
    assert(e3 === N(1.6))
  }

  "Arithmetic Operators" should "produce non-intuitive solutions given differing expression types" in {
    val e1 = B(true)
    val e2 = N(7)
    assert(eval(Binary(Plus,e1,e2)) == N(8))
  }

  "Eq" should "return true if two numerical values are the same" in {
    val e1 = N(5)
    val e2 = N(5)
    val e3 = eval(Binary(Eq, e1, e2))
    assert(e3 === B(true))

    val e4 = S("5")
    val e5 = eval(Binary(Eq, e1, e4))
    assert(e5 === B(false))

    val e6 = S("a")
    val e7 = S("a")
    val e8 = eval(Binary(Eq, e6, e7))
    assert(e8 === B(true))

    val e9 = S("b")
    val e10 = eval(Binary(Eq, e9, e7))
    assert(e10 === B(false))
  }
  
  it should "return false if two numerical values are not the same" in {
    val e1 = N(5)
    val e2 = N(7)
    val e3 = eval(Binary(Eq, e1, e2))
    assert(e3 === B(false))

    val e4 = Undefined
    val e5 = Undefined
    val e6 = eval(Binary(Eq, e4, e5))
    assert(e6 === B(true))

    val e7 = eval(Binary(Eq, e4, e1))
    assert(e7 === B(false))
  }

  "Ne" should "return true if two numerical values are different" in {
    val e1 = N(5)
    val e2 = N(7)
    val e3 = eval(Binary(Ne, e1, e2))
    assert(e3 === B(true))
  } 
  
  it should "return false if two numerical values are the same" in {
    val e1 = N(5)
    val e2 = N(5)
    val e3 = eval(Binary(Ne, e1, e2))
    assert(e3 === B(false))
  }

  "Lt" should "return true if the first expression is less than the second" in {
    val e1 = N(5)
    val e2 = N(7)
    val e3 = eval(Binary(Lt, e1, e2))
    assert(e3 === B(true))
  } 
  
  it should "return false if the first expression is not strictly less than the second" in {
    val e1 = N(7)
    val e2 = N(5)
    val e3 = eval(Binary(Lt, e1, e2))
    assert(e3 === B(false))
  } 
  
  it should "return false if two number values are the same" in {
    val e1 = N(5)
    val e2 = N(5)
    val e3 = eval(Binary(Lt, e1, e2))
    assert(e3 === B(false))
  } 

  "Le" should "return true if the first expression is less than the second" in {
    val e1 = N(5)
    val e2 = N(7)
    val e3 = eval(Binary(Le, e1, e2))
    assert(e3 === B(true))
  } 
  
  it should "return false if the first expression is greater than the second" in {
    val e1 = N(7)
    val e2 = N(5)
    val e3 = eval(Binary(Le, e1, e2))
    assert(e3 === B(false))
  } 
  
  it should "return true if two number values are the same" in {
    val e1 = N(5)
    val e2 = N(5)
    val e3 = eval(Binary(Le, e1, e2))
    assert(e3 === B(true))
  } 

  "Gt" should "return true if the first expression is greater than the second" in {
    val e1 = N(8)
    val e2 = N(7)
    val e3 = eval(Binary(Gt, e1, e2))
    assert(e3 === B(true))
  } 
  
  it should "return false if the first expression is not strictly greater than the second" in {
    val e1 = N(4)
    val e2 = N(5)
    val e3 = eval(Binary(Gt, e1, e2))
    assert(e3 === B(false))
  } 
  
  it should "return false if two number values are the same" in {
    val e1 = N(5)
    val e2 = N(5)
    val e3 = eval(Binary(Gt, e1, e2))
    assert(e3 === B(false))
  } 

  "Ge" should "return true if the first expression is greater than the second" in {
    val e1 = N(8)
    val e2 = N(7)
    val e3 = eval(Binary(Ge, e1, e2))
    assert(e3 === B(true))
  } 
  
  it should "return false if the first expression is less than the second" in {
    val e1 = N(4)
    val e2 = N(5)
    val e3 = eval(Binary(Ge, e1, e2))
    assert(e3 === B(false))
  } 
  
  it should "return true if two number values are the same" in {
    val e1 = N(5)
    val e2 = N(5)
    val e3 = eval(Binary(Ge, e1, e2))
    assert(e3 === B(true))
  }

  "Comparisons" should "produce non-intuitive results given the expressions given" in {
    val e1 = N(5)
    val e2 = Undefined
    assert(eval(Binary(Eq,e1,e2)) === B(false))
  } 

  "ConstDecl" should "extend the environment with the first expression results bound to the identifier, and then eval the second expression" in {
    val e1 = N(3)
    val e2 = Binary(Plus, Var("x"), N(1))
    val e3 = eval(ConstDecl("x", e1, e2)) 
    assert(e3 === N(4))
  } 
  
  "If" should "eval the first expression if the conditional is true" in {
    val e1 = Binary(Plus, N(3), N(2))
    val e2 = Binary(Plus, N(1), N(1))
    val e3 = eval(If(B(true), e1, e2)) 
    assert(e3 === N(5))
  } 
  
  it should "eval the second expression if the conditional is false" in {
    val e1 = Binary(Plus, N(3), N(2))
    val e2 = Binary(Plus, N(1), N(1))
    val e3 = eval(If(B(false), e1, e2)) 
    assert(e3 === N(2))
  } 
  
  "Seq" should "execute the first expression, followed by the second, and should eval to the second expression" in {
    val e1 = Binary(Plus, N(3), N(2))
    val e2 = Binary(Plus, N(1), N(1))
    val e3 = eval(Binary(Seq, e1, e2)) 
    assert(e3 === N(2))
  } 
  
  "Neg" should "return the negation of a number value" in {
    val e1 = N(5)
    val e2 = eval(Unary(Neg, e1))
    assert(e2 === N(-5))
  } 
  
  "Not" should "return the compliment of a boolean value" in {
    val e1 = B(true)
    val e2 = B(false)
    val e3 = eval(Unary(Not, e1))
    val e4 = eval(Unary(Not, e2))
    assert(e3 === B(false))
    assert(e4 === B(true))
  }
}

// An adapter class to pass in your Lab2 object.
class Lab2SpecRunner extends Lab2Spec(Lab2)

// The next bit of code runs a test for each .jsy file in src/test/resources/lab2.
// The test expects a corresponding .ans file with the expected result.
class Lab2JsyTests extends JavascriptyTester(None, "lab2", Lab2)

class Lab2Suite extends Suites(
  new Lab2SpecRunner,
  new Lab2JsyTests
)

