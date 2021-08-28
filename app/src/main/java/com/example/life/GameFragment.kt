package com.example.life

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.life.databinding.FragmentGameBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameFragment : Fragment() {

    private val DEFAULT_SPEED_MS = 100
    private val MULTIPLIER = 1.0
    private var gameIsPlaying = false
    private lateinit var board: Board
    private lateinit var binding: FragmentGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        board = Board()
        binding.canvasView.board = board

        board.cells.observe(viewLifecycleOwner) {
            binding.gameStats.text =
                getString(R.string.game_stats)
                    .format(
                        board.generation,
                        board.numCells
                    )
            binding.canvasView.drawBoard()
        }

        binding.start.setOnClickListener {
            gameIsPlaying = !gameIsPlaying
            if (gameIsPlaying)
                binding.start.setImageResource(R.drawable.ic_baseline_pause_24)
            else
                binding.start.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            lifecycleScope.launch(Dispatchers.Default) {
                while (gameIsPlaying) {
                    delay((DEFAULT_SPEED_MS*MULTIPLIER).toLong())
                    board.nextTurn()
                }
            }
        }

        return binding.root
    }
}