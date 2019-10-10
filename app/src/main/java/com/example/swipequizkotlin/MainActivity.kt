package com.example.swipequizkotlin

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.*
import androidx.recyclerview.widget.*
import com.example.reminderkotlin.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initViews()
    }

    private fun initViews() {
         // load in all the questions
        questions.add(Question(getString(R.string.question1)))
        questions.add(Question(getString(R.string.question2)))
        questions.add(Question(getString(R.string.question3)))
        questions.add(Question(getString(R.string.question4)))
        questionAdapter.notifyDataSetChanged()

        // Initialize the recycler view with a linear layout manager, adapter
        rvQuestions.layoutManager =
            LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvQuestions.adapter = questionAdapter
        rvQuestions.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )
        // make questions swipeable
        createItemTouchHelper().attachToRecyclerView(rvQuestions)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                // swipe left means the user thinks answer is incorrect
                checkAnswer(position, direction)

                questionAdapter.notifyItemChanged(viewHolder.adapterPosition)
                questionAdapter.notifyDataSetChanged()
            }
        }
        return ItemTouchHelper(callback)
    }

    private fun checkAnswer(position: Int, direction: Int) {
        // Even questions should be correct, uneven incorrect
        if (position % 2 == 0) {
            if (ItemTouchHelper.LEFT == direction) {
                Snackbar.make(
                    rvQuestions,
                    R.string.incorrect,
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    rvQuestions,
                    R.string.correct,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        } else {
            if (ItemTouchHelper.RIGHT == direction) {
                Snackbar.make(
                    rvQuestions,
                    R.string.correct,
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    rvQuestions,
                    R.string.incorrect,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}