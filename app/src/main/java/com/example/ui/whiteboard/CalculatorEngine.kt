package com.example.ui.whiteboard

enum class AngleMode {
    DEG, RAD, GRA
}

class CalculatorEngine(var angleMode: AngleMode = AngleMode.RAD) {
    fun evaluate(expression: String): String {
        return try {
            var formattedExp = expression
                .replace("×", "*")
                .replace("÷", "/")
                .replace("π", Math.PI.toString())
                .replace("e", Math.E.toString())
                .replace("sin⁻¹", "asin")
                .replace("cos⁻¹", "acos")
                .replace("tan⁻¹", "atan")
                .replace("²", "^2")
                .replace("³", "^3")
                .replace("⁻¹", "^(-1)")
                .replace("√", "sqrt")

            // Auto-close open parentheses
            val openParens = formattedExp.count { it == '(' }
            val closeParens = formattedExp.count { it == ')' }
            repeat(openParens - closeParens) {
                formattedExp += ")"
            }
            
            val result = eval(formattedExp)
            
            // Format result
            if (result == result.toLong().toDouble()) {
                result.toLong().toString()
            } else {
                result.toString()
            }
        } catch (e: Exception) {
            "Error"
        }
    }

    private fun eval(str: String): Double {
        return object : Any() {
            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < str.length) str[pos].toInt() else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.toInt()) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < str.length) throw RuntimeException("Unexpected: " + ch.toChar())
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'.toInt())) x += parseTerm() // addition
                    else if (eat('-'.toInt())) x -= parseTerm() // subtraction
                    else return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.toInt())) x *= parseFactor() // multiplication
                    else if (eat('/'.toInt())) x /= parseFactor() // division
                    else if (eat('%'.toInt())) x %= parseFactor() // modulo
                    else return x
                }
            }

            fun parseFactor(): Double {
                if (eat('+'.toInt())) return parseFactor() // unary plus
                if (eat('-'.toInt())) return -parseFactor() // unary minus

                var x: Double
                val startPos = this.pos
                if (eat('('.toInt())) { // parentheses
                    x = parseExpression()
                    eat(')'.toInt())
                } else if ((ch >= '0'.toInt() && ch <= '9'.toInt()) || ch == '.'.toInt()) { // numbers
                    while ((ch >= '0'.toInt() && ch <= '9'.toInt()) || ch == '.'.toInt()) nextChar()
                    x = str.substring(startPos, this.pos).toDouble()
                } else if (ch >= 'a'.toInt() && ch <= 'z'.toInt() || ch >= 'A'.toInt() && ch <= 'Z'.toInt() || ch == '√'.toInt()) { // functions
                    while (ch >= 'a'.toInt() && ch <= 'z'.toInt() || ch >= 'A'.toInt() && ch <= 'Z'.toInt() || ch == '√'.toInt()) nextChar()
                    val func = str.substring(startPos, this.pos)
                    x = if (func == "π" || func == "pi") {
                        Math.PI
                    } else if (func == "e") {
                        Math.E
                    } else {
                        val arg = parseFactor()
                        when (func) {
                            "sin" -> Math.sin(convertToRadian(arg))
                            "cos" -> Math.cos(convertToRadian(arg))
                            "tan" -> Math.tan(convertToRadian(arg))
                            "asin" -> convertFromRadian(Math.asin(arg))
                            "acos" -> convertFromRadian(Math.acos(arg))
                            "atan" -> convertFromRadian(Math.atan(arg))
                            "ln" -> Math.log(arg)
                            "log" -> Math.log10(arg)
                            "sqrt", "√" -> Math.sqrt(arg)
                            "abs" -> Math.abs(arg)
                            else -> throw RuntimeException("Unknown function: $func")
                        }
                    }
                } else {
                    throw RuntimeException("Unexpected: " + ch.toChar())
                }

                if (eat('^'.toInt())) x = Math.pow(x, parseFactor()) // exponentiation

                while (eat('!'.toInt())) {
                    x = factorial(x)
                }

                return x
            }
            
            private fun convertToRadian(angle: Double): Double {
                return when (angleMode) {
                    AngleMode.DEG -> Math.toRadians(angle)
                    AngleMode.RAD -> angle
                    AngleMode.GRA -> angle * Math.PI / 200.0
                }
            }
            
            private fun convertFromRadian(rad: Double): Double {
                 return when (angleMode) {
                    AngleMode.DEG -> Math.toDegrees(rad)
                    AngleMode.RAD -> rad
                    AngleMode.GRA -> rad * 200.0 / Math.PI
                }
            }

            private fun factorial(n: Double): Double {
                if (n < 0 || n != Math.floor(n)) throw RuntimeException("Factorial only defined for non-negative integers")
                var res = 1.0
                for (i in 2..n.toInt()) {
                    res *= i
                }
                return res
            }
        }.parse()
    }
}
