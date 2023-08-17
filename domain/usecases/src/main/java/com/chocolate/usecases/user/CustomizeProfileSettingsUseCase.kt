package com.chocolate.usecases.user

import com.chocolate.entities.exceptions.NullDataException
import com.chocolate.entities.exceptions.TeamixException
import repositories.UsersRepository
import javax.inject.Inject

class CustomizeProfileSettingsUseCase @Inject constructor(
    private val usersRepositories: UsersRepository
) {
    suspend fun saveNewSelectedLanguage(newLanguage: String){
        if (!newLanguage.isNullOrBlank()){
            val isUpdateSuccessful = usersRepositories.updateAppLanguage(newLanguage)
            if (!isUpdateSuccessful){
                throw TeamixException("Failed to save the selected language settings.")
            }
        }else{
            throw NullDataException("An error occurred due to null or empty new language string value")
        }
    }

    suspend fun getLastSelectedAppLanguage() = usersRepositories.getLastSelectedAppLanguage()

    suspend fun updateDarkTheme(isDarkTheme:Boolean){
        val update=usersRepositories.updateDarkTheme(isDarkTheme)
    }

    suspend fun isDarkThem()=usersRepositories.isDarkThemeEnabled()


}