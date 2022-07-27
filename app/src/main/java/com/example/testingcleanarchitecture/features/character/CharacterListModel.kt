package com.example.testingcleanarchitecture.features.character

data class CharacterListModel(
    val value: List<String>
) {
    fun transformToModel(data: CharacterListData): CharacterListModel {
        return CharacterListModel(data)
    }
}
