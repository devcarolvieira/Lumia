package com.example.lumia

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SoundViewModel(application: Application) : AndroidViewModel(application) {
    private var mediaPlayer: MediaPlayer? = null

    // Armazena o som selecionado
    private val _selectedSound = MutableStateFlow<SoundItem?>(null)
    val selectedSound: StateFlow<SoundItem?> = _selectedSound

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _volume = MutableStateFlow(0.75f)
    val volume: StateFlow<Float> = _volume

    // Função para definir o som quando o usuário clicar na lista
    fun selectSound(sound: SoundItem) {
        if (_selectedSound.value?.id != sound.id) {
            _selectedSound.value = sound
            playAudio(sound.audioRes)
        } else {
            togglePlayPause()
        }
    }

    private fun playAudio(audioRes: Int) {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null

        if (audioRes != 0) {
            try {
                mediaPlayer = MediaPlayer.create(getApplication(), audioRes)
                mediaPlayer?.let {
                    it.isLooping = true
                    it.setVolume(_volume.value, _volume.value)
                    it.start()
                    _isPlaying.value = true
                } ?: run {
                    _isPlaying.value = false
                }
            } catch (e: Exception) {
                _isPlaying.value = false
            }
        } else {
            _isPlaying.value = false
        }
    }

    fun togglePlayPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
                _isPlaying.value = false
            } else {
                it.start()
                _isPlaying.value = true
            }
        }
    }

    fun setVolume(volume: Float) {
        _volume.value = volume
        mediaPlayer?.setVolume(volume, volume)
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
