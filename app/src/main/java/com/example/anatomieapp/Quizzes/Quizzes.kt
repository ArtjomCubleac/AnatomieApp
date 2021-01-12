package com.example.anatomieapp.Quizzes

data class Quizzes(

    var questionText: Int,
    var questionAnswer: String,
){

companion object {
    //Questions for the quiz
    val LEVEL1QUESTIONS = intArrayOf(
       1,2,3,4,5,6,7,8,9,10,11,12,13,14,15
    )

    //Answers for the quiz
    val LEVEL1ANSWERS =  arrayOf(
        "clavicle",
        "femur",
        "fibula",
        "pelvic girdle",
        "mandible",
        "humerus",
        "pubis",
        "patella",
        "radius",
        "rib cage",
        "sacrum",
        "skull",
        "sternum",
        "tibia",
        "ulna",
    )
}

}