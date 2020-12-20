package com.example.squadmaker.viewmodel

import android.view.View
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.squadmaker.model.localdatasouce.roomdatabase.entity.CharacterEntity
import com.example.squadmaker.model.localdatasouce.roomdatabase.entity.SquadEntity
import com.example.squadmaker.model.repository.Repository
import com.example.squadmaker.view.mainfragment.MainFragmentDirections
import com.example.squadmaker.view.uimodel.UICharacter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModelImpl
@ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(),
    MainViewModel {

    // region Public Functions

    override fun fetchCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.fetchAndSaveCharacters()
        }
    }

    // region LiveData observing

    override fun getCharacters(): LiveData<List<UICharacter>> {
        return repository.getCharacters().map {
            return@map transformToUICharacter(it)
        }.asLiveData()
    }

    override fun getSquad(): LiveData<List<SquadEntity>> {
        return repository.getSquad()
    }

    // endregion

    // region Private Functions

    private fun transformToUICharacter(it: List<CharacterEntity>): MutableList<UICharacter> {
        val charList = mutableListOf<UICharacter>()
        it.map { character ->
            val clickAction = getClickActionForCharacter(character)
            val uiCharacter = getUICharacter(character, clickAction)
            charList.add(uiCharacter)
        }
        return charList
    }

    private fun getUICharacter(
        character: CharacterEntity,
        clickAction: (View) -> Unit
    ): UICharacter {
        return UICharacter(
            character.id,
            character.name,
            character.thumbnailPath,
            clickAction
        )
    }

    private fun getClickActionForCharacter(character: CharacterEntity): (View) -> Unit {
        return { view: View ->
            val action =
                MainFragmentDirections.actionMainFragmentToDetailedCharacterFragment(
                    character.id
                )

            view.findNavController().navigate(action)
        }
    }

    // endregion

}