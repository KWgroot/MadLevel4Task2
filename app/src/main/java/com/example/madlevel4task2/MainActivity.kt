package com.example.madlevel4task2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var menu: Menu
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        navController =  findNavController(R.id.nav_host_fragment)
        gameRepository = GameRepository(applicationContext)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        this.menu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.itemId == R.id.btnHistory || item.itemId == android.R.id.home){
            menu.clear()

            navController.navigate(
                if (navController.currentDestination?.id == R.id.gameFragment){
                    menuInflater.inflate(R.menu.menu_history, menu)
                    R.id.action_gameFragment_to_historyFragment
                } else {
                    menuInflater.inflate(R.menu.menu_main, menu)
                    R.id.action_historyFragment_to_gameFragment
                }
            )
        } else {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    gameRepository.deleteAllGames()
                }
            }
        }

        return when (item.itemId) {
            R.id.btnHistory -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}