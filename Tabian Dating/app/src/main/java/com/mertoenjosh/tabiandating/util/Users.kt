package com.mertoenjosh.tabiandating.util

import com.mertoenjosh.tabiandating.R
import com.mertoenjosh.tabiandating.models.User


class Users {
    var USERS = arrayOf(
        James, Elizabeth, Robert, Carol, Jennifer, Susan, Michael, William, Karen, Joseph, Nancy,
        Charles, Matthew, Sarah, Jessica, Donald, Mary, Paul, Patricia, Linda, Steve
    )

    companion object {
        /*
            Men
        */
        val James = User( R.drawable.james,"James", "Male", "Female", "Looking")

        val Robert = User( R.drawable.robert, "Robert", "Male", "Female", "Looking")
        val Michael = User(R.drawable.michael, "Michael", "Male", "Female", "Looking")
        val William = User(R.drawable.william, "William", "Male", "Female", "Not Looking")
        val Joseph = User(R.drawable.joseph, "Joseph", "Male", "Female", "Looking")
        val Charles = User(R.drawable.charles, "Charles", "Male", "Female", "Looking")
        val Matthew = User( R.drawable.mattew, "Matthew", "Male", "Female", "Looking")
        val Donald = User( R.drawable.donald, "Donald", "Male", "Female", "Looking" )
        val Paul = User(R.drawable.paul, "Paul", "Male", "Female", "Looking")
        val Steve = User(R.drawable.steve, "Steve", "Male", "Female", "Looking")

        /*
            Females
     */

        val Mary = User(R.drawable.mary, "Mary", "Female", "Male", "Looking")
        val Patricia = User( R.drawable.patricia, "Patricia", "Female", "Male", "Looking")
        val Jennifer = User(R.drawable.jennifer, "Jennifer", "Female", "Male", "Looking")
        val Elizabeth = User(R.drawable.elizabeth, "Elizabeth", "Female", "Male", "Looking")
        val Linda = User( R.drawable.linda, "Linda", "Female", "Male", "Looking")
        val Susan = User(R.drawable.susan, "Susan", "Female", "Male", "Looking")
        val Jessica = User(R.drawable.jessica, "Jessica", "Female", "Male", "Looking")
        val Sarah = User(R.drawable.sarah, "Sarah", "Female", "Male", "Looking")
        val Karen = User(R.drawable.karen, "Karen", "Female", "Male", "Looking")
        val Nancy = User(R.drawable.nancy, "Nancy", "Female", "Male", "Looking")
        val Carol = User(R.drawable.carol, "Carol", "Do Not Identify", "Anyone", "Looking" )
    }
}
