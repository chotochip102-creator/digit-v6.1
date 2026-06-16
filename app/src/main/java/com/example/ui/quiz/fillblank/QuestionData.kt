package com.example.ui.quiz.fillblank

data class Blank(
    val position: Int,
    val answer: String,
    val hint: String
)

data class QuestionContent(
    val text: String,
    val blanks: List<Blank>
)

data class Question(
    val bn: QuestionContent,
    val en: QuestionContent
)

enum class Topic { PHYSICS, MATH }
enum class Lang { BN, EN }

object QuestionData {
    val questions = mapOf(
        Topic.PHYSICS to listOf(
            Question(
                bn = QuestionContent(
                    text = "নিউটনের দ্বিতীয় সূত্র অনুযায়ী, বল সমান [ভর] গুণ [ত্বরণ]।",
                    blanks = listOf(
                        Blank(0, "ভর", "m"),
                        Blank(1, "ত্বরণ", "a")
                    )
                ),
                en = QuestionContent(
                    text = "Newton's second law states that force equals [mass] times [acceleration].",
                    blanks = listOf(
                        Blank(0, "mass", "m"),
                        Blank(1, "acceleration", "a")
                    )
                )
            ),
            Question(
                bn = QuestionContent(
                    text = "বেগের পরিবর্তনের হারকে [ত্বরণ] বলে। SI এককে এটি [m/s²] এ পরিমাপ করা হয়।",
                    blanks = listOf(
                        Blank(0, "ত্বরণ", "a"),
                        Blank(1, "m/s²", "SI unit")
                    )
                ),
                en = QuestionContent(
                    text = "The rate of change of velocity is called [acceleration]. In SI units it is measured in [m/s²].",
                    blanks = listOf(
                        Blank(0, "acceleration", "a"),
                        Blank(1, "m/s²", "SI unit")
                    )
                )
            ),
            Question(
                bn = QuestionContent(
                    text = "গতিশক্তির সূত্র হলো KE = ½ [m] v², যেখানে m হলো [ভর] এবং v হলো বেগ।",
                    blanks = listOf(
                        Blank(0, "m", "symbol"),
                        Blank(1, "ভর", "Bengali/English")
                    )
                ),
                en = QuestionContent(
                    text = "The formula for kinetic energy is KE = ½ [m] v², where m is [mass] and v is velocity.",
                    blanks = listOf(
                        Blank(0, "m", "symbol"),
                        Blank(1, "mass", "Bengali/English")
                    )
                )
            )
        ),
        Topic.MATH to listOf(
            Question(
                bn = QuestionContent(
                    text = "একটি সমকোণী ত্রিভুজে, [পীথাগোরাস] উপপাদ্য বলে a² + b² = [c²]।",
                    blanks = listOf(
                        Blank(0, "পীথাগোরাস", "বিজ্ঞানীর নাম/theorem name"),
                        Blank(1, "c²", "hypotenuse²")
                    )
                ),
                en = QuestionContent(
                    text = "In a right triangle, the [Pythagorean] theorem states that a² + b² = [c²].",
                    blanks = listOf(
                        Blank(0, "Pythagorean", "বিজ্ঞানীর নাম/theorem name"),
                        Blank(1, "c²", "hypotenuse²")
                    )
                )
            ),
            Question(
                bn = QuestionContent(
                    text = "দ্বিঘাত সমীকরণের সমাধান: x = [−b ± √(b²−4ac)] / [2a]।",
                    blanks = listOf(
                        Blank(0, "−b ± √(b²−4ac)", "numerator"),
                        Blank(1, "2a", "denominator")
                    )
                ),
                en = QuestionContent(
                    text = "The quadratic formula gives x = [−b ± √(b²−4ac)] / [2a].",
                    blanks = listOf(
                        Blank(0, "−b ± √(b²−4ac)", "numerator"),
                        Blank(1, "2a", "denominator")
                    )
                )
            ),
            Question(
                bn = QuestionContent(
                    text = "একটি বৃত্তের ক্ষেত্রফল = [π] r², এবং পরিধি = 2 [π] r |",
                    blanks = listOf(
                        Blank(0, "π", "≈3.14"),
                        Blank(1, "π", "≈3.14")
                    )
                ),
                en = QuestionContent(
                    text = "The area of a circle = [π] r², and the circumference = 2 [π] r.",
                    blanks = listOf(
                        Blank(0, "π", "≈3.14"),
                        Blank(1, "π", "≈3.14")
                    )
                )
            )
        )
    )

    fun normalize(input: String): String =
        input.trim().lowercase()
            .replace("\\s+".toRegex(), "")
            .replace("−", "-")
            .replace("×", "*")
            .replace("÷", "/")
}
