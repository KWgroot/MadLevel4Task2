package com.example.madlevel4task2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.random.Random

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {

    private lateinit var gameRepository: GameRepository
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "RO-SHAM-BO (Rock-Paper-Scissors)"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        gameRepository = GameRepository(requireContext())

        ivRock.setImageResource(R.drawable.rock)
        ivPaper.setImageResource(R.drawable.paper)
        ivScissors.setImageResource(R.drawable.scissors)

        ivRock.setOnClickListener{
            runGame(0)
        }

        ivPaper.setOnClickListener{
            runGame(1)
        }

        ivScissors.setOnClickListener{
            runGame(2)
        }
    }

    private fun runGame(playerChoice: Int) {
        val computerChoice = Random.nextInt(0, 2)

        ivComputer.setImageResource(setChoice(playerChoice, computerChoice, true))
        ivYou.setImageResource(setChoice(playerChoice, computerChoice, false))

        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.insertGame(
                    Game(
                        setChoice(playerChoice, computerChoice, false),
                        setChoice(playerChoice, computerChoice, true),
                        Date(),
                        winner(playerChoice, computerChoice)
                    )
                )
            }
        }
    }

    private fun winner(playerChoice: Int, computerChoice: Int): String {
        if (playerChoice == computerChoice) {
            mainScope.launch {
                withContext(Dispatchers.Main) {
                    tvWinner.setText(R.string.draw)
                }
            }
            return "Draw"
        } else if ((playerChoice == 0 && computerChoice == 2) || (playerChoice == 1 && computerChoice == 0) || (playerChoice == 2 && computerChoice == 1)) {
            mainScope.launch {
                withContext(Dispatchers.Main) {
                    tvWinner.setText(R.string.win)
                }
            }
            return "You win"
        } else if ((computerChoice == 0 && playerChoice == 2) || (computerChoice == 1 && playerChoice == 0) || (computerChoice == 2 && playerChoice == 1)) {
            mainScope.launch {
                withContext(Dispatchers.Main) {
                    tvWinner.setText(R.string.lose)
                }
            }
            return "You lose"
        }
        return "Error"
    }

    @DrawableRes
    private fun setChoice(playerChoice: Int, computerChoice: Int, computer: Boolean): Int {
        if (computer){
            if (computerChoice == 0) {
                return R.drawable.rock
            } else if (computerChoice == 1) {
                return R.drawable.paper
            } else if (computerChoice == 2){
                return R.drawable.scissors
            }
        } else {
            if (playerChoice == 0) {
                return R.drawable.rock
            } else if (playerChoice == 1) {
                return R.drawable.paper
            } else if (playerChoice == 2){
                return R.drawable.scissors
            }
        }
        return 0
    }
}